package org.quetzaco.archives.application.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.quetzaco.archives.qarchives.QarchivesApplicationTests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description Created by dong on 2017/8/23.
 */
public class OaDepartmentMapperTest extends QarchivesApplicationTests {

  final static Logger logger = LoggerFactory.getLogger(OaDepartmentMapperTest.class);
  @Autowired
  OaDepartmentMapper oaDepartmentMapper;

  @Test
  public void getDeptPrtId() throws Exception {
    oaDepartmentMapper.insertLinkDepOADepartment(1l, 0l);

    assertEquals((Long)0l,oaDepartmentMapper.getDeptPrtId(2l));
    assertEquals((Long)1l,oaDepartmentMapper.getDeptPrtId(0l));
  }

  @Test
  public void insertLinkDepOADepartment() throws Exception {
    oaDepartmentMapper.insertLinkDepOADepartment(0l, 0l);
  }

}