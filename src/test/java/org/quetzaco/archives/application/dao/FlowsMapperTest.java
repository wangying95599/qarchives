package org.quetzaco.archives.application.dao;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.quetzaco.archives.model.Flows;
import org.quetzaco.archives.qarchives.QarchivesApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @Description Created by dong on 2017/7/21.
 */
public class FlowsMapperTest extends QarchivesApplicationTests {
    @Autowired
    FlowsMapper flowsMapper;
    @Test
    public void insert() throws Exception {
        Flows flows = new Flows();
        flows.setCreatedBy(1l);
        flows.setDeadLine(DateUtils.addDays(new Date(),1));
        flows.setEndDt(null);
        flows.setRecordFlag(true);
        flows.setStatus("IN_PROCESS");
        flows.setType("Lending");
        flows.setCreatedDt(new Date());
        flowsMapper.insert(flows);
        assertEquals((Long)4l,flows.getId());
    }

}