package org.quetzaco.archives.application.dao;

import org.apache.ibatis.annotations.Param;
import org.quetzaco.archives.model.AccessLog;
import org.quetzaco.archives.model.AccessLogExample;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessLogMapper {
    long countByExample(AccessLogExample example);

    int deleteByExample(AccessLogExample example);

    int insert(AccessLog record);

    int insertSelective(AccessLog record);

    List<AccessLog> selectByExample(AccessLogExample example);

    int updateByExampleSelective(@Param("record") AccessLog record, @Param("example") AccessLogExample example);

    int updateByExample(@Param("record") AccessLog record, @Param("example") AccessLogExample example);

    List<AccessLog> getAccessLogById(AccessLog accessLog);
}