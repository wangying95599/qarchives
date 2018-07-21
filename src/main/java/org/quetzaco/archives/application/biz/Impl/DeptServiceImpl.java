package org.quetzaco.archives.application.biz.Impl;

import org.apache.commons.collections4.ListUtils;
import org.quetzaco.archives.application.biz.DeptService;
import org.quetzaco.archives.application.dao.DeptMapper;
import org.quetzaco.archives.application.dao.RoleMapper;
import org.quetzaco.archives.model.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by deya on 2017/7/11.
 */
@Service
public class DeptServiceImpl implements DeptService {

  @Autowired
  DeptMapper deptMapper;

  @Autowired
  RoleMapper roleMapper;
  @Value("${BOSSDEPTID}")
    Long BOSSDEPTID;
  @Value("${BOSSDEPTNAME}")
  String BOSSDEPTNAME;
  @Override
  public List<Map> getDeptUsers(Long deptId) {
      List<Map> list =deptMapper.selectUsersByDeptID(deptId);
      for (Map map : list) {
          if ("0".equals(map.get("state"))) {
              map.put("state", "激活");
          } else {
              map.put("state","关闭");
          }
          if(map.get("roleName")==null){
              map.put("roleName","普通用户");
          }
      }
      return list;
  }

    @Override
    public List<Dept> getDeptList(Long prtId) {
        List<Dept> depts;
        if (prtId != null) {
            depts = deptMapper.selectDeptList(prtId);
        } else {
            depts = deptMapper.selectDeptListForNull();
            Dept dept = new Dept();
            dept.setId(BOSSDEPTID);
            dept.setName("总裁室");
            depts.add(dept);
        }
        if (depts == null || depts.size() == 0) return null;
        addHasChilds(depts);
        return depts;
    }

    public void addHasChilds(List<Dept> depts) {
        List<Long> ids = deptMapper.getHaveChildDept();
        for (Dept dept : depts) {
            if (ids.contains(dept.getId())) {
                dept.setHasChilds(true);
            } else {
                dept.setHasChilds(false);
            }
        }
    }

    @Override
  public void addUserToDept(Long userId, Long deptId) {

    deptMapper.insertUserToDept(userId, deptId);
  }

    @Override
    public List<Dept> getDeptsByUser(Long usrId) {
        List<Dept> depts = deptMapper.getDeptsByUserAdmin(usrId);
        if (ListUtils.emptyIfNull(depts).size() > 0 && !"ALL".equals(depts.get(0).getName())) {
            addHasChilds(depts);
            return depts;
        }

        List<Dept> deptsList;
        if (ListUtils.emptyIfNull(depts).size() > 0 && "ALL".equals(depts.get(0).getName())) {
            deptsList = deptMapper.selectDeptListForNull();
        } else {
            deptsList = deptMapper.selectDeptListByUser(usrId);
        }
        addHasChilds(deptsList);
        return deptsList;
    }

  @Override
  public void createDept(Dept dept) {
	deptMapper.insertSelective(dept);
  }

  @Override
  public void updateDept(Dept dept) {
	deptMapper.updateByPrimaryKeySelective(dept);
  }

  @Override
  public Dept getDeptByNameAndPrtId(Dept dept) {
	return deptMapper.getDeptByNameAndPrtId(dept);
  }

    @Override
    public void updateDeptsForUser(Long oldDeptId,Long deptId, Long usrId) {
        deptMapper.updateDeptsForUser(deptId,usrId);
        roleMapper.deleteRoleByDeptAndUser(oldDeptId,usrId);
    }

    @Override
    public Dept getDeptById(Long deptId) {
        return deptMapper.selectByPrimaryKey(deptId);
    }

    @Override
    public Dept getDeptByDes(String description) {
        return deptMapper.getDeptByDes(description);
    }


  /*@Override
  public PageInfo getDeptUsers(Long deptId, int offset, int limit) {
    PageHelper.startPage(offset, limit);
    return new PageInfo(getDeptUsers(deptId));
  }*/


}
