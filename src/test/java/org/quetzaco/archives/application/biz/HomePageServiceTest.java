package org.quetzaco.archives.application.biz;

import org.junit.Test;
import org.quetzaco.archives.qarchives.QarchivesApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;

public class HomePageServiceTest extends QarchivesApplicationTests {


  @Autowired
  HomePageService homePageService;


  @Test
  public void insert() throws Exception {
    homePageService.insert("aaa", "link","1234567");
  }

}