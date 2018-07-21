package org.quetzaco.archives.application.dao;

import org.apache.ibatis.annotations.Param;
import org.quetzaco.archives.model.FlowNodeHistories;
import org.quetzaco.archives.model.FlowNodeHistoriesExample;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FlowNodeHistoriesMapper {
    long countByExample(FlowNodeHistoriesExample example);

    int deleteByExample(FlowNodeHistoriesExample example);

    int insert(FlowNodeHistories record);

    int insertSelective(FlowNodeHistories record);

    List<FlowNodeHistories> selectByExample(FlowNodeHistoriesExample example);

    int updateByExampleSelective(@Param("record") FlowNodeHistories record, @Param("example") FlowNodeHistoriesExample example);

    int updateByExample(@Param("record") FlowNodeHistories record, @Param("example") FlowNodeHistoriesExample example);

    List<Map> getFlowStatus(Long flowId);
}