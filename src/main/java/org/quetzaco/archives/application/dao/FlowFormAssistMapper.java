package org.quetzaco.archives.application.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.quetzaco.archives.model.Documents;
import org.quetzaco.archives.model.FlowFormAssist;
import org.quetzaco.archives.model.FlowFormAssistExample;
import org.quetzaco.archives.model.User;

public interface FlowFormAssistMapper {
    long countByExample(FlowFormAssistExample example);

    int deleteByExample(FlowFormAssistExample example);

    int insert(FlowFormAssist record);

    int insertSelective(FlowFormAssist record);

    List<FlowFormAssist> selectByExample(FlowFormAssistExample example);

    int updateByExampleSelective(@Param("record") FlowFormAssist record, @Param("example") FlowFormAssistExample example);

    int updateByExample(@Param("record") FlowFormAssist record, @Param("example") FlowFormAssistExample example);
    
    List<FlowFormAssist> selectStartByMeList(@Param("contextUser") User contextUser);
    
    List<FlowFormAssist> getTurnOverToMe(@Param("usrId") Long id,@Param("type") String type);
    
    FlowFormAssist getInfo(Long flowId);
    
    void saveDocument(@Param("flowId") Long  flowId,@Param("record_id") Long   record_id,@Param("doc_id") Long doc_id,@Param("document_local_id") String document_local_id,@Param("updated_dt") String updated_dt);
	 
	void deleteDocument(@Param("flowId") Long   flowId,@Param("record_id") Long   record_id,@Param("doc_id") Long doc_id,@Param("document_local_id") String document_local_id,@Param("updated_dt") String updated_dt);

    int updateLendTypeWhenLending(@Param("flowId") Long flowId, @Param("relationMap")Map relationMap);
//	 List<Documents> getDocument(@Param("flowId") Long   flowId);
//	List getRecord(@Param("flowId") Long   flowId);
}