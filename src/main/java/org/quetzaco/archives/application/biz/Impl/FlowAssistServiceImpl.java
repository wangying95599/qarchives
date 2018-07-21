package org.quetzaco.archives.application.biz.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.quetzaco.archives.application.biz.*;
import org.quetzaco.archives.application.dao.DocumentsMapper;
import org.quetzaco.archives.application.dao.FlowFormAssistMapper;
import org.quetzaco.archives.application.dao.FlowNodeHistoriesMapper;
import org.quetzaco.archives.application.dao.RecordMapper;
import org.quetzaco.archives.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FlowAssistServiceImpl implements FlowAssistService {
    @Autowired
    FlowFormAssistMapper flowFormAssistMapper;
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

    final static Logger LOGGER = LoggerFactory.getLogger(FlowAssistServiceImpl.class);

    @Override
    @Transactional
    public void start(FlowFormAssist form,User user) {
        String name = user.getName();
        String tt = name +" 给您分配了一个协查流程，请您及时处理";
      Long flowId = flowService.start(form.getFlows(),tt);
      form.setFlowId(flowId);
      flowFormAssistMapper.insert(form);
    }
    
    @Override
    public PageInfo selectStartByMeList(User contextUser, int pageNum, int pageSize) {
      PageHelper.startPage(pageNum, pageSize);
      return new PageInfo(flowFormAssistMapper.selectStartByMeList(contextUser));
    }
    
    @Override
    public PageInfo getTurnOverToMe(User user, String type, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);//type 00（全部）01（未完成）02（已完成）
        List<FlowFormAssist> list = flowFormAssistMapper.getTurnOverToMe(user.getId(),type);
        return new PageInfo(list);
    }
    
    @Override
    public FlowFormAssist getInfo(Long flowId) {
    	FlowFormAssist form = flowFormAssistMapper.getInfo(flowId);

//        List<Documents> documentsList = getDocumentsList(flowId);
//        List<Record> recordList = getRecordList(flowId);
//        flowFormDeliver.setDocumentsList(documentsList);
//        flowFormDeliver.setRecordList(recordList);
        return form;
    }
    
    @Transactional
    @Override
    /**
     * action:to_createdy,to_companyadmin,to_deptadmin
     * 
     */
    public void process(User user, Long flowId, String action,Long assigneeId) {
        FlowNodes flowNodes = flowNodesService.getNodesByFlowAndUser(flowId, user.getId()).get(0);
        FlowNodeHistories flowNodeHistories = new FlowNodeHistories();
        flowNodeHistories.setNodeId(flowNodes.getId());
        flowNodeHistories.setType(flowNodes.getType());
        flowNodeHistories.setFlowId(flowNodes.getFlowId());
        flowNodeHistories.setAssigneeId(flowNodes.getAssigneeId());
        if(action!=null){
            flowNodeHistories.setAction(action);
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
        if(assigneeId!=null){
//            FlowFormDeliver flowFormDeliver = new FlowFormDeliver();
//            flowFormDeliver.setReceiveUser(receiveUser);
//            FlowFormDeliverExample flowFormDeliverExample = new FlowFormDeliverExample();
//            flowFormDeliverExample.createCriteria().andFlowIdEqualTo(flowId);
//            flowFormDeliverMapper.updateByExampleSelective(flowFormDeliver, flowFormDeliverExample);
            FlowNodes flowNodes1 = new FlowNodes();
            flowNodes1.setAssigneeId(assigneeId);
            flowNodes1.setAssigneeBy(user.getId());
            flowNodes1.setType(flowNodes.getType());
            flowNodes1.setFlowId(flowId);
            flowNodes1.setDeadLine(flowNodes.getDeadLine());
            flowNodes1.setCreatedDt(new Date());
            flowNodes1.setRecordFlag(true);
            flowNodesList.add(flowNodes1);
        }

        Flows flows = new Flows();
        flows.setId(flowId);
        flows.setNodes(flowNodesList);
        flows.setType(flowNodes.getType());

        flowService.proccess(flows, flowNodeHistories);
        
        if("to_CAdmin".equals(action)) {
        	FlowFormAssist r = new FlowFormAssist();
        	r.setAssitUser(assigneeId);
        	FlowFormAssistExample e = new FlowFormAssistExample();
        	e.createCriteria().andFlowIdEqualTo(flowId);
        	flowFormAssistMapper.updateByExampleSelective(r, e);
        }
    }
    
    @Transactional
    @Override
    public void updateAsssitForm(FlowFormAssist flowFormAssist) {
        if (flowFormAssist == null || flowFormAssist.getFlowId() == null) {
            return;
        }
        FlowFormAssist r =new FlowFormAssist();
        r.setAssitUser(flowFormAssist.getAssigneeId());
    	r.setAssistContent(flowFormAssist.getAssistContent());
    	r.setDescription(flowFormAssist.getDescription());
    	FlowFormAssistExample e = new FlowFormAssistExample();
    	e.createCriteria().andFlowIdEqualTo(flowFormAssist.getFlowId());
    	flowFormAssistMapper.updateByExampleSelective(r, e);

    }
    
    @Transactional
    @Override
    public void end(FlowFormAssist flowFormAssist, FlowNodeHistories flowNodeHistories) {
        if (flowFormAssist == null || flowFormAssist.getFlows() == null) {
            throw new RuntimeException(" flowFormAssist is null or empty ");
        }
        Flows flows = flowFormAssist.getFlows();
        flowService.end(flows, flowNodeHistories);

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

        FlowFormAssist assist = new FlowFormAssist();
        assist.setFlowId(flowId);
        assist.setFlows(flow);
        end(assist,flowNodeHistories);
    }

    public void saveDocument(Long flowId,String id,String type) {
    	 DateFormat dateFormat = DateFormat.getDateInstance();
         String date = dateFormat.format(new Date());
    	if("doc".equals(type)) {
	    	if(id!=null && id.contains("_")) {
	    		String[] ids = id.split("_");
	    		for(String idTemp :ids) {
	    			flowFormAssistMapper.saveDocument(flowId, null,null,idTemp, date);
	    		}
	    	}else {
	    		flowFormAssistMapper.saveDocument(flowId, null,null,id, date);
	    	}
    	}else if("record".equals(type)) {
    		if(id!=null && id.contains("_")) {
	    		String[] ids = id.split("_");
	    		for(String idTemp :ids) {
	    			flowFormAssistMapper.saveDocument(flowId, Long.parseLong(idTemp),null,null, date);
	    		}
	    	}else {
	    		flowFormAssistMapper.saveDocument(flowId, Long.parseLong(id),null,null, date);
	    	}
    	}else {
    		//recdoc
    		if(id!=null && id.contains("_")) {
	    		String[] ids = id.split("_");
	    		for(String idTemp :ids) {
	    			flowFormAssistMapper.saveDocument(flowId, null,Long.parseLong(idTemp),null, date);
	    		}
	    	}else {
	    		flowFormAssistMapper.saveDocument(flowId, null,Long.parseLong(id),null, date);
	    	}
    	}
    }
    
    public PageInfo getDocument(Long flowId) {
    	 List list = documentsMapper.searchDocumentListByIds(flowId);
    	 List list2= recordMapper.searchRecordListByIds(flowId);

		System.out.println("doc "+list);
		System.out.println("rec "+list2);
		list.addAll(list2);
		
		PageInfo page= new PageInfo(list);
		return page;
	}
	 
	public void deleteDocument(Long flowId,String id,String type) {
		DateFormat dateFormat = DateFormat.getDateInstance();
        String date = dateFormat.format(new Date());
		if("doc".equals(type)) {
			flowFormAssistMapper.deleteDocument(flowId,null,null,id,date);
		}else if("record".equals(type))  {
			flowFormAssistMapper.deleteDocument(flowId,Long.parseLong(id),null,null,date);
		}else{
			flowFormAssistMapper.deleteDocument(flowId,null,Long.parseLong(id),null,date);
		}
	}
}
