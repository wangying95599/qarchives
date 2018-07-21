package org.quetzaco.archives.application.dao;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.quetzaco.archives.model.OaDepartment;
import org.quetzaco.archives.model.OaDepartmentExample;
import org.springframework.stereotype.Repository;

@Repository
public interface OaDepartmentMapper {

  long countByExample(OaDepartmentExample example);

  int deleteByExample(OaDepartmentExample example);

  int deleteByPrimaryKey(Long orgid);

  int insert(OaDepartment record);

  int insertSelective(OaDepartment record);

  List<OaDepartment> selectByExample(OaDepartmentExample example);

  OaDepartment selectByPrimaryKey(Long orgid);

  int updateByExampleSelective(@Param("record") OaDepartment record,
      @Param("example") OaDepartmentExample example);

  int updateByExample(@Param("record") OaDepartment record,
      @Param("example") OaDepartmentExample example);

  int updateByPrimaryKeySelective(OaDepartment record);

  int updateByPrimaryKey(OaDepartment record);

  @Delete("delete from oa_department")
  int deleteAll();

  /**
   * 父组织(1596)不做同步
   * @return
   */
  @Select("SELECT * from oa_department WHERE orgid not in (SELECT oa_department_id FROM link_dept_oa_department) order by orgseq ;")
  @ResultMap("org.quetzaco.archives.application.dao.OaDepartmentMapper.BaseResultMap")
  List<OaDepartment> selectNotExistsData();

  @Insert("insert into link_dept_oa_department (dept_id,oa_department_id) values (#{deptId},#{orgid})")
  @Options(useGeneratedKeys = false)
    // 解决使用默认主键生成器的错误
  int insertLinkDepOADepartment(@Param("deptId") Long deptId, @Param("orgid") Long orgid);

  @Select("SELECT dept_id from link_dept_oa_department WHERE oa_department_id = #{parentorgid}")
  Long getDeptPrtId(@Param("parentorgid") Long parentorgid);

  @Update("UPDATE dept set name = od.orgname , prt_id = (CASE "
      + "when ldod2.oa_department_id = #{oaBaseDepartmentId} then  NULL "
      + "else ldod2.dept_id END)"
      + " from  oa_department od INNER JOIN\n"
      + "link_dept_oa_department ldod  on od.orgid = ldod.oa_department_id\n"
      + "INNER JOIN  link_dept_oa_department ldod2  on od.parentorgid = ldod2.oa_department_id\n "
      + "WHERE  id = ldod.dept_id;")
  @Options(useGeneratedKeys = false)
    // 解决使用默认主键生成器的错误
  int updateExistsOAOrganization(@Param("oaBaseDepartmentId") long oaBaseDepartmentId);



}