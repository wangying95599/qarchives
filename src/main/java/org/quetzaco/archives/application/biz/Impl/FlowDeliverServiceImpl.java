package org.quetzaco.archives.application.biz.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.time.DateUtils;
import org.quetzaco.archives.application.biz.*;
import org.quetzaco.archives.application.dao.DocumentsMapper;
import org.quetzaco.archives.application.dao.FlowFormDeliverMapper;
import org.quetzaco.archives.application.dao.FlowNodeHistoriesMapper;
import org.quetzaco.archives.application.dao.RecordMapper;
import org.quetzaco.archives.model.*;
import org.quetzaco.archives.util.boot.FlowsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class FlowDeliverServiceImpl implements FlowDeliverService {
    @Autowired
    FlowFormDeliverMapper flowFormDeliverMapper;
    @Autowired
    FlowService flowService;
    @Autowired
    DocumentService documentService;
    @Autowired
    RecordService recordService;
    @Autowired
    UserService userService;
    @Autowired
    FlowNodesService flowNodesService;
    @Autowired
    DocumentsMapper documentsMapper;
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    RoleService roleService;
    @Autowired
    FlowNodeHistoriesMapper flowNodeHistoriesMapper;
    @Autowired
    ElasticsearchService elasticsearchService;

    final static Logger LOGGER = LoggerFactory.getLogger(FlowDeliverServiceImpl.class);

    @Override
    public PageInfo getTurnOverFromMe(User user, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<FlowFormDeliver> list = flowFormDeliverMapper.getTurnOverFromMe(user.getId());
        return new PageInfo(list);
    }

    @Override
    public PageInfo getTurnOverToMe(User user, String type, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);//type 00（全部）01（未完成）02（已完成）
        List<FlowFormDeliver> list = flowFormDeliverMapper.getTurnOverToMe(user.getId(),type);
        return new PageInfo(list);
    }

    @Override
    public Long start(FlowFormDeliver flowFormDeliver,List<Long> docIds,List<Long> revIds) {
        Long flowId = flowService.start(flowFormDeliver.getFlows(),flowFormDeliver.getTitle());
        flowFormDeliver.setFlowId(flowId);
        flowFormDeliverMapper.insert(flowFormDeliver);
        insertLinkFlowDoc(docIds, revIds, flowId);
        return flowId;
    }

    @Override
    public void insertLinkFlowDoc(List<Long> docIds, List<Long> revIds, Long flowId) {
        List<Map> list = new ArrayList<>();

        if(docIds!=null&&docIds.size()>0){
            DocumentsExample documentsExample = new DocumentsExample();
            documentsExample.createCriteria().andIdIn(docIds).andRecordFlagEqualTo("00");
            List<Documents> documentsList = documentsMapper.selectByExample(documentsExample);
            for(Documents doc : documentsList){
                Map map = new HashMap();
                map.put("flowId", flowId);
                map.put("docId", doc.getId());
                map.put("fileNum", doc.getFileNum());
                map.put("fileType", "documents");
                list.add(map);
            }
        }
        if(revIds!=null&&revIds.size()>0){
            RecordExample recordExample =  new RecordExample();
            recordExample.createCriteria().andIdIn(revIds).andRecordFlagEqualTo(true);
            List<Record> recordList = recordMapper.selectByExample(recordExample);
            for(Record rec : recordList){
                Map map = new HashMap();
                map.put("flowId", flowId);
                map.put("docId", rec.getId());
                map.put("fileNum", rec.getFileNum());
                map.put("fileType", "record");
                list.add(map);
            }
        }
        int count = flowFormDeliverMapper.addDeliverModelBatch(list);
    }

    @Transactional
    @Override
    public void process(User user, Long flowId, String action, String description, Long leader, Long receiveUser,Long deptId) {
        FlowNodes flowNodes = flowNodesService.getNodesByFlowAndUser(flowId, user.getId()).get(0);
        FlowNodeHistories flowNodeHistories = new FlowNodeHistories();
        flowNodeHistories.setNodeId(flowNodes.getId());
        flowNodeHistories.setType(flowNodes.getType());
        flowNodeHistories.setFlowId(flowNodes.getFlowId());
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

        List<FlowNodes> flowNodesList = new ArrayList<>();
        //管理员请求审批 生成新的flownodes
        if(leader!=null&&receiveUser!=null){
            FlowFormDeliver flowFormDeliver = new FlowFormDeliver();
            flowFormDeliver.setReceiveUser(receiveUser);
            FlowFormDeliverExample flowFormDeliverExample = new FlowFormDeliverExample();
            flowFormDeliverExample.createCriteria().andFlowIdEqualTo(flowId);
            flowFormDeliverMapper.updateByExampleSelective(flowFormDeliver, flowFormDeliverExample);
            FlowNodes flowNodes1 = new FlowNodes();
            flowNodes1.setAssigneeId(leader);
            flowNodes1.setAssigneeBy(user.getId());
            flowNodes1.setType(flowNodes.getType());
            flowNodes1.setFlowId(flowId);
            flowNodes1.setDeadLine(flowNodes.getDeadLine());
            flowNodes1.setCreatedDt(new Date());
            flowNodes1.setRecordFlag(true);
            flowNodesList.add(flowNodes1);
        }

        //当文件所在部门  领导审批时
        Map map = userService.loadingUser(user.getId());
        if(deptId!=null&&map.get("dept_id").equals(deptId)){
            FlowFormDeliverExample flowFormDeliverExample = new FlowFormDeliverExample();
            flowFormDeliverExample.createCriteria().andFlowIdEqualTo(flowId);
            FlowFormDeliver flowFormDeliver = flowFormDeliverMapper.selectByExample(flowFormDeliverExample).get(0);
            FlowNodes flowNodes1 = new FlowNodes();
            flowNodes1.setFlowId(flowFormDeliver.getFlowId());
            flowNodes1.setCreatedDt(new Date());
            flowNodes1.setType(flowNodes.getType());
            flowNodes1.setAssigneeId(flowFormDeliver.getReceiveUser());
            flowNodes1.setAssigneeBy(flowNodes.getAssigneeBy());
            flowNodes1.setRecordFlag(true);
            flowNodes1.setDeadLine(flowNodes.getDeadLine());
            flowNodesList.add(flowNodes1);
        }

        Flows flows = new Flows();
        flows.setId(flowId);
        flows.setNodes(flowNodesList);
        flows.setType(flowNodes.getType());

        flowService.proccess(flows, flowNodeHistories);
    }

    @Override
    public FlowFormDeliver getDeliverInfo(Long flowId) {
        FlowFormDeliver flowFormDeliver = flowFormDeliverMapper.getDeliverInfo(flowId);
        List<Documents> documentsList = getDocumentsList(flowId);
        List<Record> recordList = getRecordList(flowId);
        flowFormDeliver.setDocumentsList(documentsList);
        flowFormDeliver.setRecordList(recordList);
        return flowFormDeliver;
    }

    @Override
    public List<User> getManagerInReceiveDept(Long deptId) {
        return roleService.getUserByRoleAndDept(deptId, 3l);//部门管理员 roleId =3;
    }

    @Override
    public List<Long> getDuplicateInTurnOver(String fileType, Long[] docIds) {
        return flowFormDeliverMapper.getDuplicateInTurnOver(fileType,docIds);
    }

    @Transactional
    @Override
    public int startForC2C(User user, FlowFormDeliver flowFormDeliver) {
        List<Documents> documentsList = flowFormDeliver.getDocumentsList();
        List<Record> recordList = flowFormDeliver.getRecordList();
        if (flowFormDeliver==null)
            return 0;
        if((documentsList==null||documentsList.size()==0)&&(recordList==null||recordList.size()==0))
            return 0;
        List<Long> docIds = new ArrayList<>();
        List<Long> revIds = new ArrayList<>();
        if(documentsList!=null&&documentsList.size()>0){
            for(Documents doc :documentsList){
                docIds.add(doc.getId());
                Documents doc1 = new Documents();
                doc1.setId(doc.getId());
                doc1.setReserveLocation(doc.getNewReserveLocation());
                documentsMapper.updateByPrimaryKeySelective(doc1);
            }
        }
        if(recordList!=null&&recordList.size()>0){
            for (Record rec:recordList){
                revIds.add(rec.getId());
                Record record = new Record();
                record.setId(rec.getId());
                record.setReserveLocation(rec.getNewReserveLocation());
                recordMapper.updateByPrimaryKeySelective(record);
            }
        }
        Flows flows = new Flows();
        flows.setType(FlowsType.DELIVER.getName());
        flows.setCreatedDt(new Date());
        flows.setCreatedBy(user.getId());
        flows.setDeadLine(DateUtils.addDays(new Date(),30));
        flows.setStatus("COMPLETE");
        flows.setResult("ACCEPT");
        flows.setEndDt(flows.getCreatedDt());
        flows.setNodes(new ArrayList<>());

        flowFormDeliver.setRecordFlag("00");
        flowFormDeliver.setDeliverUser(user.getName());
        flowFormDeliver.setFlows(flows);
        flowFormDeliver.setReceiveUser(user.getId());
        flowFormDeliver.setReceiveDeptId(flowFormDeliver.getDeliverDeptId());
        Long flowId = start(flowFormDeliver, docIds, revIds);

        FlowNodeHistories flowNodeHistories = new FlowNodeHistories();
        flowNodeHistories.setNodeId(99999l);
        flowNodeHistories.setType(flows.getType());
        flowNodeHistories.setFlowId(flowId);
        flowNodeHistories.setAssigneeId(flowFormDeliver.getReceiveUser());
        flowNodeHistories.setAction(flows.getResult());
        flowNodeHistories.setDescription(flowFormDeliver.getDescription());
        flowNodeHistories.setCreatedDt(flows.getCreatedDt());
        flowNodeHistories.setDeadLine(flows.getDeadLine());
        flowNodeHistories.setAssigneeBy(flows.getCreatedBy());
        flowNodeHistories.setRecordFlag(true);
        flowNodeHistoriesMapper.insert(flowNodeHistories);


        return 0;
    }

    @Transactional
    @Override
    public void end(FlowFormDeliver flowFormDeliver, FlowNodeHistories flowNodeHistories) {
        if (flowFormDeliver == null || flowFormDeliver.getFlows() == null) {
            throw new RuntimeException(" flowFormLending is null or empty ");
        }
        Flows flows = flowFormDeliver.getFlows();
        flowService.end(flows, flowNodeHistories);

        if(!"ACCEPT".equals(flows.getResult()))return;
        FlowFormDeliverExample flowFormDeliverExample = new FlowFormDeliverExample();
        flowFormDeliverExample.createCriteria().andFlowIdEqualTo(flowFormDeliver.getFlowId());
        flowFormDeliver = flowFormDeliverMapper.selectByExample(flowFormDeliverExample).get(0);
        List<Documents> documentsList = getDocumentsList(flowFormDeliver.getFlowId());
        List<Record> recordList = getRecordList(flowFormDeliver.getFlowId());
        Long ReceiveDeptId = flowFormDeliver.getReceiveDeptId();
        int i = documentService.turnOver(flowFormDeliver.getFlowId(),ReceiveDeptId, documentsList);
        int j = recordService.turnOver(flowFormDeliver.getFlowId(),ReceiveDeptId, recordList);

        List<String> fileIds = new ArrayList<>();
        if(documentsList!=null && !documentsList.isEmpty()){
            List<Long> docIds = new ArrayList<>();
            documentsList.forEach(x->docIds.add(x.getId()));
            fileIds.addAll(documentsMapper.getSubFidBydocIds(docIds));
        }

        if(recordList!=null && !recordList.isEmpty()){
            List<Long> recIds = new ArrayList<>();
            recordList.forEach(x->recIds.add(x.getId()));
            fileIds.addAll(recordMapper.getSubFidByRecIds(recIds));
        }

        boolean isSucceeded = elasticsearchService.updateDepId(fileIds,ReceiveDeptId);
        LOGGER.debug("bulk update "+isSucceeded);
        LOGGER.debug("移交了："+i+"个文件");
        LOGGER.debug("移交了："+j+"个案卷");
    }


    @Override
    public List<Documents> getDocumentsList(Long id){
        return flowFormDeliverMapper.getTurnOverDocList(id);
    }

    @Override
    public List<Record> getRecordList(Long id){
        return flowFormDeliverMapper.getTurnOverRecList(id);
    }
}
