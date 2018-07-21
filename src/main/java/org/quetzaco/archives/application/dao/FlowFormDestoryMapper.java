package org.quetzaco.archives.application.dao;

import org.apache.ibatis.annotations.Param;
import org.quetzaco.archives.model.FlowFormDestory;
import org.quetzaco.archives.model.FlowFormDestoryExample;
import org.quetzaco.archives.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FlowFormDestoryMapper {

  long countByExample(FlowFormDestoryExample example);

  int deleteByExample(FlowFormDestoryExample example);

  int insert(FlowFormDestory record);

  int insertSelective(FlowFormDestory record);

  List<FlowFormDestory> selectByExample(FlowFormDestoryExample example);

  int updateByExampleSelective(@Param("record") FlowFormDestory record,
      @Param("example") FlowFormDestoryExample example);

  int updateByExample(@Param("record") FlowFormDestory record,
      @Param("example") FlowFormDestoryExample example);

  List<FlowFormDestory> selectStartByMeList(@Param("contextUser") User contextUser);

  List<FlowFormDestory> selectProcessByMe(@Param("usrId") Long usrId, @Param("isProcessed") Boolean isProcessed);

    List<FlowFormDestory> getDeletedFlow(@Param("deptId") Long deptId,@Param("isManager") boolean isManager,@Param("usrId")Long userId);

  FlowFormDestory getFlowDestroyForRemain(Long flowId);

  List<Map> getsubDeletedFlow(Long flowId);
}