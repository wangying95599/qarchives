package org.quetzaco.archives.application.biz.sync.oa.Impl;

import org.junit.Test;
import org.quetzaco.archives.application.biz.sync.oa.OAOrganizationService;
import org.quetzaco.archives.qarchives.QarchivesApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;

/**
 * @Description Created by dong on 2017/8/21.
 */

public class OAOrganizationServiceImplTest extends QarchivesApplicationTests {

  @Autowired
  OAOrganizationService oaOrganizationService;

  @Test
  @Commit
  public void syncOAData() throws Exception {
    oaOrganizationService.syncOAData();
  }

  @Test
  public void clearOAData() throws Exception {
    oaOrganizationService.clearOAData();
  }

  @Test
  @Commit
  public void getAllOAData() throws Exception {
    oaOrganizationService.getAllOAData();
  }

}