package org.quetzaco.archives.application.dao;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.quetzaco.archives.model.OaCodesheet;
import org.quetzaco.archives.model.OaCodesheetExample;
import org.springframework.stereotype.Repository;

@Repository
public interface OaCodesheetMapper {
    long countByExample(OaCodesheetExample example);

    int deleteByExample(OaCodesheetExample example);

    int deleteByPrimaryKey(Long operatorid);

    int insert(OaCodesheet record);

    int insertSelective(OaCodesheet record);

    List<OaCodesheet> selectByExample(OaCodesheetExample example);

    OaCodesheet selectByPrimaryKey(Long operatorid);

    int updateByExampleSelective(@Param("record") OaCodesheet record, @Param("example") OaCodesheetExample example);

    int updateByExample(@Param("record") OaCodesheet record, @Param("example") OaCodesheetExample example);

    int updateByPrimaryKeySelective(OaCodesheet record);

    int updateByPrimaryKey(OaCodesheet record);

    @Delete("delete from oa_codesheet")
    int deleteAll();

   }