package org.quetzaco.archives.application.biz.sync.oa.Impl;

import java.util.Date;
import java.util.List;
import org.quetzaco.archives.application.biz.sync.oa.OAOrganizationService;
import org.quetzaco.archives.application.dao.DeptMapper;
import org.quetzaco.archives.application.dao.OaCodesheetMapper;
import org.quetzaco.archives.application.dao.OaDepartmentMapper;
import org.quetzaco.archives.application.dao.OaPostMapper;
import org.quetzaco.archives.application.dao.UserMapper;
import org.quetzaco.archives.application.dao.sync.oa.OAConnectionHelper;
import org.quetzaco.archives.model.Dept;
import org.quetzaco.archives.model.OaCodesheet;
import org.quetzaco.archives.model.OaDepartment;
import org.quetzaco.archives.model.OaPost;
import org.quetzaco.archives.model.User;
import org.quetzaco.archives.util.config.ArchiveProperties;
import org.quetzaco.archives.util.encoder.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @Description Created by dong on 2017/8/18.
 */
@Service
public class OAOrganizationServiceImpl implements OAOrganizationService {

  @Autowired
  OAConnectionHelper oaConnectionHelper;
  @Autowired
  ArchiveProperties archiveProperties;
  @Autowired
  OaDepartmentMapper oaDepartmentMapper;
  @Autowired
  OaPostMapper oaPostMapper;
  @Autowired
  OaCodesheetMapper oaCodesheetMapper;

  @Autowired
  UserMapper userMapper;
  @Autowired
  DeptMapper deptMapper;

  @Autowired
  PasswordEncoder passwordEncoder;

  /**
   *
   */
  public void clearOAData() {
    oaDepartmentMapper.deleteAll();
    oaCodesheetMapper.deleteAll();
    oaPostMapper.deleteAll();
  }

  public void getAllOAData() {
    getOAOrganization();
    getAllOaUsers();
    getAllOaPassword();
  }

  public void syncOAData() {
    createNoExistsData();
    updateExistsData();
  }

  private void getOAOrganization() {
    List<OaDepartment> allOaOrganizationUnits = oaConnectionHelper
        .getAllOaOrganizationUnits(archiveProperties.getOABaseOrgId(),
            oaDepartment -> {
              return oaDepartmentMapper.insert(oaDepartment);
            });

  }

  private void getAllOaUsers() {

    List<OaPost> allOaUsers = oaConnectionHelper.getAllOaUsers(oaPost -> {
      return oaPostMapper.insert(oaPost);
    });
  }

  private void getAllOaPassword() {

    List<OaCodesheet> allOaPassword = oaConnectionHelper.getAllOaPassword(oaCodesheet -> {
      return oaCodesheetMapper.insert(oaCodesheet);
    });
  }

  private void createNoExistsData() {
    createNoExistsOAOrganization();
    createNoExistsOaUsers();
  }

  private void createNoExistsOaUsers() {
    oaPostMapper.selectNotExistsData().forEach(oaPost -> {
      User user = new User();
      user.setLoginName(oaPost.getUserid());
      user.setPassword(passwordEncoder.encode("123456"));
      user.setName(oaPost.getOperatorname());
      user.setCreatedDt(new Date());
      user.setUpdatedDt(user.getCreatedDt());
      userMapper.insertSelective(user);
      oaPostMapper.insertLinkUsersOaPost(user.getId(), oaPost.getOperatorid());
    });
  }

  private void createNoExistsOAOrganization() {
    oaDepartmentMapper.selectNotExistsData().forEach(oaDepartment -> {
      Dept dept = new Dept();
      dept.setName(oaDepartment.getOrgname());
      if(!oaDepartment.getParentorgid().equals(archiveProperties.getOABaseOrgId())) {
        dept.setPrtId(oaDepartmentMapper.getDeptPrtId(oaDepartment.getParentorgid()));
      }
      deptMapper.insertSelective(dept);
      oaDepartmentMapper.insertLinkDepOADepartment(dept.getId(), oaDepartment.getOrgid());
    });

  }

  private void updateExistsData() {
    updateExistsOAOrganization();
    updateExistsOaUsers();
    updateUserDepts();
  }

  private void updateUserDepts() {
    oaPostMapper.deleteAllOAUserDepts();
    oaPostMapper.insertOAUserDepts();
    oaPostMapper.importUsersToRootDept();
  }

  private void updateExistsOaUsers() {
    oaPostMapper.updateExistsOaUsers();
  }

  private void updateExistsOAOrganization() {
    oaDepartmentMapper.updateExistsOAOrganization(Long.parseLong(archiveProperties.getOABaseOrgId()));
  }

  @Scheduled(cron = "0 0 2 * * ?")
  public void syncRunner() {
    clearOAData();
    getAllOAData();
    syncOAData();
  }

}
