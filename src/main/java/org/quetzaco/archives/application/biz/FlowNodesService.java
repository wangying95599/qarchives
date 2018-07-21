package org.quetzaco.archives.application.biz;

import com.sun.tools.javac.comp.Flow;
import org.quetzaco.archives.model.FlowNodes;
import org.quetzaco.archives.model.ScheduleToOa;
import org.quetzaco.archives.model.User;

import java.util.List;
import java.util.Map;

public interface FlowNodesService {

    //根据 用户id(assignId)  和flowId ,assignBy获得 flowNode 对象
    List<FlowNodes> getNodesByFlowAndUser(Long FlowId, Long usrId);

    List<Map> getFlowStatus(Long flowId);

    int insert(FlowNodes flowNodes);

    int insert(FlowNodes flowNodes, String title);

    void propelToOa(ScheduleToOa scheduleToOa);


}
