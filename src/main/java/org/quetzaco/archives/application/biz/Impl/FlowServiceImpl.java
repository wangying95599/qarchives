package org.quetzaco.archives.application.biz.Impl;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.quetzaco.archives.application.biz.FlowNodeHistoryService;
import org.quetzaco.archives.application.biz.FlowNodesService;
import org.quetzaco.archives.application.biz.FlowService;
import org.quetzaco.archives.application.dao.FlowNodeHistoriesMapper;
import org.quetzaco.archives.application.dao.FlowNodesMapper;
import org.quetzaco.archives.application.dao.FlowsMapper;
import org.quetzaco.archives.model.*;
import org.quetzaco.archives.util.config.PropelProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @Description Created by dong on 2017/7/21.
 */
@Service
public class FlowServiceImpl implements FlowService {

    @Autowired
    FlowsMapper flowsMapper;
    @Autowired
    FlowNodesMapper flowNodesMapper;
    @Autowired
    FlowNodeHistoriesMapper flowNodeHistoriesMapper;
    @Autowired
    FlowNodesService flowNodesService;
    @Autowired
    FlowNodeHistoryService flowNodeHistoryService;
    @Autowired
    PropelProperties propelProperties;

    @Transactional
    @Override
    public Long start(Flows flow ,String title) {
        if (flow == null) {
            throw new RuntimeException("flow is null ");
        }

        if ((!"COMPLETE".equals(flow.getStatus()))&&(flow.getNodes() == null || flow.getNodes().size() == 0)) {
            throw new RuntimeException("node list is null ");
        }

        flowsMapper.insert(flow);

        flow.getNodes().forEach(flowNodes -> {
            flowNodes.setFlowId(flow.getId());
            flowNodesService.insert(flowNodes,title);
        });

        return flow.getId();


    }

    @Transactional
    @Override
    public void proccess(Flows flow, FlowNodeHistories flowNodeHistories) {

        if (flowNodeHistories == null || flowNodeHistories.getNodeId() == null) {
            throw new RuntimeException("flowNodeHistories IS NULL or not exists ");
        }

        if (flowNodeHistories.getAssigneeId() == null) {
            throw new RuntimeException("flowNodeHistories getAssigneeId IS NULL");
        }
        if (flowNodeHistories.getAssigneeBy() == null) {
            throw new RuntimeException("flowNodeHistories getAssigneeBy IS NULL");
        }

        if (flow == null || flow.getId() == null) {
            throw new RuntimeException("flow is null or not exists");
        }
        if (flow.getType() == null) {
            throw new RuntimeException("flow getType is null ");
        }

        flowNodeHistoryService.insert(flowNodeHistories);

        flowNodesMapper.deleteByPrimaryKey(flowNodeHistories.getNodeId());

        FlowNodesExample flowNodesExample = new FlowNodesExample();
        flowNodesExample.createCriteria().andFlowIdEqualTo(flow.getId());

        if (0 == flowNodesMapper.countByExample(flowNodesExample)) {

            if (flow.getNodes() != null && flow.getNodes().size() > 0) {
                flow.getNodes().forEach(flowNodes -> {
                    if (flow.getNodes().size() == 1) {
                        flowNodes.setNeedAssigneAction(true);
                    }
                    flowNodes.setFlowId(flow.getId());
                    flowNodesService.insert(flowNodes);

                });
            } else {
                FlowNodes flowNodes = new FlowNodes();
                flowNodes.setDeadLine(DateUtils.addDays(new Date(), 1));
                flowNodes.setType(flow.getType());
                flowNodes.setCreatedDt(new Date());
                flowNodes.setAssigneeId(flowNodeHistories.getAssigneeBy());
                flowNodes.setAssigneeBy(flowNodeHistories.getAssigneeId());
                flowNodes.setFlowId(flow.getId());
                flowNodes.setNeedAssigneAction(true);
                flowNodesService.insert(flowNodes);
            }
        }
    }

    @Transactional
    @Override
    public void end(Flows flow, FlowNodeHistories flowNodeHistories) {
        if (flowNodeHistories == null || flowNodeHistories.getNodeId() == null) {
            throw new RuntimeException("flowNodeHistories IS NULL or not exists ");
        }

        if (flowNodeHistories.getAssigneeId() == null) {
            throw new RuntimeException("flowNodeHistories getAssigneeId IS NULL");
        }
        if (flowNodeHistories.getAssigneeBy() == null) {
            throw new RuntimeException("flowNodeHistories getAssigneeBy IS NULL");
        }
        if (flow == null || flow.getId() == null) {
            throw new RuntimeException("flow is null or not exists");
        }

        if (StringUtils.isEmpty(flow.getResult())) {
            throw new RuntimeException("flow  getResult is null ");
        }

        flowNodeHistoryService.insert(flowNodeHistories);

        flow.setStatus("COMPLETE");

        FlowNodesExample flowNodesExample = new FlowNodesExample();
        flowNodesExample.createCriteria().andFlowIdEqualTo(flow.getId());
        flowNodesMapper.deleteByExample(flowNodesExample);
        flow.setEndDt(new Date());
        flowsMapper.updateByPrimaryKeySelective(flow);

    }

    @Override
    public Flows history(Flows flows) {
        if (flows == null || flows.getId() == null) {
            throw new RuntimeException("flow is null or not exists");
        }
        FlowNodeHistoriesExample example = new FlowNodeHistoriesExample();
        example.createCriteria().andFlowIdEqualTo(flows.getId());
        flows.setNodeHistories(flowNodeHistoriesMapper.selectByExample(example));
        return flows;
    }

    @Override
    public Flows detail(Flows flows) {
        if (flows == null || flows.getId() == null) {
            throw new RuntimeException("flow is null or not exists");
        }
        return flowsMapper.selectByPrimaryKey(flows.getId());
    }

    @Override
    public int confirmGiveBack(Long flowId) {
        flowsMapper.updatePropelFlagNoGuid(flowId, "02");
        if(propelProperties.isPropel()){
            String sdtoGuid = flowsMapper.getSdtoGuidByFlowId(flowId);
            if(StringUtils.isEmpty(sdtoGuid)){
                ScheduleToOa sd = new ScheduleToOa();
                sd.setRefrenceId(sdtoGuid);
                sd.setUsermode(propelProperties.getUsermode());
                sd.setPassword(propelProperties.getPassword());
                sd.setBizsystem(propelProperties.getBizsystem());
                flowNodeHistoryService.updateFlagToOa(sd);
            }
        }
        Flows flows = new Flows();
        flows.setResult("GIVEBACK");
        flows.setId(flowId);
        return flowsMapper.updateByPrimaryKeySelective(flows);
    }

}
