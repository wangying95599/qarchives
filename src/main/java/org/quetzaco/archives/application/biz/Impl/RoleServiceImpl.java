package org.quetzaco.archives.application.biz.Impl;

import org.quetzaco.archives.application.biz.RoleService;
import org.quetzaco.archives.application.dao.DeptMapper;
import org.quetzaco.archives.application.dao.RoleMapper;
import org.quetzaco.archives.model.Role;
import org.quetzaco.archives.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by deya on 2017/7/11.
 */

@Service("RoleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    DeptMapper deptMapper;
    @Autowired
    RedisTemplate redisTemplate;

//  @Autowired
//  UserMapper userMapper;

    @Override
    @CacheEvict(value = "userext",key = "'ctx_'+#p1")
    public Map assignRole(Long roleId, Long usrId, Long deptId) {

        String userKey  = "user_"+usrId;
        redisTemplate.delete(userKey);
	/*if (roleId == null) {
          //设置为查看权限
	  roleMapper.assignDefaultRole(usrId, deptId);
	} else {*/
        System.out.println("usrId---- " + usrId);
        //得到全局 的deptId
        Long siteDeptId = deptMapper.getDeptIdForSite();

        //如果是分配全局角色
        if (deptId == -1) {
            roleMapper.deleteRoleByUser(usrId);
            roleMapper.insertRoleWithUser(roleId, usrId, siteDeptId);
            Role backRole = roleMapper.selectByPrimaryKey(roleId);
            return assignRoleBack(usrId, backRole.getName());
        } else {
            //如果是分配部门的角色的话  应该删除他全局的角色
            roleMapper.deleteBySiteDeptId(usrId, siteDeptId);
            if (roleId != -1) {
                Role role = roleMapper.getRoleByDeptAndUser(deptId, usrId);
                if (role != null) {
                    roleMapper.updateRoleByDeptAndUser(roleId, deptId, usrId);
                } else {
                    roleMapper.insertRoleWithUser(roleId, usrId, deptId);
                }

                Role backRole = roleMapper.selectByPrimaryKey(roleId);
                return assignRoleBack(usrId, backRole.getName());
            } else {
                //如果是分配 普通人员 角色
                roleMapper.deleteRoleByDeptAndUser(deptId, usrId);
                return assignRoleBack(usrId, "普通用户");
            }
        }
//	}
    }

    public static Map assignRoleBack(Long usrId, String roleName) {
        Map map = new HashMap();
        map.put("usrId", usrId);
        map.put("roleName", roleName);
        return map;
    }

    @Override
    public List<Role> getRoleList() {
        return roleMapper.getRoleList();
    }

    @Override
    public List<Map> getRoleListForSelector() {
        List<Role> roles = roleMapper.getRoleList();
        List<Map> maps = new ArrayList<Map>();
        for (Role role : roles) {
            Map map = new HashMap();
            map.put("text", role.getName());
            map.put("value", role.getId());
            maps.add(map);
        }
        return maps;
    }

    @Override
    public Role getRoleByDepAndUser(Long deptId, Long usrId) {
        return roleMapper.getRoleByDeptAndUser(deptId, usrId);
    }

    @Override
    public List<User> getUserByRoleAndDept(Long deptId, Long roleId) {
        if (deptId == -1) {
            deptId = deptMapper.getDeptIdForSite();
        }
        return roleMapper.getUserByRoleAndDept(deptId, roleId);
    }

    @Transactional
    @Override
    public void removeUserByRoleAndDept(Long[] usrIds, Long deptId, Long roleId) {
        if (deptId == -1)
            deptId = deptMapper.getDeptIdForSite();
        String[] userKeys =new String[usrIds.length];
        for (int i = 0; i < usrIds.length; i++) {
            Long usrId = usrIds[i];
            userKeys[i] = "user_"+usrId;
            roleMapper.removeUserByRoleAndDept(usrId, deptId, roleId);
        }
        redisTemplate.delete(userKeys);

    }

    @Override
    public List<Role> getRoleListByUser(Long usrId) {

        List<Role> roles = roleMapper.getRoleList();
        if (roleMapper.isSiteAdmin(usrId) > 0) {
            return roles;
        } else {
            int j = 0;
            for (int i = 0; i < roles.size(); i++) {

                if ("档案管理员".equals(roles.get(i - j).getName()) || "档案整理员".equals(roles.get(i - j).getName()
                )) {
                    roles.remove(i - j);
                    j++;
                }
            }
            return roles;
        }

    }

    @Override
    public List<User> selectRoles(Long roleId) {
        return roleMapper.selectRoles(roleId);
    }
}
