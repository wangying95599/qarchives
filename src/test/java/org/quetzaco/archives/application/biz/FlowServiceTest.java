package org.quetzaco.archives.application.biz;

import org.apache.commons.lang3.time.DateUtils;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.quetzaco.archives.model.FlowNodes;
import org.quetzaco.archives.model.Flows;
import org.quetzaco.archives.qarchives.QarchivesApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * @Description Created by dong on 2017/7/21.
 */
public class FlowServiceTest extends QarchivesApplicationTests {
    @Autowired
    FlowService flowService;

    @Test
    public void history() throws Exception {
        Flows flows = new Flows();
        flows.setId(1l);
       assertEquals(0, flowService.history(flows).getNodeHistories().size());


    }

    @Test
    public void start() throws Exception {

        Flows flows = new Flows();
        flows.setCreatedBy(1l);
        flows.setDeadLine(DateUtils.addDays(new Date(), 1));
        flows.setEndDt(null);
        flows.setRecordFlag(true);
        flows.setStatus("IN_PROCESS");
        flows.setType("Lending");
        flows.setCreatedDt(new Date());

        FlowNodes flowNodes = new FlowNodes();
        flowNodes.setAssigneeBy(1l);
        flowNodes.setAssigneeId(1l);
        flowNodes.setCreatedDt(new Date());
        flowNodes.setType("Lending");
        flowNodes.setDeadLine(DateUtils.addDays(new Date(), 1));

        flows.setNodes(Lists.newArrayList(flowNodes));

        flowService.start(flows);
        assertEquals((Long) 5l, flows.getId());

    }

}