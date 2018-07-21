package org.quetzaco.archives.web.restful;

import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.annotations.Param;
import org.elasticsearch.index.mapper.DocumentMapper;
import org.quetzaco.archives.application.biz.FlowLendingService;
import org.quetzaco.archives.application.biz.FlowNodesService;
import org.quetzaco.archives.application.biz.RoleService;
import org.quetzaco.archives.application.dao.DocumentsMapper;
import org.quetzaco.archives.application.dao.RecordMapper;
import org.quetzaco.archives.model.*;
import org.quetzaco.archives.model.api.APIEntity;
import org.quetzaco.archives.util.boot.FlowsType;
import org.quetzaco.archives.web.config.session.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class FlowLendingController extends BaseRestContoller {

    @Autowired
    FlowLendingService flowLendingService;

    @Autowired
    FlowNodesService flowNodesService;
    @Value("${kmhk.deptId}")
    Long bossDeptId;
    @Autowired
    RoleService roleService;

    //发起借阅
    // @RequestMapping(value = "/flowLending" ,method = RequestMethod.POST)
    public HttpEntity<APIEntity> start(@RequestParam("title")String title,@RequestParam("reelNum")String reelNum,
                                       @RequestParam("lendingPermission")String lendingPermission ,@RequestParam("deadLine")String deadLine,
                                       @RequestParam("description")String description,@RequestParam("assigneeId")Long assigneeId,
                                       @RequestParam("assigneeBy")Long assigneeBy,@RequestParam("deptId") Long deptId,
                                       @RequestParam("reelType")String reelType,@RequestParam("lendingUser")String lendingUser) throws ParseException {

        //生成 flowFormlending
        FlowFormLending flowFormLending = new FlowFormLending();

        //生成flowNodes  list  默认只有一个node 是部门管理员
        List<FlowNodes> flowNodesList = new ArrayList<FlowNodes>();
        FlowNodes flowNodes = new FlowNodes();
        flowNodes.setType(FlowsType.LENDING.getName());
        flowNodes.setAssigneeId(assigneeId);
        flowNodes.setCreatedDt(new Date());
        flowNodes.setDeadLine(strToDate(deadLine));
        flowNodes.setAssigneeBy(assigneeBy);
        flowNodesList.add(flowNodes);

        //生成 flows
        Flows flows = new Flows();
        flows.setType(FlowsType.LENDING.getName());
        flows.setCreatedDt(flowNodes.getCreatedDt());
        flows.setCreatedBy(assigneeBy);
        flows.setDeadLine(strToDate(deadLine));
        flows.setStatus("IN_PROCESS");
        flows.setNodes(flowNodesList);

        flowFormLending.setTitle(title);
        /*flowFormLending.setReelNum(reelNum);
        flowFormLending.setReelType(reelType);*/
        flowFormLending.setDepId(deptId);
        flowFormLending.setLendingUser(lendingUser);
        flowFormLending.setDescription(description);
        flowFormLending.setLendingPermission(lendingPermission);
        flowFormLending.setFlows(flows);

        flowLendingService.start(flowFormLending);
        return buildEntity(APIEntity.create(null),HttpStatus.CREATED);
    }

    //发起借阅
    @RequestMapping(value = "/flowLending" ,method = RequestMethod.POST)
    public HttpEntity<APIEntity> start(@SessionAttribute(WebSecurityConfig.SESSION_KEY) User contextUser, FlowFormLending flowFormLending,
                                       @RequestParam(value = "docIds[]",required = false) List<Long> docIds,@RequestParam(value = "revIds[]",required = false) List<Long> revIds,
                                       @RequestParam("assigneeId") Long assigneeId,@DateTimeFormat(pattern = "yyyy-MM-dd")@RequestParam("deadLine") Date deadLine,
                                       @RequestParam("assitFlowId") Long assitFlowId){
        List<User> users = roleService.getUserByRoleAndDept(bossDeptId, 3l);
        String type = "01";
        for(User user:users){
            if(assigneeId.equals(user.getId())){
                type = "02";
                break;
            }
        }
        List<FlowNodes> flowNodesList = new ArrayList<FlowNodes>();
        FlowNodes flowNodes = new FlowNodes();
        flowNodes.setType(FlowsType.LENDING.getName());
        flowNodes.setAssigneeId(assigneeId);
        flowNodes.setCreatedDt(new Date());
        flowNodes.setDeadLine(deadLine);
        flowNodes.setAssigneeBy(contextUser.getId());
        flowNodesList.add(flowNodes);

        //生成 flows
        Flows flows = new Flows();
        flows.setType(FlowsType.LENDING.getName());
        flows.setCreatedDt(flowNodes.getCreatedDt());
        flows.setCreatedBy(contextUser.getId());
        flows.setDeadLine(deadLine);
        flows.setStatus("IN_PROCESS");
        flows.setNodes(flowNodesList);

        if("01".equals(type))flowFormLending.setHostId(assigneeId);
        flowFormLending.setRecordFlag(true);
        flowFormLending.setManagerId(assigneeId);
        flowFormLending.setLendingUser(contextUser.getName());
        flowFormLending.setFlows(flows);

        return buildEntity(APIEntity.create(flowLendingService.start(contextUser,flowFormLending,docIds,revIds,type,assitFlowId)),HttpStatus.OK);
    }

    /*public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }*/


    public static Date strToDate(String strDate) throws ParseException {
        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");

        Date date = fmt.parse(strDate);
        return date;
    }

    //根据reelNum,reelType判断文档是否存在
    /*@RequestMapping(value = "/flowLending/isvalid",method = RequestMethod.POST)
    public HttpEntity<APIEntity> reelNumisValid(@Param("reelNum") String reelNum,@Param("reelType")String reelType){
        FlowFormLending flowFormLending = new FlowFormLending();
        flowFormLending.setReelNum(reelNum);
        flowFormLending.setReelType(reelType);
        flowLendingService.getFormDetail(flowFormLending);
        if (flowFormLending.getLendingModel() != null)
            return buildEntity(APIEntity.create(true),HttpStatus.OK);
        return buildEntity(APIEntity.create(false), HttpStatus.OK);
    }*/



    //获取有我发起的流程
    @RequestMapping(value = "/flowLending/start/{usrId}",method = RequestMethod.GET)
    public HttpEntity<APIEntity<PageInfo>> selectStartByMeList(@PathVariable("usrId")Long usrId,@Param("pageNum") int pageNum,@Param("pageSize") int pageSize){
        User contextUser = new User();
        contextUser.setId(usrId);
        return buildEntity(APIEntity.create(flowLendingService.selectStartByMeList(contextUser, pageNum, pageSize,null)),HttpStatus.OK);
    }

    @RequestMapping(value = "/flowLending/start/{usrId}",method = RequestMethod.POST)
    public HttpEntity<APIEntity<PageInfo>> searchStartByMeList(@PathVariable("usrId")Long usrId,@Param("pageNum") int pageNum,
                                                               @Param("pageSize") int pageSize,@RequestParam("searchInfo[]")String[] searchInfo){
        User contextUser = new User();
        contextUser.setId(usrId);
        return buildEntity(APIEntity.create(flowLendingService.selectStartByMeList(contextUser, pageNum, pageSize,searchInfo)),HttpStatus.OK);
    }

    //获取由我审批的流程
    @RequestMapping(value = "/flowLending/{usrId}/process/{isProcessed}",method = RequestMethod.GET)
    public HttpEntity<APIEntity> getProcessByMe(@PathVariable("usrId")Long usrId,@PathVariable("isProcessed")Boolean isProcessed,
                                                @Param("pageNum") int pageNum,@Param("pageSize") int pageSize){
        /*User contextUser = new User();
        @Param("process")Boolean isProcessed ,
        contextUser.setId(usrId);*/
        return buildEntity(APIEntity.create(flowLendingService.getProcessByMe(usrId,isProcessed, pageNum, pageSize,null)), HttpStatus.OK);
    }

    //搜索由我审批的流程
    @RequestMapping(value = "/flowLending/{usrId}/process/{isProcessed}",method = RequestMethod.POST)
    public HttpEntity<APIEntity> searchProcessByMe(@PathVariable("usrId")Long usrId,@PathVariable("isProcessed")Boolean isProcessed,
                                                @RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize,
                                                @RequestParam(value="searchInfo[]" ,required = false)String[] searchInfo){
        /*User contextUser = new User();
        @Param("process")Boolean isProcessed ,
        contextUser.setId(usrId);*/
        return buildEntity(APIEntity.create(flowLendingService.getProcessByMe(usrId,isProcessed, pageNum, pageSize,searchInfo)), HttpStatus.OK);
    }
    //批准或否决我的流程
    @RequestMapping(value = "/flowLending/end/{result}",method = RequestMethod.POST)
    public HttpEntity<APIEntity> end(@SessionAttribute(WebSecurityConfig.SESSION_KEY)User user,Long flowId,@PathVariable("result") String result,String description){

        flowLendingService.end(user,flowId,result,description);
        // flowLendingService.end(flowFormLending,flowNodeHistories);
        return buildEntity(APIEntity.create(null),HttpStatus.OK);
    }

    //获取流程得detail
    @RequestMapping(value = "/flowLending/details",method = RequestMethod.POST)
    public HttpEntity<APIEntity> getFlowLendingDetail(@RequestParam(value = "deptId", required = false) Long deptId, @RequestParam("reelNum") String reelNum,
                                                      @RequestParam("reelType") String reelType, @RequestParam("flowId") Long flowId) {

        FlowFormLending flowFormLending = new FlowFormLending();
        flowFormLending.setFlowId(flowId);
        /*flowFormLending.setReelType(reelType);
        flowFormLending.setReelNum(reelNum);*/
        flowFormLending.setDepId(deptId);
        return buildEntity(APIEntity.create(flowLendingService.getFlowLendingDetail(flowFormLending)),HttpStatus.OK);
    }


    //获取流程状态信息
    @RequestMapping(value = "/flowLending/status/{flowId}" ,method = RequestMethod.GET)
    public HttpEntity<APIEntity> getFlowStatus(@PathVariable("flowId") Long flowId){
        return buildEntity(APIEntity.create(flowNodesService.getFlowStatus(flowId)),HttpStatus.OK);
    }


    //请求审批
    @RequestMapping(value="/flowLending/process",method = RequestMethod.POST)
    public HttpEntity<APIEntity> process(@RequestParam("flowId") Long flowId,@SessionAttribute(WebSecurityConfig.SESSION_KEY)User user,
                                         @RequestParam(value = "action",required = false)String action,
                                         @RequestParam(value="description",required = false)String description,
                                         @RequestParam(value = "checks[]",required = false)Long[] checks){


        flowLendingService.proccess(flowId,user,action,description,checks);
        return buildEntity(APIEntity.create("请求审批成功"),HttpStatus.CREATED);
    }

    //获取流程本部门审批成员和文档所在部门管理员
    @RequestMapping(value = "/flowLending/{deptId}/files/{fileDeptId}",method = RequestMethod.GET)
    public HttpEntity<APIEntity> getAskUsers(@PathVariable("deptId") Long deptId,@PathVariable("fileDeptId")Long fileDeptId){
        return buildEntity(APIEntity.create(flowLendingService.getAskUsers(deptId,fileDeptId)),HttpStatus.OK);
    }

    //查看信息
    @RequestMapping(value = "/flowLending/{flowId}/detail",method = RequestMethod.GET)
    public HttpEntity<APIEntity> getLendingInfo(@PathVariable Long flowId){
        return buildEntity(APIEntity.create(flowLendingService.getLendingInfo(flowId)),HttpStatus.OK);
    }

    //确认归还
    @RequestMapping(value = "/flowLending/{flowId}/giveBack",method = RequestMethod.GET)
    public HttpEntity<APIEntity> confirmGiveBack(@PathVariable Long flowId) {
        return buildEntity(APIEntity.create(flowLendingService.confirmGiveBack(flowId)), HttpStatus.OK);
    }

}
