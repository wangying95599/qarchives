package org.quetzaco.archives.application.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.quetzaco.archives.model.FlowNodes;
import org.quetzaco.archives.model.FlowNodesExample;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowNodesMapper {
    long countByExample(FlowNodesExample example);

    int deleteByExample(FlowNodesExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FlowNodes record);

    int insertSelective(FlowNodes record);

    List<FlowNodes> selectByExample(FlowNodesExample example);

    FlowNodes selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FlowNodes record, @Param("example") FlowNodesExample example);

    int updateByExample(@Param("record") FlowNodes record, @Param("example") FlowNodesExample example);

    int updateByPrimaryKeySelective(FlowNodes record);

    int updateByPrimaryKey(FlowNodes record);
}