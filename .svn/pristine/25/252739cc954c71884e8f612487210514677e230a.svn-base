package org.quetzaco.archives.application.dao;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.quetzaco.archives.model.OaPost;
import org.quetzaco.archives.model.OaPostExample;
import org.springframework.stereotype.Repository;

@Repository
public interface OaPostMapper {

  long countByExample(OaPostExample example);

  int deleteByExample(OaPostExample example);

  int deleteByPrimaryKey(Long operatorid);

  int insert(OaPost record);

  int insertSelective(OaPost record);

  List<OaPost> selectByExample(OaPostExample example);

  OaPost selectByPrimaryKey(Long operatorid);

  int updateByExampleSelective(@Param("record") OaPost record,
      @Param("example") OaPostExample example);

  int updateByExample(@Param("record") OaPost record, @Param("example") OaPostExample example);

  int updateByPrimaryKeySelective(OaPost record);

  int updateByPrimaryKey(OaPost record);

  @Delete("delete from oa_post")
  int deleteAll();

  @Select("SELECT * from oa_post WHERE operatorid not in (SELECT oa_post_id FROM link_users_oa_post);")
  @ResultMap("org.quetzaco.archives.application.dao.OaPostMapper.BaseResultMap")
  List<OaPost> selectNotExistsData();

  @Insert("insert into link_users_oa_post (usr_id,oa_post_id) values (#{usrId},#{oaPostId})")
  @Options(useGeneratedKeys = false)
    // 解决使用默认主键生成器的错误
  int insertLinkUsersOaPost(@Param("usrId") Long usrId, @Param("oaPostId") Long oaPostId);

  @Update("update users set login_name = oa.userid,updated_dt = now(),name = oa.operatorname from oa_post oa INNER JOIN link_users_oa_post luop on oa.operatorid = luop.oa_post_id WHERE id = luop.usr_id")
  @Options(useGeneratedKeys = false)
  int updateExistsOaUsers();

  @Insert("INSERT INTO link_dept_users (usr_id,dept_id)\n"
      + "select  luop.usr_id ,ldod.dept_id from link_users_oa_post luop INNER JOIN oa_post op  on op.operatorid = luop.oa_post_id INNER JOIN link_dept_oa_department ldod on ldod.oa_department_id = op.orgid")
  @Options(useGeneratedKeys = false)
  int insertOAUserDepts();

  @Delete("delete from link_dept_users where usr_id in (select usr_id from link_users_oa_post)")
  @Options(useGeneratedKeys = false)
  int deleteAllOAUserDepts();
  /**
   * 插入所有不在根部门的
   * @return
   */
  @Insert("INSERT INTO link_dept_users(usr_id, dept_id)\n"
      + "SELECT luop.usr_id ,d.id\n"
      + "FROM (dept d INNER JOIN link_dept_oa_department ldod ON ldod.dept_id = d.id AND d.prt_id IS NULL\n"
      + "  INNER JOIN (select * from oa_department x order by x.orgseq asc OFFSET 1) as od ON ldod.oa_department_id = od.orgid\n"
      + "  ) INNER JOIN oa_department od2  on od2.orgseq LIKE od.orgseq||'%.'\n"
      + " INNER JOIN oa_post op on op.orgid = od2.orgid\n"
      + "INNER JOIN  link_users_oa_post luop  on luop.oa_post_id = op.operatorid\n"
      + " ORDER BY  d.id ;")
  @Options(useGeneratedKeys = false)
  int importUsersToRootDept();

}