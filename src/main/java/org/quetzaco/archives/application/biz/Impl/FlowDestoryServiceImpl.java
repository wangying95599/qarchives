package org.quetzaco.archives.application.biz.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.quetzaco.archives.application.biz.*;
import org.quetzaco.archives.application.dao.DocumentsMapper;
import org.quetzaco.archives.application.dao.FlowFormDestoryMapper;
import org.quetzaco.archives.application.dao.FlowsMapper;
import org.quetzaco.archives.application.dao.RecordMapper;
import org.quetzaco.archives.application.search.elastic.ElasticSearchDao;
import org.quetzaco.archives.model.*;
import org.quetzaco.archives.util.boot.RoleType;
import org.quetzaco.archives.util.config.ElasticsearchProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Description Created by dong on 2017/7/21.
 */
@Service
public class FlowDestoryServiceImpl implements FlowDestoryService {

  @Autowired
  FlowFormDestoryMapper flowFormDestoryMapper;
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
  RoleService roleService;
  @Autowired
  FlowDeliverService flowDeliverService;
  @Autowired
  DocumentsMapper documentsMapper;
  @Autowired
  RecordMapper recordMapper;
  @Autowired
  FlowsMapper flowsMapper;
  @Autowired
  UserService userService;
  @Autowired
  ElasticSearchDao elasticSearchDao;
  @Autowired
  ElasticsearchProperties elasticsearchProperties;

  final static Logger LOGGER = LoggerFactory.getLogger(FlowDestoryServiceImpl.class);


  @Override
  @Transactional
  public void start(FlowFormDestory flowFormDestory,List<Long> docIds,List<Long> recIds) {
   // flowService.start(flowFormDestory.getFlows());
    Long flowId = flowService.start(flowFormDestory.getFlows(),flowFormDestory.getTitle());
    flowFormDestory.setFlowId(flowId);
    flowFormDestoryMapper.insert(flowFormDestory);
    flowDeliverService.insertLinkFlowDoc(docIds,recIds,flowId);
  }

  @Override
  public PageInfo selectStartByMeList(User contextUser, int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    return new PageInfo(flowFormDestoryMapper.selectStartByMeList(contextUser));
  }

  @Override
  public PageInfo getProcessByMe(Long usrId, Boolean isProcessed, int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
      return new PageInfo(flowFormDestoryMapper.selectProcessByMe(usrId, isProcessed));
  }

  @Override
  public FlowFormDestory getFormDetail(FlowFormDestory flowFormDestory) {
    setLendingModel(flowFormDestory);
    return flowFormDestory;
  }

  //查询借阅文档信息 document/archive/record/box
  //flowFormLending.setLendingModel();
  private void setLendingModel(FlowFormDestory flowFormDestory) {
    switch (flowFormDestory.getReelType()) {
      case "document":
        flowFormDestory.setDestoryModel(
            documentService.selectDocumentByFileCode(flowFormDestory.getReelNum()));
        break;
      case "record":
          flowFormDestory.setDestoryModel(recordService.selectByFileCode(flowFormDestory.getReelNum()));
        break;
    }
  }

  @Override
  public FlowFormDestory getFlowHistory(FlowFormDestory flowFormDestory) {
    Flows flows = null;
    if (flowFormDestory.getFlows() != null && flowFormDestory.getFlows().getId() != null) {
      flows = flowFormDestory.getFlows();
    } else {
      flows = new Flows();
      flows.setId(flowFormDestory.getFlowId());
      flows = flowService.detail(flows);
    }

    flowFormDestory.setFlows(flowService.history(flows));
    return flowFormDestory;
  }

  @Transactional
  @Override
  public void proccess(FlowFormDestory flowFormDestory, FlowNodeHistories flowNodeHistories) {
    if (flowFormDestory == null || flowFormDestory.getFlows() == null) {
      throw new RuntimeException(" flowFormLending is null or empty ");
    }

    flowService.proccess(flowFormDestory.getFlows(), flowNodeHistories);
  }

  @Transactional
  @Override
  public void end(FlowFormDestory flowFormDestory, FlowNodeHistories flowNodeHistories) {
    if (flowFormDestory == null || flowFormDestory.getFlows() == null) {
      throw new RuntimeException(" flowFormLending is null or empty ");
    }
    flowService.end(flowFormDestory.getFlows(), flowNodeHistories);
  }


  @Override
  public Map<String, List<User>> getAskUsers() {
    List<User> list1= roleService.selectRoles(10L);
    List<User> list2= roleService.selectRoles(9L);
    List<User> list3= roleService.selectRoles(8L);
    Map<String, List<User>> map = new HashMap<String, List<User>>();
    map.put("reel", list1);
    map.put("file", list2);
    map.put("lead",list3);
    return map;
  }

  @Deprecated
  @Override
  public FlowFormDestory getFlowDestroyDetail(FlowFormDestory flowFormDestory) {
    setLendingModel(flowFormDestory);
    return flowFormDestory;
  }

  @Override
  public PageInfo getDeletedFlow(Long deptId, int pageNum, int pageSize,User contextUser) {
    Map map = userService.loadingUser(contextUser.getId());
    Object obj = map.get("role_id");
    Long roleId = null;
    if(obj!=null){
      roleId = (Long)obj;
    }
    boolean isManager = false;
    if(RoleType.DEPT_MANAGER.getType().equals(roleId)||RoleType.MANAGER.getType().equals(roleId))isManager=true;
    System.out.println(pageNum+"--------"+pageSize);
    PageHelper.startPage(pageNum, pageSize);
    List<FlowFormDestory> maps = flowFormDestoryMapper.getDeletedFlow(deptId,isManager,contextUser.getId());
    PageInfo pageInfo = new PageInfo(maps);
    /*for (Map map : maps) {
      String reelType = (String) map.get("reelType");
      if ("document".equals(reelType))
        map.put("reelType", "文件");
      else if ("record".equals(reelType))
        map.put("reelType", "案卷");
      else if ("archive".equals(reelType))
        map.put("reelType", "档案");
      else
        map.put("reelType", "全宗");
    }*/
    return pageInfo;
  }

  @Override
  public FlowFormDestory getFlowDestroyForRemain(Long flowId) {
    FlowFormDestory flowFormDestory = flowFormDestoryMapper.getFlowDestroyForRemain(flowId);
    setLendingModel(flowFormDestory);
    return flowFormDestory;
  }

  @Override
  public FlowFormDestory getFlowDestroyDetail(Long flowId) {
    FlowFormDestory flowFormDestory = flowFormDestoryMapper.getFlowDestroyForRemain(flowId);
    List<Documents> documentsList = flowDeliverService.getDocumentsList(flowId);
    List<Record> recordList = flowDeliverService.getRecordList(flowId);
    flowFormDestory.setDocumentsList(documentsList);
    flowFormDestory.setRecordList(recordList);
    if(documentsList!=null){
      LOGGER.debug("销毁了:"+documentsList.size()+"个件级档案" );
    }
    if(recordList!=null){
      LOGGER.debug("销毁了:"+recordList.size()+"个卷级档案");
    }
    return flowFormDestory;
  }

  @Transactional
  @Override
  public int destroy(Long flowId) {
    FlowFormDestory flowFormDestory = getFlowDestroyDetail(flowId);
    List<Documents> documentsList = flowFormDestory.getDocumentsList();
    List<Record> recordList = flowFormDestory.getRecordList();
    List<Long> docIds = new ArrayList<>();
    List<Long> recIds = new ArrayList<>();

    if (documentsList!=null&&documentsList.size()>0) {
      for (Documents documents : documentsList){
        docIds.add(documents.getId());
      }
    }
    if (recordList!=null&&recordList.size()>0) {
      for(Record record:recordList){
        recIds.add(record.getId());
      }
    }
    int  a = 0;
    if(docIds!=null&&docIds.size()>0){
      DocumentsExample documentsExample = new DocumentsExample();
      documentsExample.createCriteria().andIdIn(docIds);
      Documents documents = new Documents();
      documents.setRecordFlag("02");
      int b = documentsMapper.updateByExampleSelective(documents, documentsExample);
      List<String> fids = documentsMapper.getSubFidBydocIds(docIds);
      fids.forEach(x->{
        elasticSearchDao.deleteDocument(elasticsearchProperties.getIndex(),elasticsearchProperties.getType(),x);
      });
      a = a+b;
    }

    if(recIds!=null&&recIds.size()>0){
      RecordExample recordExample = new RecordExample();
      recordExample.createCriteria().andIdIn(recIds);
      Record record = new Record();
      record.setRecordFlag(false);
      int c = recordMapper.updateByExampleSelective(record, recordExample);
      List<String> fids = recordMapper.getSubFidByRecIds(recIds);
      fids.forEach(x->{
        elasticSearchDao.deleteDocument(elasticsearchProperties.getIndex(),elasticsearchProperties.getType(),x);
      });
      a = a+c;
    }

    Flows flows = new Flows();
    flows.setId(flowId);
    flows.setResult("DELETED");
    flows.setEndDt(new Date());
    flowsMapper.updateByPrimaryKeySelective(flows);
    return a;
  }

  @Override
  public List<Map> getsubDeletedFlow(Long flowId,User user) {

    return flowFormDestoryMapper.getsubDeletedFlow(flowId);
  }
}
