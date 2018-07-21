package org.quetzaco.archives.web.restful;

import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.time.DateUtils;
import org.quetzaco.archives.application.biz.DocumentService;
import org.quetzaco.archives.application.biz.FlowAssistService;
import org.quetzaco.archives.application.biz.FlowNodesService;
import org.quetzaco.archives.model.FlowFormAssist;
import org.quetzaco.archives.model.FlowNodes;
import org.quetzaco.archives.model.Flows;
import org.quetzaco.archives.model.User;
import org.quetzaco.archives.model.api.APIEntity;
import org.quetzaco.archives.web.config.session.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class FlowAssistController extends BaseRestContoller {

    @Autowired
    FlowAssistService flowAssistService;
    @Autowired
    FlowNodesService flowNodesService;
    @Autowired
    DocumentService documentService;
    
    //移交发起
    @RequestMapping(value = "/flowFormAssist",method = RequestMethod.POST)
    public HttpEntity<APIEntity> start(@SessionAttribute(WebSecurityConfig.SESSION_KEY)User user,FlowFormAssist flowFormAssist                                     
                                       ){
        List<FlowNodes> flowNodesList = new ArrayList<>();
        FlowNodes flowNodes = new FlowNodes();
        flowNodes.setType("assist");
        flowNodes.setAssigneeId(flowFormAssist.getAssigneeId());
        flowNodes.setCreatedDt(new Date());
        flowNodes.setDeadLine(DateUtils.addDays(new Date(),30));
        flowNodes.setAssigneeBy(user.getId());
        flowNodesList.add(flowNodes);

        Flows flows = new Flows();
        flows.setType("assist");
        flows.setCreatedDt(flowNodes.getCreatedDt());
        flows.setCreatedBy(user.getId());
        flows.setDeadLine(DateUtils.addDays(new Date(),30));
        flows.setStatus("IN_PROCESS");
        flows.setNodes(flowNodesList);

        flowFormAssist.setRecordFlag("00");
        flowFormAssist.setFlows(flows);

       // return buildEntity(APIEntity.create(flowAssistService.start(flowFormAssist)), HttpStatus.OK);
        flowAssistService.start(flowFormAssist,user);
        return buildEntity(APIEntity.create(null),HttpStatus.CREATED);
    }
    
    //移交发起
    @RequestMapping(value = "/flowFormAssist/restart",method = RequestMethod.POST)
    public HttpEntity<APIEntity> restart(@SessionAttribute(WebSecurityConfig.SESSION_KEY)User user,FlowFormAssist flowFormAssist){
        
        flowAssistService.process(user,flowFormAssist.getFlowId(),"restart",flowFormAssist.getAssigneeId());
        flowAssistService.updateAsssitForm(flowFormAssist);
        return buildEntity(APIEntity.create("process success"), HttpStatus.OK);
    }
    
    //获取发起的流程
    @RequestMapping(value = "/flowFormAssist/start/{userId}",method = RequestMethod.GET)
    public HttpEntity<APIEntity<PageInfo>>selectStartByMeList(@PathVariable("userId")Long userId,@RequestParam("pageNum")int offset,
                                                              @RequestParam("pageSize")int limit){
        User contextUser = new User();
        contextUser.setId(userId);
        return buildEntity(APIEntity.create(flowAssistService.selectStartByMeList(contextUser,offset,limit)));
    }

    //经我审批
    @RequestMapping(value = "/flowFormAssist/{type}/process",method = RequestMethod.GET)
    public HttpEntity<APIEntity<PageInfo>> getTurnOverToMe(@SessionAttribute(WebSecurityConfig.SESSION_KEY)User user, @PathVariable("type") String type,@RequestParam("pageNum") int offset, @RequestParam("pageSize") int limit){
        return buildEntity(APIEntity.create(flowAssistService.getTurnOverToMe(user,type, offset, limit)), HttpStatus.OK);
    }
    
    //查看信息
    @RequestMapping(value = "/flowFormAssist/{flowId}/detail",method = RequestMethod.GET)
    public HttpEntity<APIEntity> flowFormAssist(@PathVariable Long flowId){
        return buildEntity(APIEntity.create(flowAssistService.getInfo(flowId)),HttpStatus.OK);
    }

    /**
     * 第一次先保存，再查询
     * @param id
     * @param flowId
     * @param recordtype
     * @return
     */
    @RequestMapping(value = "/flowFormAssist/searchdoc/{id}/{flowId}/{recordtype}", method = RequestMethod.GET)
    public HttpEntity<APIEntity<PageInfo>> searchDocumentListByIds(@PathVariable("id") String id,
    		@PathVariable("flowId") Long flowId,@PathVariable("recordtype") String recordtype) {
        if(!"_".equals(id))
        	flowAssistService.saveDocument(flowId, id, recordtype);
        
      return buildEntity(
          APIEntity.create(flowAssistService.getDocument(flowId)));
    }
    
    //保存 flowId和docId的关联
    @RequestMapping(value = "/flowFormAssist/savedoc/{flowId}/{id}/{type}", method = RequestMethod.GET)
    public HttpEntity<APIEntity> saveDocument(@PathVariable("id") String id,@PathVariable("flowId") Long flowId
    		,@PathVariable("type") String type) {
       
      flowAssistService.saveDocument(flowId,id,type);
      
      return buildEntity(APIEntity.create(null),HttpStatus.OK);
    }
    //删除 flowId和docId的关联
    @RequestMapping(value = "/flowFormAssist/deldoc/{flowId}/{id}/{type}", method = RequestMethod.GET)
    public HttpEntity<APIEntity> deleteDocument(@PathVariable("id") String id,@PathVariable("flowId") Long flowId
    		,@PathVariable("type") String type) {
       
      flowAssistService.deleteDocument(flowId,id,type);
      
      return buildEntity(APIEntity.create(null),HttpStatus.OK);
    }
    //处理
    @RequestMapping(value = "/flowFormAssist/process",method = RequestMethod.GET)
    public HttpEntity<APIEntity> process(@SessionAttribute(WebSecurityConfig.SESSION_KEY)User user, @RequestParam("flowId") Long flowId,
                                         @RequestParam(value = "action",required = false)String action,
                                         @RequestParam(value = "userId",required = false)Long userId){
    	flowAssistService.process(user,flowId,action,userId);
        return buildEntity(APIEntity.create("process success"), HttpStatus.OK);
    }
    
    //结束流程
    //批准或否决我的流程
    @RequestMapping(value = "/flowFormAssist/end/{result}",method = RequestMethod.GET)
    public HttpEntity<APIEntity> end(@SessionAttribute(WebSecurityConfig.SESSION_KEY)User user,@RequestParam("flowId") Long flowId,@PathVariable("result") String result,@RequestParam(value = "description" ,required = false ,defaultValue = "") String description){
        // flowAssistService.end(assist,flowNodeHistories);
        flowAssistService.end(user,flowId,result,description);
        return buildEntity(APIEntity.create(null),HttpStatus.OK);
    }
}
