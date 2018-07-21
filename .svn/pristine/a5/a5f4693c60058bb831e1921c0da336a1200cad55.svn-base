package org.quetzaco.archives.application.biz;

import java.util.List;
import java.util.Map;

import org.quetzaco.archives.model.Role;
import org.quetzaco.archives.model.User;

/**
 * Created by deya on 2017/7/11.
 */
public interface RoleService {

  Map assignRole(Long roleId, Long usrId, Long deptId);

  List<Role> getRoleList();

  List<Map> getRoleListForSelector();

  Role getRoleByDepAndUser(Long deptId, Long usrId);

  List<User> getUserByRoleAndDept(Long deptId, Long roleId);

  void removeUserByRoleAndDept(Long[] usrId, Long deptId, Long roleId);

  List<Role> getRoleListByUser(Long usrId);

  List<User>selectRoles(Long roleId);
}
