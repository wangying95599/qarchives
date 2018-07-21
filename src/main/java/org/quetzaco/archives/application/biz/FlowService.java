package org.quetzaco.archives.application.biz;

import org.quetzaco.archives.model.FlowNodeHistories;
import org.quetzaco.archives.model.Flows;
import org.quetzaco.archives.model.ScheduleToOa;

public interface FlowService {
    Long start(Flows flow,String title);

    void proccess(Flows flow, FlowNodeHistories flowNodeHistories);

    void end(Flows flow, FlowNodeHistories flowNodeHistories);

    Flows history(Flows flows);

    Flows detail(Flows flows);

    int confirmGiveBack(Long flowId);


}