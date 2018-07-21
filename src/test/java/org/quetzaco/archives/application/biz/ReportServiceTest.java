package org.quetzaco.archives.application.biz;

import java.util.List;

import org.junit.Test;
import org.quetzaco.archives.qarchives.QarchivesApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

/**
 * 
 */
public class ReportServiceTest extends QarchivesApplicationTests {
    @Autowired
    ReportService reportService;

    @Test
    @Rollback(false)
    public void reportForYear() throws Exception {
	      Long deptId=137L;
	      String start="1990";
	      String end="2017";
	      List map= reportService.reportForYearJuan(deptId, start, end);
	      System.out.println(map);
	      map= reportService.reportForYearJian(deptId, start, end);
	      System.out.println(map);
    }
    
    @Test
    @Rollback(false)
    public void reportForDuration() throws Exception {
	      Long deptId=137L;
	      String start="1990";
	      String end="2017";
	      List map= reportService.reportForDurationJuan(deptId, start, end);
	      System.out.println(map);
	      map= reportService.reportForDurationJian(deptId, start, end);
	      System.out.println(map);
    }
    
    @Test
    @Rollback(false)
    public void reportForUse() throws Exception {
	      Long deptId=137L;
	      String start="1990";
	      String end="2017";
	      List map= reportService.reportForUseJuan(deptId, start, end);
	      System.out.println(map);
	      map= reportService.reportForUseJian(deptId, start, end);
	      System.out.println(map);
    }
}