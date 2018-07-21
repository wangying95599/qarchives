package org.quetzaco.archives.application.biz.Impl;

import org.quetzaco.archives.application.biz.DeptService;
import org.quetzaco.archives.application.biz.RoleService;
import org.quetzaco.archives.application.biz.UserService;
import org.quetzaco.archives.application.dao.FlowsMapper;
import org.quetzaco.archives.application.dao.OaCodesheetMapper;
import org.quetzaco.archives.application.dao.UserMapper;
import org.quetzaco.archives.model.OaCodesheetExample;
import org.quetzaco.archives.model.Role;
import org.quetzaco.archives.model.User;
import org.quetzaco.archives.util.boot.RoleType;
import org.quetzaco.archives.util.config.ArchiveProperties;
import org.quetzaco.archives.util.encoder.password.OAPasswordEncoder;
import org.quetzaco.archives.util.encoder.password.PasswordEncoder;
import org.quetzaco.archives.util.runnable.ThreadLocalTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by deya on 2017/7/10.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

  final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
  @Autowired
  UserMapper userMapper;

  @Autowired
  DeptService deptService;

  @Autowired
  RoleService roleService;

  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  OAPasswordEncoder oaPasswordEncoder;
  @Autowired
  ArchiveProperties archiveProperties;
  @Autowired
  OaCodesheetMapper oaCodesheetMapper;
  @Autowired
  FlowsMapper flowsMapper;
  @Autowired
  RedisTemplate redisTemplate;

  @Override
  public void createUser(User user, Long deptId) {
    userMapper.insertSelective(user);
    String loginName = user.getLoginName();
    if (deptId != null) {
      User deptUser = userMapper.selectUserByLoginName(loginName);
      //把用户加到部门下
      deptService.addUserToDept(deptUser.getId(), deptId);
      //给用户设置默认权限（查看）
//      roleService.assignRole(null, deptUser.getId(), deptId);
    }

  }

  @Override
  public User getUserDetails(Long id) {
    return userMapper.selectByPrimaryKey(id);
  }

  @Override
  public List<User> getUserList() {
    return userMapper.getUserList();
  }

  @Override
  public void deactiveUser(Long id) {
    User user = new User();
    user.setState("1");
    user.setId(id);
    user.setUpdatedDt(new Date());
    userMapper.updateByPrimaryKeySelective(user);
  }

  @Override
  public void updateUser(User user) {
    userMapper.updateByPrimaryKeySelective(user);
  }

  @Override
  public User login(String loginName, String password) {
    User user = userMapper.getUserByLoginName(loginName);
    if (user == null) {
      return null;
    }

    if ("OA".equals(archiveProperties.getPasswordValiditor())&&!"admin".equals(loginName)){
      OaCodesheetExample example = new OaCodesheetExample();
      example.createCriteria().andUseridEqualTo(loginName);
      String correctPwd = "";
      try {
        correctPwd = oaCodesheetMapper.selectByExample(example).get(0).getPassword();
      } catch (Exception e) {
        logger.warn("can not find in OA User list  , check password_validator property");
      }
      if (oaPasswordEncoder.isPasswordValid(correctPwd, password)) {
        return user;
      } else {
        return null;
      }
    }else{
      String correctPwd = user.getPassword();
      if (passwordEncoder.isPasswordValid(correctPwd, password)) {
        return user;
      } else {
        return null;
      }
    }


  }

  @Override
  public User selectUserByLoginName(String loginName) {
    return userMapper.selectUserByLoginName(loginName);
  }

  @Override
  public boolean isAdmin(long usrId) {
    if (userMapper.isAdmin(usrId) != null) {
      return true;
    } else {
      return false;
    }
  }

  //此方法和上面的deactive方法 重复
  @Override
  public void closeUser(Long usrId) {
    User user = new User();
    user.setId(usrId);
    user.setState("1");
    user.setUpdatedDt(new Date());
    userMapper.updateByPrimaryKeySelective(user);
  }

  @Override
  public void activateUser(Long usrId) {
    User user = new User();
    user.setId(usrId);
    user.setState("0");
    user.setUpdatedDt(new Date());
    userMapper.updateByPrimaryKeySelective(user);
  }

  @Override
  public Map loadingUser(Long usrId) {
    String userKey = "user_"+usrId;
    Map map = (Map) redisTemplate.opsForValue().get(userKey);
    if(map==null){
      map = userMapper.loadingUser(usrId);
      redisTemplate.opsForValue().set(userKey,map);
    }

    return map;
  }

  @Override
  public boolean updatePassword(Long usrId, String oldPwd, String newPwd) {
    User user = userMapper.selectByPrimaryKey(usrId);
    if (!passwordEncoder.isPasswordValid(user.getPassword(), oldPwd)) {
      return false;
    }
    User newUser = new User();
    newUser.setId(usrId);
    newUser.setPassword(passwordEncoder.encode(newPwd));
    userMapper.updateByPrimaryKeySelective(newUser);
    return true;
  }

  @Override
  public Map remainToBeDone(User user) {
    List<Map> list1 = flowsMapper.remainToBeDone(user.getId());
    List<Map> list2 = flowsMapper.remainGiveBack(user.getId());
    // list1.addAll(list2);
    Map map = new HashMap();
    map.put("toBe", list1);
    map.put("giveBack", list2);
    return map;
  }

  @Override

  public User getContextUser(Long usrId) {
    User ContextUser = userMapper.getContextUser(usrId);
    return ContextUser;
  }

  @Override
  public User getContextUser() {
    Long usrId = ThreadLocalTest.getUsrTl();
    User contextUser = userMapper.getContextUser(usrId);
    if(contextUser.getRole()==null){
      Role role = new Role();
      role.setId(RoleType.EVERYMAN.getType());
      role.setName(RoleType.EVERYMAN.getDesc());
    }
    return contextUser;
  }

  @Override
  public Long getRoleByUsr(Long usrId) {
    User contextUser = getContextUser(usrId);
    Role role = contextUser.getRole();
    Long roleId = RoleType.EVERYMAN.getType();
    if (role != null) {
      roleId = role.getId();
    }

    logger.debug("roleId : "+roleId);
    return roleId;
  }

  @Override
  public Long getRoleByUsr() {
    User contextUser = getContextUser();
    Role role = contextUser.getRole();
    Long roleId = RoleType.EVERYMAN.getType();
    if (role != null) {
      roleId = role.getId();
    }

    logger.debug("roleId : "+roleId);
    return roleId;
  }
}
