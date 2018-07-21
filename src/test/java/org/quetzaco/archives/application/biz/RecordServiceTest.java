package org.quetzaco.archives.application.biz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.quetzaco.archives.model.AcousticImage;
import org.quetzaco.archives.model.Documents;
import org.quetzaco.archives.model.PageSortSearch;
import org.quetzaco.archives.model.Record;
import org.quetzaco.archives.qarchives.QarchivesApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;

public class RecordServiceTest extends QarchivesApplicationTests{
	@Autowired
	RecordService recordService;
	
	@Test
    public void searchGlobalRecordList() {
		try {
			Record record = new Record ();
		  record.setArchiveType("ALL");
		  record.setFileNum("005");
//		  record.setTitle("测试");
//		  record.setReserveDuration("5年");
//		  record.setStartYear("2017-11-17");
//		  record.setEndYear("2017-12-17");
//		  record.setImportance("一般");
//		  record.setThemeWord("1");
//		  record.setSaveNum(123L);
//		  record.setReserveLocation("存放");
		  
		  PageSortSearch pss =new PageSortSearch();
		  record.setPageSortSearch(pss);
		  pss.setPageNum(1);
		  pss.setPageSize(10);
		  
		  Documents doc = new Documents();
		  doc.setDocAttr("docAttr");
		
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  
		  AcousticImage acousticImage = new AcousticImage();
		  acousticImage.setPlace("123");
		  acousticImage.setFigure("123");
		  acousticImage.setPhotographer("123");
		
		  acousticImage.setPhotographyTimeStr(sdf.format(new Date()));
		  acousticImage.setNumber(123);
		
		     PageInfo page = recordService.searchGlobalRecordList(record,null,null, 0, 100);
		     System.out.println("searchGlobalRecordListTest            "+page);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
}
