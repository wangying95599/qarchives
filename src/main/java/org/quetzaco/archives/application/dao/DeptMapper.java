package org.quetzaco.archives.application.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.quetzaco.archives.model.Dept;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DeptMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Dept record);

    int insertSelective(Dept record);

    Dept selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Dept record);

    int updateByPrimaryKey(Dept record);

    List<Map> selectUsersByDeptID(Long deptId);

    List<Dept> selectDeptList(Long prtId);

    List<Dept> selectDeptListForNull();

    int insertUserToDept(@Param("userId")Long userId,@Param("deptId")Long deptId);

    List<Dept> getDeptsByUserAdmin(Long usrId);

    Dept getDeptByNameAndPrtId(Dept dept);

    Long getDeptIdForSite();

    int updateDeptsForUser(@Param("deptId") Long deptId,@Param("usrId") Long usrId);

    @Select({
        "select id,name,prt_id,description from dept where id in (select dept_id from link_dept_users where usr_id = #{usrId}) and prt_id is null and description notnull order by description"})
    @ResultMap("org.quetzaco.archives.application.dao.DeptMapper.BaseResultMap")
    List<Dept> selectDeptListByUser(Long usrId);

    @Select({"select id,name,prt_id,description from dept where description = #{description}"})
    @ResultMap("org.quetzaco.archives.application.dao.DeptMapper.BaseResultMap")
    Dept getDeptByDes(String description);

    @Select({"select id from dept where id in (select distinct prt_id from dept) "})
    List<Long> getHaveChildDept();
}