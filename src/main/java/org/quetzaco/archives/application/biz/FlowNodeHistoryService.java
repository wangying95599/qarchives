package org.quetzaco.archives.application.biz;

import org.quetzaco.archives.application.dao.FlowNodeHistoriesMapper;
import org.quetzaco.archives.model.FlowNodeHistories;
import org.quetzaco.archives.model.ScheduleToOa;
import org.springframework.beans.factory.annotation.Autowired;

public interface FlowNodeHistoryService{
    int insert(FlowNodeHistories flowNodeHistories);
    void updateFlagToOa(ScheduleToOa scheduleToOa);
}
