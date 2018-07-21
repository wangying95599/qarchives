package org.quetzaco.archives.application.biz;

import java.util.List;

import org.junit.Test;
import org.quetzaco.archives.model.Documents;
import org.quetzaco.archives.model.Files;
import org.quetzaco.archives.qarchives.QarchivesApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;

public class DocumentServiceTest extends QarchivesApplicationTests{
	@Autowired
	DocumentService documentService;
	
	@Test
    public void searchGlobalRecordList() {
		Documents document =new Documents();
		document.setTitle("jdk");
	
		PageInfo page = documentService.searchGlobalDocumentList(document, 1, 10);
		List<Files> list = page.getList();
		for (Files f:list){
			System.out.println(f.getLocation());
		}
	}
}
