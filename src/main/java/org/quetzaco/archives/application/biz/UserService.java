package org.quetzaco.archives.application.biz;

import org.quetzaco.archives.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by deya on 2017/7/10.
 */
public interface UserService {

  void createUser(User user,Long deptId);

  User getUserDetails(Long id);

  List<User> getUserList();

  void deactiveUser(Long id);

  void updateUser(User user);

  User login(String loginName, String password);

  User selectUserByLoginName(String loginName);

  boolean isAdmin(long usrId);

  void closeUser(Long usrId);

  void activateUser(Long usrId);

  Map loadingUser(Long usrId);

  boolean updatePassword(Long usrId, String oldPwd, String newPwd);

  Map remainToBeDone(User user);

  User getContextUser(Long usrId);

  User getContextUser();

  Long getRoleByUsr(Long usrId);

  Long getRoleByUsr();
}
