package org.quetzaco.archives.application.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.quetzaco.archives.model.Documents;
import org.quetzaco.archives.model.FlowFormDeliver;
import org.quetzaco.archives.model.FlowFormDeliverExample;
import org.quetzaco.archives.model.Record;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface FlowFormDeliverMapper {
    long countByExample(FlowFormDeliverExample example);

    int deleteByExample(FlowFormDeliverExample example);

    int insert(FlowFormDeliver record);

    int insertSelective(FlowFormDeliver record);

    List<FlowFormDeliver> selectByExample(FlowFormDeliverExample example);

    int updateByExampleSelective(@Param("record") FlowFormDeliver record, @Param("example") FlowFormDeliverExample example);

    int updateByExample(@Param("record") FlowFormDeliver record, @Param("example") FlowFormDeliverExample example);

    List<FlowFormDeliver> getTurnOverFromMe(Long id);

    List<FlowFormDeliver> getTurnOverToMe(@Param("usrId") Long id,@Param("type") String type);

    int addDeliverModelBatch(List<Map> list);

    List<Documents> getTurnOverDocList(Long id);

    List<Record> getTurnOverRecList(Long id);

    FlowFormDeliver getDeliverInfo(Long flowId);

    List<Long> getDuplicateInTurnOver(@Param("fileType") String fileType, @Param("docIds") Long[] docIds);

    int updateNewFileNum(@Param("flowId") Long flowId, @Param("docId") Long docId, @Param("newFileNum") String fileNum, @Param("fileType") String documents);
}