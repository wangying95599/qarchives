package org.quetzaco.archives.application.biz.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.tools.javac.comp.Flow;
import org.quetzaco.archives.application.biz.*;
import org.quetzaco.archives.application.dao.*;
import org.quetzaco.archives.model.*;
import org.quetzaco.archives.web.restful.FlowLendingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.Doc;
import java.text.ParseException;
import java.util.*;

/**
 * @Description Created by dong on 2017/7/21.
 */
@Service
public class FlowLendingServiceImpl implements FlowLendingService {

  @Autowired
  FlowFormLendingMapper flowFormLendingMapper;
  @Autowired
  FlowService flowService;
  @Autowired
  DocumentService documentService;
  @Autowired
  BoxService boxService;
  @Autowired
  RecordService recordService;
  @Autowired
  ArchiveService archiveService;
  @Autowired
  DeptService deptService;
  @Autowired
  RoleService roleService;
  @Autowired
  FlowFormDeliverMapper flowFormDeliverMapper;
  @Autowired
  FlowDeliverService flowDeliverService;
  @Autowired
  FlowNodesService flowNodesService;
  @Autowired
  DocumentsMapper documentsMapper;
  @Autowired
  RecordMapper recordMapper;
  @Autowired
  UserService userService;
  @Autowired
  FlowFormAssistMapper flowFormAssistMapper;
  @Autowired
  FlowAssistService flowAssistService;

  final static Logger LOGGER = LoggerFactory.getLogger(FlowLendingServiceImpl.class);


  @Override
  @Transactional
  public void start(FlowFormLending flowFormLending) {
    Long flowId = flowService.start(flowFormLending.getFlows(),flowFormLending.getTitle());
    flowFormLending.setFlowId(flowId);
    flowFormLendingMapper.insert(flowFormLending);
  }

  @Override
  public PageInfo selectStartByMeList(User contextUser, int pageNum, int pageSize,String[] searchInfo) {
    String title ="";
    String reelNum ="";
    Date start = null;
    Date end = null;
    if(searchInfo!=null){
      title = searchInfo[0];
      reelNum = searchInfo[1];

      try {
        if(!"".equals(searchInfo[2].trim())) {
          start = FlowLendingController.strToDate(searchInfo[2]);
        }
        if(!"".equals(searchInfo[3].trim())){
          end = FlowLendingController.strToDate(searchInfo[3]);
        }
      } catch (ParseException e) {
        e.printStackTrace();
      }

    }
    System.out.println(title+"  "+reelNum+"  "+ start+"  "+end);
    PageHelper.startPage(pageNum, pageSize);
    return new PageInfo(flowFormLendingMapper.selectStartByMeList(contextUser,title,reelNum,start,end));
  }

  @Override
  public PageInfo getProcessByMe(Long usrId,Boolean isProcessed, int pageNum, int pageSize,String[] searchInfo) {
    Map map = new HashMap();
    Date start=null;
    Date end =null;
    if (searchInfo!=null) {
      List list = new ArrayList();
    /*ffl.flow_id, ffl.title, ffl.reel_num, ffl.reel_type, ffl.dep_id, ffl.lending_user, ffl.lending_permission, ffl.description,
    ffl.record_flag, f.id, f.type, f.status, f.result, f.created_dt, f.dead_line, f.end_dt*/
      list.add("ffl.title");
      list.add("ffl.reel_num");
      list.add("ffl.lending_user");
      for(int i = 0;i<list.size();i++){
        if(!"".equals(searchInfo[i])){
          map.put(list.get(i),searchInfo[i]);
        }
      }

      try {
        if(!"".equals(searchInfo[3].trim()))
          start = FlowLendingController.strToDate(searchInfo[3]);
        if(!"".equals(searchInfo[4].trim()))
          end = FlowLendingController.strToDate(searchInfo[4]);
        System.out.println(searchInfo[3] + "    "+ searchInfo[4]);
        System.out.println(start+"     start   end    "+end);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }

    System.out.println("this is a map   "+map.toString() );
    PageHelper.startPage(pageNum, pageSize);
    return new PageInfo(flowFormLendingMapper.selectProcessByMe(usrId,isProcessed,map,start,end));
  }

  @Override
  public FlowFormLending getFormDetail(FlowFormLending flowFormLending) {
    setLendingModel(flowFormLending);
    return flowFormLending;
  }

  //查询借阅文档信息 document/archive/record/box
  //flowFormLending.setLendingModel();
  private void setLendingModel(FlowFormLending flowFormLending) {
    /*switch (flowFormLending.getReelType()) {
      case "document":
        flowFormLending.setLendingModel(
            documentService.selectDocumentByFileCode(flowFormLending.getReelNum()));
        break;
      case "archive":
        flowFormLending
            .setLendingModel(archiveService.selectByFileCode(flowFormLending.getReelNum()));
        break;
      case "record":
        flowFormLending.setLendingModel(recordService.selectByFileCode(flowFormLending.getReelNum()));
        break;
      case "box":
        flowFormLending.setLendingModel(boxService.selectByFileCode(flowFormLending.getReelNum()));
        break;
    }*/
  }

  @Override
  public FlowFormLending getFlowHistory(FlowFormLending flowFormLending) {
    Flows flows = null;
    if (flowFormLending.getFlows() != null && flowFormLending.getFlows().getId() != null) {
      flows = flowFormLending.getFlows();
    } else {
      flows = new Flows();
      flows.setId(flowFormLending.getFlowId());
      flows = flowService.detail(flows);
    }

    flowFormLending.setFlows(flowService.history(flows));
    return flowFormLending;
  }

  /*@Transactional
  @Override
  public void proccess(FlowFormLending flowFormLending, FlowNodeHistories flowNodeHistories) {
    if (flowFormLending == null || flowFormLending.getFlows() == null) {
      throw new RuntimeException(" flowFormLending is null or empty ");
    }

    flowService.proccess(flowFormLending.getFlows(), flowNodeHistories);
  }*/

  @Transactional
  @Override
  public void end(FlowFormLending flowFormLending, FlowNodeHistories flowNodeHistories) {
    if (flowFormLending == null || flowFormLending.getFlows() == null) {
      throw new RuntimeException(" flowFormLending is null or empty ");
    }
    flowService.end(flowFormLending.getFlows(), flowNodeHistories);
  }

  @Transactional
  @Override
  public void end(User user, Long flowId, String result, String description) {
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

    FlowFormLending flowFormLending = new FlowFormLending();
    flowFormLending.setFlows(flow);
    end(flowFormLending,flowNodeHistories);
  }

  @Override
  public Map getFlowLendingDetail(FlowFormLending flowFormLending) {
    String deptName=null;
    if(flowFormLending.getDepId()!=null)
    deptName = deptService.getDeptById(flowFormLending.getDepId()).getName();
    flowFormLending = flowFormLendingMapper.selectByPrimaryKey(flowFormLending.getFlowId());

    setLendingModel(flowFormLending);
    Map map = new HashMap();
    map.put("flowFormLending", flowFormLending);
    map.put("dept", deptName);
    return map;
  }

  @Override
  public Map<String, List<User>> getAskUsers(Long deptId, Long fileDeptId) {
    List<User> list1= roleService.getUserByRoleAndDept(deptId,6L);
    List<User> list2= roleService.getUserByRoleAndDept(fileDeptId,3L);
    List<User> list3= roleService.selectRoles(8L);
    Map<String, List<User>> map = new HashMap<String, List<User>>();
    map.put("reel", list1);
    map.put("file", list2);
    map.put("lead",list3);
    return map;
  }

  @Override
  public FlowFormLending getLendingInfo(Long flowId) {
    FlowFormLending flowFormLending = flowFormLendingMapper.getLendingInfo(flowId);
    List<Documents> documentsList = flowDeliverService.getDocumentsList(flowId);
    List<Record> recordList = flowDeliverService.getRecordList(flowId);
    flowFormLending.setDocumentsList(documentsList);
    flowFormLending.setRecordList(recordList);
    if(documentsList!=null){
      LOGGER.debug("销毁了:"+documentsList.size()+"个件级档案" );
    }
    if(recordList!=null){
      LOGGER.debug("销毁了:"+recordList.size()+"个卷级档案");
    }
    return flowFormLending;
  }

  @Transactional
  @Override
  public Long start(User user,FlowFormLending flowFormLending, List<Long> docIds, List<Long> revIds,String type,Long assitFlowId) {
    //结束协查流程
    flowAssistService.end(user,assitFlowId,"ACCEPT","");
    List<Documents> documentsList = new ArrayList<>();
    List<Record> recordList = new ArrayList<>();
    if(docIds!=null&&docIds.size()>0){
      DocumentsExample documentsExample = new DocumentsExample();
      documentsExample.createCriteria().andIdIn(docIds).andRecordFlagEqualTo("00");
      documentsList = documentsMapper.selectByExample(documentsExample);
    }
    if(revIds!=null&&revIds.size()>0){
      RecordExample recordExample = new RecordExample();
      recordExample.createCriteria().andIdIn(revIds).andRecordFlagEqualTo(true);
      recordList = recordMapper.selectByExample(recordExample);
    }
    Map<Long,List> map = new HashMap<Long,List>();
    if(documentsList!=null&&documentsList.size()>0){
      for(Documents documents :documentsList){
        Long deptId = documents.getDeptId();
        if(!map.containsKey(deptId)){
          List list1 = new ArrayList();
          list1.add(documents);
          map.put(deptId, list1);
        }else{
          map.get(deptId).add(documents);
        }
      }
    }
    if(recordList!=null&&recordList.size()>0){
      for(Record record:recordList){
        Long deptId = record.getDeptId();
        if(!map.containsKey(deptId)){
          List list1 = new ArrayList();
          list1.add(record);
          map.put(deptId,list1);
        }else{
          map.get(deptId).add(record);
        }
      }
    }
    for(Map.Entry<Long,List> entry:map.entrySet()){
      Long loanDeptId = entry.getKey();
      List list = entry.getValue();
      List<Long> simpleDocIds = new ArrayList<>();
      List<String> documentLocalIds = new ArrayList<>();
      List<Long> simpleRevIds = new ArrayList<>();
      for(Object obj :list){
        if(obj instanceof Documents){
          simpleDocIds.add(((Documents) obj).getId());
          documentLocalIds.add(((Documents) obj).getDocumentLocalId());
        }else if(obj instanceof Record){
          simpleRevIds.add(((Record) obj).getId());
        }
      }
      flowFormLending.setLendingType(type);
      flowFormLending.setLoanDeptId(loanDeptId);
      Long flowId = flowService.start(flowFormLending.getFlows(),flowFormLending.getTitle());
      flowFormLending.setFlowId(flowId);
      flowFormLendingMapper.insert(flowFormLending);
      flowDeliverService.insertLinkFlowDoc(simpleDocIds,simpleRevIds,flowId);
      Map relationMap = new HashMap();
      if(simpleDocIds!=null&&simpleDocIds.size()>0) relationMap.put("doc_id", simpleDocIds);
      if(simpleRevIds!=null&&simpleRevIds.size()>0)relationMap.put("record_id",simpleRevIds);
      if(documentLocalIds!=null&&documentLocalIds.size()>0) relationMap.put("document_local_id", documentLocalIds);
      if(relationMap.size()!=0) flowFormAssistMapper.updateLendTypeWhenLending(assitFlowId, relationMap);
    }

    return 0l ;
  }

  @Transactional
  @Override
  public void proccess(Long flowId, User user, String action, String description, Long[] checks) {
    FlowNodes flowNodes = flowNodesService.getNodesByFlowAndUser(flowId,user.getId()).get(0);
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
    FlowFormLending flowFormLending = flowFormLendingMapper.selectByPrimaryKey(flowId);
    if(checks!=null&&checks.length>0&&flowFormLending.getManagerId().equals(user.getId())&&"02".equals(flowFormLending.getLendingType())){
      //当公司管理员分配给部门管理员时(type==02 借阅其它部门的文档)
      FlowFormLendingExample flowFormLendingExample = new FlowFormLendingExample();
      flowFormLending.setFlowId(flowId);
      FlowFormLending flowFormLending1 = new FlowFormLending();
      flowFormLending1.setHostId(checks[0]);
      flowFormLendingMapper.updateByExampleSelective(flowFormLending1, flowFormLendingExample);
    }else if((checks==null||checks.length==0)&&user.getId()==flowFormLending.getHostId()&&"02".equals(flowFormLending.getLendingType())){
      //当部门管理员批准时  (type==02 借阅其它部门的文档)
      FlowNodes fn = new FlowNodes();
      fn.setType(flowNodes.getType());
      fn.setCreatedDt(new Date());
      fn.setDeadLine(flowNodes.getDeadLine());
      fn.setFlowId(flowId);
      fn.setAssigneeBy(user.getId());
      fn.setAssigneeId(flowFormLending.getManagerId());
      fn.setRecordFlag(true);
      list.add(fn);
    }

    if (checks!=null) {
      for(int i= 0;i<checks.length;i++){
        FlowNodes flowNodes1 = new FlowNodes();
        flowNodes1.setType(flowNodes.getType());
        flowNodes1.setCreatedDt(new Date());
        flowNodes1.setDeadLine(flowNodes.getDeadLine());
        flowNodes1.setFlowId(flowId);
        flowNodes1.setAssigneeBy(user.getId());
        flowNodes1.setAssigneeId(checks[i]);
        flowNodes1.setRecordFlag(true);
        list.add(flowNodes1);
      }
    }

    Flows flow = new Flows();
    flow.setId(flowId);
    flow.setNodes(list);
    flow.setType(flowNodes.getType());

    flowService.proccess(flow, flowNodeHistories);
  }

  @Override
  public int confirmGiveBack(Long flowId) {
    return flowService.confirmGiveBack(flowId);
  }


}
