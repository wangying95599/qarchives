package org.quetzaco.archives.application.biz;

import org.junit.Test;
import org.quetzaco.archives.model.User;
import org.quetzaco.archives.qarchives.QarchivesApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

/**
 * @Description Created by dong on 2017/7/21.
 */
public class FlowAssistServiceTest extends QarchivesApplicationTests {
    @Autowired
    FlowAssistService flowService;

    @Test
    @Rollback(false)
    public void saveDocument() throws Exception {
      Long flowId=111L;
      String id="7605_";
      String type="recdoc";
      flowService.saveDocument(flowId, id, type);
      
    }
    
    @Test
    public void getDocument() throws Exception {
      Long flowId=30L;
      flowService.getDocument(flowId);
      
    }

    @Test
    @Rollback(false)
    public void deleteDocument() throws Exception {
    	Long flowId=111L;
        String id="doc123";
        String type="doc";
        flowService.deleteDocument(flowId, id, type);
    }
    @Test
    @Rollback(false)
    public void process() {
    	User user = new User();
    	user.setId(2L);
    	Long flowId=30L;
    	String action="to_CAdmin";
    	Long userId=2221L;
    	flowService.process(user, flowId, action, userId);
    }
}