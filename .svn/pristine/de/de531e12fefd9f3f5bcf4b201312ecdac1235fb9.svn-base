package org.quetzaco.archives.application.dao;

import org.apache.ibatis.annotations.Param;
import org.quetzaco.archives.model.Swift;
import org.quetzaco.archives.model.SwiftExample;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwiftMapper {
    long countByExample(SwiftExample example);

    int deleteByExample(SwiftExample example);

    int deleteByPrimaryKey(String prefix);

    int insert(Swift record);

    int insertSelective(Swift record);

    List<Swift> selectByExample(SwiftExample example);

    Swift selectByPrimaryKey(String prefix);

    int updateByExampleSelective(@Param("record") Swift record, @Param("example") SwiftExample example);

    int updateByExample(@Param("record") Swift record, @Param("example") SwiftExample example);

    int updateByPrimaryKeySelective(Swift record);

    int updateByPrimaryKey(Swift record);
}