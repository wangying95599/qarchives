package org.quetzaco.archives.application.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.quetzaco.archives.model.HistoryDocument;
import org.quetzaco.archives.model.HistoryDocumentExample;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryDocumentMapper {
    long countByExample(HistoryDocumentExample example);

    int deleteByExample(HistoryDocumentExample example);

    int deleteByPrimaryKey(String id);

    int insert(HistoryDocument record);

    int insertSelective(HistoryDocument record);

    List<HistoryDocument> selectByExample(HistoryDocumentExample example);

    List<HistoryDocument> selectByExampleWenShu(HistoryDocumentExample example);

    List<HistoryDocument> selectByExampleWeiGuiDang(HistoryDocumentExample example);

    List<HistoryDocument> selectByExampleShengXiang(HistoryDocumentExample example);

    List<HistoryDocument> selectByExampleShiWu(HistoryDocumentExample example);

    HistoryDocument selectByExampleTiaoMu(HistoryDocumentExample example);
    HistoryDocument selectByPrimaryKey(String id);

    String download(String fildId);
    int updateByExampleSelective(@Param("record") HistoryDocument record, @Param("example") HistoryDocumentExample example);

    int updateByExample(@Param("record") HistoryDocument record, @Param("example") HistoryDocumentExample example);

    int updateByPrimaryKeySelective(HistoryDocument record);

    int updateByPrimaryKey(HistoryDocument record);
}