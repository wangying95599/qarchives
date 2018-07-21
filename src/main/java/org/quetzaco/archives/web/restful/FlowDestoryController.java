package org.quetzaco.archives.web.restful;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.quetzaco.archives.application.biz.FlowDestoryService;
import org.quetzaco.archives.application.biz.FlowNodesService;
import org.quetzaco.archives.model.*;
import org.quetzaco.archives.model.api.APIEntity;
import org.quetzaco.archives.util.boot.FlowsType;
import org.quetzaco.archives.web.config.session.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class FlowDestoryController extends BaseRestContoller {

    @Autowired
    FlowDestoryService flowDestoryService;

    @Autowired
    FlowNodesService flowNodesService;

    //发起销毁
    // @RequestMapping(value = "flowDestroy", method = RequestMethod.POST)
    @Deprecated
    public HttpEntity<APIEntity> start1(@RequestParam("title") String title,
                                       @RequestParam("destroyUser") String destroyUser,
                                       @RequestParam("superviseUser") String superviseUser,
                                       @RequestParam("description") String description, @RequestParam("reelType") String reelType,
                                       @RequestParam("assigneeBy") Long assigneeBy, @RequestParam("deptId") Long deptId, @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("deadLine") Date deadLine, @RequestParam("assigneeId[]") Long[] ids) throws ParseException {

        //生成 flowFormlending
       // FlowFormDestory flowFormLending = new FlowFormLending();

        //生成flowNodes  list  默认只有一个node 是部门管理员
        if (ids == null || ids.length == 0)
            return null;

        List<FlowNodes> flowNodesList = new ArrayList<FlowNodes>();
        FlowNodes flowNodes = null;
        for (int i = 0; i < ids.length; i++) {
            flowNodes = new FlowNodes();
            flowNodes.setType(FlowsType.DESTROY.getName());
            flowNodes.setAssigneeId(ids[i]);
            flowNodes.setCreatedDt(new Date());
            flowNodes.setDeadLine(deadLine);
            flowNodes.setAssigneeBy(assigneeBy);
            flowNodesList.add(flowNodes);
        }

        //生成 flows
        Flows flows = new Flows();
        flows.setType(FlowsType.DESTROY.getName());
        flows.setCreatedDt(flowNodes.getCreatedDt());
        flows.setCreatedBy(assigneeBy);
        flows.setDeadLine(deadLine);
        flows.setStatus("IN_PROCESS");
        flows.setNodes(flowNodesList);

        FlowFormDestory flowFormDestory = new FlowFormDestory();
        flowFormDestory.setReelType(reelType);
        flowFormDestory.setTitle(title);
        flowFormDestory.setDestoryUser(destroyUser);
        flowFormDestory.setSuperviseUser(superviseUser);
        flowFormDestory.setDeptId(deptId);
        flowFormDestory.setDescription(description);
        flowFormDestory.setFlows(flows);
        // flowDestoryService.start(flowFormDestory);
        return buildEntity(APIEntity.create(null),HttpStatus.CREATED);
    }

    @RequestMapping(value = "/flowDestroy", method = RequestMethod.POST)
    public HttpEntity<APIEntity> start(@SessionAttribute(WebSecurityConfig.SESSION_KEY)User user, @RequestParam(value = "docIds[]",required = false) List<Long> docIds,
                                       @RequestParam(value = "recIds[]",required = false) List<Long> revIds, @RequestParam("assigneeIds[]") Long[] ids,
                                       @DateTimeFormat(pattern = "yyyy-MM-dd")@RequestParam("deadLine") Date deadLine,FlowFormDestory flowFormDestory) throws ParseException {
        //生成 flowFormlending
        // FlowFormDestory flowFormLending = new FlowFormLending();

        //生成flowNodes  list  默认只有一个node 是部门管理员
        if (ids == null || ids.length == 0)
            return null;

        List<FlowNodes> flowNodesList = new ArrayList<FlowNodes>();
        FlowNodes flowNodes = null;
        for (int i = 0; i < ids.length; i++) {
            flowNodes = new FlowNodes();
            flowNodes.setType(FlowsType.DESTROY.getName());
            flowNodes.setAssigneeId(ids[i]);
            flowNodes.setCreatedDt(new Date());
            flowNodes.setDeadLine(deadLine);
            flowNodes.setAssigneeBy(user.getId());
            flowNodesList.add(flowNodes);
        }

        //生成 flows
        Flows flows = new Flows();
        flows.setType(FlowsType.DESTROY.getName());
        flows.setCreatedDt(flowNodes.getCreatedDt());
        flows.setCreatedBy(user.getId());
        flows.setDeadLine(deadLine);
        flows.setStatus("IN_PROCESS");
        flows.setNodes(flowNodesList);

        flowFormDestory.setFlows(flows);
        flowDestoryService.start(flowFormDestory,docIds,revIds);
        return buildEntity(APIEntity.create(null),HttpStatus.CREATED);
    }

    public static Date strToDate(String strDate) throws ParseException {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        Date date = fmt.parse(strDate);
        return date;
    }

    //获取发起的销毁流程
    @RequestMapping(value = "/flowDestorying/start/{userId}",method = RequestMethod.GET)
    public HttpEntity<APIEntity<PageInfo>>selectStartByMeList(@PathVariable("userId")Long userId,@RequestParam("pageNum")int offset,
                                                              @RequestParam("pageSize")int limit){
        User contextUser = new User();
        contextUser.setId(userId);
        return buildEntity(APIEntity.create(flowDestoryService.selectStartByMeList(contextUser,offset,limit)));
    }

    //获取由我审批的销毁流程
    @RequestMapping(value = "/flowDestroy/{usrId}/process/{isProcessed}", method = RequestMethod.GET)
    public HttpEntity<APIEntity> getProcessByMe(@PathVariable("usrId") Long usrId, @PathVariable("isProcessed") Boolean isProcessed,
                                                @Param("pageNum") int pageNum, @Param("pageSize") int pageSize) {
        /*User contextUser = new User();
        @Param("process")Boolean isProcessed ,
        contextUser.setId(usrId);*/
        return buildEntity(APIEntity.create(flowDestoryService.getProcessByMe(usrId, isProcessed, pageNum, pageSize)), HttpStatus.OK);
    }

    //获取流程本部门审批成员和文档所在部门管理员
    @RequestMapping(value = "/flowDestory/askUser",method = RequestMethod.GET)
    public HttpEntity<APIEntity> getAskUsers(){
        return buildEntity(APIEntity.create(flowDestoryService.getAskUsers()), HttpStatus.OK);
    }


    //请求审批
    @RequestMapping(value = "/flowDestroy/process")
    public HttpEntity<APIEntity> process(@RequestParam("flowId") Long flowId,@RequestParam("usrId")Long usrId,
                                         @RequestParam(value = "action",required = false)String action,
                                         @RequestParam(value="description",required = false)String description,
                                         @RequestParam(value = "checks[]",required = false)Long[] checks){
        FlowNodes flowNodes = flowNodesService.getNodesByFlowAndUser(flowId,usrId).get(0);
        FlowNodeHistories flowNodeHistories = new FlowNodeHistories();
        flowNodeHistories.setNodeId(flowNodes.getId());
        flowNodeHistories.setType(flowNodes.getType());
        flowNodeHistories.setFlowId(flowId);
        flowNodeHistories.setAssigneeId(flowNodes.getAssigneeId());
        if(action!=null){
            flowNodeHistories.setAction(action);
            flowNodeHistories.setDescription(description);
        }else{
            flowNodeHistories.setAction("ASK");
            flowNodeHistories.setDescription("");
        }
        flowNodeHistories.setCreatedDt(new Date());
        flowNodeHistories.setDeadLine(flowNodes.getDeadLine());
        flowNodeHistories.setAssigneeBy(flowNodes.getAssigneeBy());
        flowNodeHistories.setRecordFlag(true);

        List<FlowNodes> list = new ArrayList<FlowNodes>();
        if (checks!=null) {
            for(int i= 0;i<checks.length;i++){
                FlowNodes flowNodes1 = new FlowNodes();
                flowNodes1.setType(flowNodes.getType());
                flowNodes1.setCreatedDt(new Date());
                flowNodes1.setDeadLine(flowNodes.getDeadLine());
                flowNodes1.setFlowId(flowId);
                flowNodes1.setAssigneeBy(usrId);
                flowNodes1.setAssigneeId(checks[i]);
                flowNodes1.setRecordFlag(true);
                list.add(flowNodes1);
            }
        }

        Flows flow = new Flows();
        flow.setId(flowId);
        flow.setNodes(list);
        flow.setType(flowNodes.getType());

        FlowFormDestory flowFormLending = new FlowFormDestory();
        flowFormLending.setFlowId(flowId);
        flowFormLending.setFlows(flow);

        flowDestoryService.proccess(flowFormLending,flowNodeHistories);
        return buildEntity(APIEntity.create("请求审批成功"),HttpStatus.CREATED);
    }

//    @RequestMapping(value = "/flowDetory/{usrId}/process/{isProcessed}",method = RequestMethod.GET)
//    public HttpEntity<APIEntity> getProcessByMe(@PathVariable("usrId")Long usrId, @PathVariable("isProcessed")Boolean isProcessed,
//                                                @Param("pageNum") int pageNum, @Param("pageSize") int pageSize){
//        /*User contextUser = new User();
//        @Param("process")Boolean isProcessed ,
//        contextUser.setId(usrId);*/
//        return buildEntity(APIEntity.create(flowDestoryService.getProcessByMe(usrId,isProcessed, pageNum, pageSize,null)), HttpStatus.OK);
//    }

    //批准或否决我的流程
    @RequestMapping(value = "/flowDestroy/end/{result}",method = RequestMethod.POST)
    public HttpEntity<APIEntity> end(Long usrId,Long flowId,@PathVariable("result") String result,String description){

        FlowNodes flowNodes = flowNodesService.getNodesByFlowAndUser(flowId,usrId).get(0);
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

        FlowFormDestory flowFormDestory = new FlowFormDestory();
        flowFormDestory.setFlows(flow);

        flowDestoryService.end(flowFormDestory,flowNodeHistories);

        return buildEntity(APIEntity.create(null),HttpStatus.OK);
    }

    // @RequestMapping(value = "/flowDestroy/details", method = RequestMethod.POST)
    @Deprecated
    public HttpEntity<APIEntity> getFlowLendingDetail(@Param("reelNum") String reelNum,
                                                      @Param("reelType") String reelType, @Param("flowId") Long flowId) {

        FlowFormDestory flowFormDestory = new FlowFormDestory();
        flowFormDestory.setFlowId(flowId);
        flowFormDestory.setReelType(reelType);
        flowFormDestory.setReelNum(reelNum);
        return buildEntity(APIEntity.create(flowDestoryService.getFlowDestroyDetail(flowFormDestory)), HttpStatus.OK);
    }
    @RequestMapping(value = "/flowDestroy/{flowId}/details", method = RequestMethod.GET)
    public HttpEntity<APIEntity> getDetail(@PathVariable Long flowId) {

        return buildEntity(APIEntity.create(flowDestoryService.getFlowDestroyDetail(flowId)), HttpStatus.OK);
    }



    @RequestMapping(value="/flowDestroy/{flowId}/remain",method=RequestMethod.GET)
    public HttpEntity<APIEntity> getFlowDestroyForRemain(@PathVariable Long flowId){
        return buildEntity(APIEntity.create(flowDestoryService.getFlowDestroyForRemain(flowId)), HttpStatus.OK);
    }

    @RequestMapping(value = "/flowDestroy/deleted/{deptId}", method = RequestMethod.GET)
    public HttpEntity<APIEntity> getDeletedFlow(@PathVariable("deptId") Long deptId ,@RequestParam("pageNum")int pageNum,@RequestParam("pageSize")int pageSize,@SessionAttribute(WebSecurityConfig.SESSION_KEY)User contextUser) {
        return buildEntity(APIEntity.create(flowDestoryService.getDeletedFlow(deptId,pageNum,pageSize,contextUser)), HttpStatus.OK);
    }

    @RequestMapping(value = "/flowDestroy/sub/{flowId}")
    public HttpEntity<APIEntity> getSubDeletedFlow(@PathVariable("flowId") Long flowId, @SessionAttribute(WebSecurityConfig.SESSION_KEY)User user) {
        return buildEntity(APIEntity.create(flowDestoryService.getsubDeletedFlow(flowId,user)), HttpStatus.OK);
    }

    //验证文件/案卷是否存在
    @Deprecated
    @RequestMapping(value = "/flowDestroy/isValid",method = RequestMethod.GET)
    public HttpEntity<APIEntity> isValid(@RequestParam("reelNum")String fileNum,@RequestParam("reelType")String fileType){
        FlowFormDestory flowFormDestory = new FlowFormDestory();
        flowFormDestory.setReelNum(fileNum);
        flowFormDestory.setReelType(fileType);
        flowDestoryService.getFormDetail(flowFormDestory);
        if(flowFormDestory.getDestoryModel()!=null){
            return buildEntity(APIEntity.create(true), HttpStatus.OK);
        }
        return buildEntity(APIEntity.create(false), HttpStatus.OK);
    }

    @RequestMapping(value = "/flowDestroy/{flowId}/destroy",method = RequestMethod.GET)
    public HttpEntity<APIEntity> destroy(@PathVariable Long flowId){
        return buildEntity(APIEntity.create(flowDestoryService.destroy(flowId)), HttpStatus.OK);
    }


}
