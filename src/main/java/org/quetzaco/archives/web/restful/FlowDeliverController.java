package org.quetzaco.archives.web.restful;

import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.zookeeper.server.quorum.Learner;
import org.quetzaco.archives.application.biz.FlowDeliverService;
import org.quetzaco.archives.application.biz.FlowNodesService;
import org.quetzaco.archives.model.*;
import org.quetzaco.archives.model.api.APIEntity;
import org.quetzaco.archives.util.boot.FlowsType;
import org.quetzaco.archives.web.config.session.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class FlowDeliverController extends BaseRestContoller {

    @Autowired
    FlowDeliverService flowDeliverService;
    @Autowired
    FlowNodesService flowNodesService;

    //由我发起（只有部门管理员才能发起）
    @RequestMapping(value = "/flowFormDelivers",method = RequestMethod.GET)
    public HttpEntity<APIEntity<PageInfo>> getTurnOverFromMe(@SessionAttribute(WebSecurityConfig.SESSION_KEY)User user, @RequestParam("pageSize") int pageSize, @RequestParam("pageNum") int pageNum){
        return buildEntity(APIEntity.create(flowDeliverService.getTurnOverFromMe(user, pageNum, pageSize)), HttpStatus.OK);
    }

    //经我审批
    @RequestMapping(value = "/flowFormDelivers/{type}/process",method = RequestMethod.GET)
    public HttpEntity<APIEntity<PageInfo>> getTurnOverToMe(@SessionAttribute(WebSecurityConfig.SESSION_KEY)User user, @PathVariable("type") String type,@RequestParam("pageSize") int pageSize, @RequestParam("pageNum") int pageNum){
        return buildEntity(APIEntity.create(flowDeliverService.getTurnOverToMe(user,type, pageNum, pageSize)), HttpStatus.OK);
    }

    //移交发起
    @RequestMapping(value = "/flowFormDelivers",method = RequestMethod.POST)
    public HttpEntity<APIEntity> start(@SessionAttribute(WebSecurityConfig.SESSION_KEY)User user,FlowFormDeliver flowFormDeliver,
                                       @RequestParam(value = "docIds[]",required = false) List<Long> docIds,@RequestParam(value = "revIds[]",required = false) List<Long> revIds,
                                       @RequestParam("assigneeId") Long assigneeId){
        List<FlowNodes> flowNodesList = new ArrayList<>();
        FlowNodes flowNodes = new FlowNodes();
        flowNodes.setType(FlowsType.DELIVER.getName());
        flowNodes.setAssigneeId(assigneeId);
        flowNodes.setCreatedDt(new Date());
        flowNodes.setDeadLine(DateUtils.addDays(new Date(),30));
        flowNodes.setAssigneeBy(user.getId());
        flowNodesList.add(flowNodes);

        Flows flows = new Flows();
        flows.setType(FlowsType.DELIVER.getName());
        flows.setCreatedDt(flowNodes.getCreatedDt());
        flows.setCreatedBy(user.getId());
        flows.setDeadLine(DateUtils.addDays(new Date(),30));
        flows.setStatus("IN_PROCESS");
        flows.setNodes(flowNodesList);

        flowFormDeliver.setRecordFlag("00");
        flowFormDeliver.setDeliverUser(user.getName());
        flowFormDeliver.setFlows(flows);

        return buildEntity(APIEntity.create(flowDeliverService.start(flowFormDeliver,docIds,revIds)), HttpStatus.OK);
    }

    //移交发起（公司到公司）
    @RequestMapping(value = "/flowFormDelivers/c2c",method = RequestMethod.POST)
    public HttpEntity<APIEntity> startForC2C(@SessionAttribute(WebSecurityConfig.SESSION_KEY)User user,@RequestBody FlowFormDeliver flowFormDeliver){
        return buildEntity(APIEntity.create(flowDeliverService.startForC2C(user, flowFormDeliver)), HttpStatus.ACCEPTED);
    }

    //移交处理
    @RequestMapping(value = "/flowFormDelivers/process",method = RequestMethod.POST)
    public HttpEntity<APIEntity> process(@SessionAttribute(WebSecurityConfig.SESSION_KEY)User user, @RequestParam("flowId") Long flowId,
                                         @RequestParam(value = "action",required = false)String action,
                                         @RequestParam(value="description",required = false)String description,
                                         @RequestParam(value = "leader",required = false)Long leader,
                                         @RequestParam(value = "receiveUser",required = false) Long receiveUser,
                                         @RequestParam(value = "deptId" ,required = false) Long deptId){
        flowDeliverService.process(user,flowId,action,description,leader,receiveUser,deptId);
        return buildEntity(APIEntity.create("process success"), HttpStatus.OK);
    }

    //结束移交流程
    //批准或否决我的流程
    @RequestMapping(value = "/flowFormDeliver/end/{result}",method = RequestMethod.POST)
    public HttpEntity<APIEntity> end(@SessionAttribute(WebSecurityConfig.SESSION_KEY)User user,Long flowId,@PathVariable("result") String result,String description){

        FlowNodes flowNodes = flowNodesService.getNodesByFlowAndUser(flowId,user.getId()).get(0);
        FlowNodeHistories flowNodeHistories = new FlowNodeHistories();
        flowNodeHistories.setNodeId(flowNodes.getId());
        flowNodeHistories.setType(flowNodes.getType());
        flowNodeHistories.setFlowId(flowId);
        flowNodeHistories.setAssigneeId(flowNodes.getAssigneeId());
        flowNodeHistories.setAction(result);
        flowNodeHistories.setDescription(description);
        flowNodeHistories.setCreatedDt(new Date());
        flowNodeHistories.setDeadLine(flowNodes.getDeadLine());
        flowNodeHistories.setAssigneeBy(flowNodes.getAssigneeBy());
        flowNodeHistories.setRecordFlag(true);

        Flows flow  = new Flows();
        flow.setId(flowId);
        flow.setResult(result);

        FlowFormDeliver flowFormDeliver = new FlowFormDeliver();
        flowFormDeliver.setFlowId(flowId);
        flowFormDeliver.setFlows(flow);

        flowDeliverService.end(flowFormDeliver,flowNodeHistories);

        return buildEntity(APIEntity.create(null),HttpStatus.CREATED);
    }

    //查看信息
    @RequestMapping(value = "/flowFormDeliver/{flowId}/detail",method = RequestMethod.GET)
    public HttpEntity<APIEntity> getDeliverInfo(@PathVariable Long flowId){
        return buildEntity(APIEntity.create(flowDeliverService.getDeliverInfo(flowId)),HttpStatus.OK);
    }

    //获取文件所在部门管理员
    @RequestMapping(value = "/flowFormDeliver/{deptId}/manager",method = RequestMethod.GET)
    public HttpEntity<APIEntity> getManagerInReceiveDept(@PathVariable Long deptId){
        return buildEntity(APIEntity.create(flowDeliverService.getManagerInReceiveDept(deptId)),HttpStatus.OK);
    }

    //判断文件是否在移交流程中
    @RequestMapping(value = "/flowFormDeliver/{fileType}/duplicate",method = RequestMethod.GET)
    public HttpEntity<APIEntity> getDuplicateInTurnOver(@PathVariable("fileType") String fileType,@RequestParam("docIds[]") Long[] docIds){
        return buildEntity(APIEntity.create(flowDeliverService.getDuplicateInTurnOver(fileType,docIds)),HttpStatus.OK);
    }
}
