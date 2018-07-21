package org.quetzaco.archives.application.dao;

import static org.junit.Assert.assertNotEquals;

import java.util.List;
import org.junit.Test;
import org.quetzaco.archives.model.OaDepartment;
import org.quetzaco.archives.model.OaPost;
import org.quetzaco.archives.qarchives.QarchivesApplicationTests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description Created by dong on 2017/8/23.
 */
public class OaPostMapperTest  extends QarchivesApplicationTests{
  final static Logger logger = LoggerFactory.getLogger(OaPostMapperTest.class);

  @Autowired
  OaPostMapper oaPostMapper;
  @Test
  public void selectNotExistsData() throws Exception {
    List<OaPost> oaDepartments = oaPostMapper.selectNotExistsData();
    assertNotEquals(null,oaDepartments);
    assertNotEquals(0,oaDepartments.size());
   // logger.debug("oaDepartments has {0} rows",oaDepartments.size());
  }

}