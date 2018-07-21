package org.quetzaco.archives.application.biz.Impl;

import org.quetzaco.archives.application.biz.FlowNodeHistoryService;
import org.quetzaco.archives.application.dao.*;
import org.quetzaco.archives.model.FlowNodeHistories;
import org.quetzaco.archives.model.ScheduleToOa;
import org.quetzaco.archives.util.config.PropelProperties;
import org.quetzaco.archives.util.propelToOA.WSTodoOABeanProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;

@Service
public class FlowNodeHistoryServiceImpl implements FlowNodeHistoryService {

    @Autowired
    FlowNodeHistoriesMapper flowNodeHistoriesMapper;
    @Autowired
    FlowFormLendingMapper flowFormLendingMapper;
    @Autowired
    FlowFormDeliverMapper flowFormDeliverMapper;
    @Autowired
    FlowFormDestoryMapper flowFormDestoryMapper;
    @Autowired
    FlowFormAssistMapper flowFormAssistMapper;
    @Autowired
    PropelProperties propelProperties;
    private final Logger LOOGER = LoggerFactory.getLogger(FlowNodeHistoryServiceImpl.class);

    @Override
    public int insert(FlowNodeHistories flowNodeHistories) {
        flowNodeHistoriesMapper.insert(flowNodeHistories);
        //通知待办已办
        if (propelProperties.isPropel()) {
            /*refrenceId   flow_node_id
            * processinstname  title
            * excutor       login_name
            * processinstid   flow_id
            * bizsystem      档案系统 KMHDAXT
            * actionurl*/
            Long nodeId = flowNodeHistories.getNodeId();
            ScheduleToOa sdto = new ScheduleToOa();
            sdto.setUsermode(propelProperties.getUsermode());
            sdto.setPassword(propelProperties.getPassword());
            sdto.setRefrenceId(nodeId.toString());
            sdto.setBizsystem(propelProperties.getBizsystem());
            updateFlagToOa(sdto);

        }
        return 0;
    }

    public void updateFlagToOa(ScheduleToOa scheduleToOa){
        LOOGER.debug("---------------------------------------- UPDATE TO OA  start ------------------");
        LOOGER.debug("scheduleToOa:"+scheduleToOa);
        WSTodoOABeanProxy proxy = new WSTodoOABeanProxy();
        try {
            int b = proxy.updateTodoFlag(scheduleToOa.getUsermode(), scheduleToOa.getPassword(), scheduleToOa.getRefrenceId(), scheduleToOa.getBizsystem());
            LOOGER.debug("---------------------------------------- UPDATE TO OA  end ------------------"+b);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}
