package org.quetzaco.archives.application.dao;

import org.apache.ibatis.annotations.Param;
import org.quetzaco.archives.model.Flows;
import org.quetzaco.archives.model.FlowsExample;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FlowsMapper {
    long countByExample(FlowsExample example);

    int deleteByExample(FlowsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Flows record);

    int insertSelective(Flows record);

    List<Flows> selectByExample(FlowsExample example);

    Flows selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Flows record, @Param("example") FlowsExample example);

    int updateByExample(@Param("record") Flows record, @Param("example") FlowsExample example);

    int updateByPrimaryKeySelective(Flows record);

    int updateByPrimaryKey(Flows record);

    List<Map> remainToBeDone(Long usrId);

    List<Map> remainGiveBack(Long usrId);

    List<Map> remainGiveBackToCreatedBy();

    int updatePropelFlag(@Param("flowId") Long id,@Param("sdtoGuid")String sdtoGuid,@Param("flag") String flag);

    int updatePropelFlagNoGuid(@Param("flowId") Long id, @Param("flag") String flag);

    String getSdtoGuidByFlowId(Long flowId);
}