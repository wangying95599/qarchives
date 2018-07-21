package org.quetzaco.archives.application.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.quetzaco.archives.model.FlowFormLending;
import org.quetzaco.archives.model.FlowFormLendingExample;
import org.quetzaco.archives.model.User;

public interface FlowFormLendingMapper {
    long countByExample(FlowFormLendingExample example);

    int deleteByExample(FlowFormLendingExample example);

    int insert(FlowFormLending record);

    int insertSelective(FlowFormLending record);

    List<FlowFormLending> selectByExample(FlowFormLendingExample example);

    int updateByExampleSelective(@Param("record") FlowFormLending record, @Param("example") FlowFormLendingExample example);

    int updateByExample(@Param("record") FlowFormLending record, @Param("example") FlowFormLendingExample example);

    List<FlowFormLending> selectStartByMeList(@Param("contextUser") User contextUser, @Param("title")String title, @Param("reelNum")String reelNum,
                                              @Param("start")Date start, @Param("end")Date end);

    List<FlowFormLending> selectProcessByMe(@Param("usrId")Long usrId, @Param("isProcessed")Boolean isProcessed, @Param("searchMap")Map map,
                                            @Param("start")Date start ,@Param("end")Date end);

    FlowFormLending selectByPrimaryKey(Long flowId);

    FlowFormLending getLendingInfo(Long flowId);
}