package org.quetzaco.archives.web.restful;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quetzaco.archives.application.biz.RoleService;
import org.quetzaco.archives.model.Role;
import org.quetzaco.archives.model.User;
import org.quetzaco.archives.model.api.APIEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by deya on 2017/7/11.
 */
@RestController
public class RoleController extends BaseRestContoller {

  @Autowired
  RoleService roleService;


  //给用户 分配角色
  @RequestMapping(value = "/assign/{roleId}/{usrId}/{deptId}",method = RequestMethod.PUT)
  public HttpEntity<APIEntity<Map>> assignRole(@PathVariable Long roleId, @PathVariable Long usrId,@PathVariable Long deptId) {
	return buildEntity(APIEntity.create(roleService.assignRole(roleId, usrId, deptId)), HttpStatus. ACCEPTED);
  }

  @RequestMapping(value = "/roles",method = RequestMethod.GET)
  public HttpEntity<APIEntity<List<Role>>> getRoleList(){
	return buildEntity(APIEntity.create(roleService.getRoleList()), HttpStatus.OK);
  }

  @RequestMapping(value = "/roles/users/{usrId}",method = RequestMethod.GET)
  public HttpEntity<APIEntity<List<Role>>> getRoleListByUser(@PathVariable Long usrId){
    return buildEntity(APIEntity.create(roleService.getRoleListByUser(usrId)), HttpStatus.OK);
  }

  //为角色下拉框准备数据  (当前代码没有用到)
    //@RequestMapping(value = "/roles/selector",method = RequestMethod.GET)
    public HttpEntity<APIEntity<List<Map>>> getRoleListForSelector(){
        return buildEntity(APIEntity.create(roleService.getRoleListForSelector()), HttpStatus.OK);
    }

  //根据部门和角色信息得到 用户（查询出某个部门下某个角色 的用户有哪些）
    @RequestMapping(value = "/roles/{roleId}/depts/{deptId}" ,method = RequestMethod.GET)
  public HttpEntity<APIEntity<List<User>>> getUserByRoleAndDept(@PathVariable("deptId") Long deptId,@PathVariable("roleId") Long roleId){

	return buildEntity(APIEntity.create(roleService.getUserByRoleAndDept(deptId,roleId)), HttpStatus.OK);
  }


  //移除用户（特定的角色和特定的部门下）
    @RequestMapping(value = "/roles/{roleId}/depts/{deptId}/users",method = RequestMethod.POST)
    public HttpEntity<APIEntity<Map>> removeUserByRoleAndDept(@PathVariable("deptId")Long deptId, @PathVariable("roleId")Long roleId, @RequestParam(value="usrIds[]") Long[] usrIds) {
      roleService.removeUserByRoleAndDept(usrIds,deptId,roleId);
      Map map = new HashMap();
      map.put("deptId",deptId);
      map.put("roleId",roleId);
      return buildEntity(APIEntity.create(map), HttpStatus.OK);
    }

}
