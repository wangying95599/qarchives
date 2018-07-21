package org.quetzaco.archives.application.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.quetzaco.archives.model.HistoryFile;
import org.quetzaco.archives.model.HistoryFileExample;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryFileMapper {
    long countByExample(HistoryFileExample example);

    int deleteByExample(HistoryFileExample example);

    int deleteByPrimaryKey(String fileId);

    int insert(HistoryFile record);

    int insertSelective(HistoryFile record);

    List<HistoryFile> selectByExample(HistoryFileExample example);

    List<HistoryFile> selectHistoryFile(String id);
    HistoryFile selectByPrimaryKey(String fileId);

    int updateByExampleSelective(@Param("record") HistoryFile record, @Param("example") HistoryFileExample example);

    int updateByExample(@Param("record") HistoryFile record, @Param("example") HistoryFileExample example);

    int updateByPrimaryKeySelective(HistoryFile record);

    int updateByPrimaryKey(HistoryFile record);
}