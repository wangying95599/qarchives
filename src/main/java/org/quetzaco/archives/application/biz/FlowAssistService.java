package org.quetzaco.archives.application.biz;

import org.quetzaco.archives.model.FlowFormAssist;
import org.quetzaco.archives.model.FlowNodeHistories;
import org.quetzaco.archives.model.User;

import com.github.pagehelper.PageInfo;

public interface FlowAssistService {
	 void start(FlowFormAssist flowFormAssist,User user);
	 
	 PageInfo selectStartByMeList(User contextUser, int pageNum, int pageSize);
	 
	 PageInfo getTurnOverToMe(User user, String type, int pageNum, int pageSize);
	 
	 FlowFormAssist getInfo(Long flowId);
	 
	 void process(User user, Long flowId, String action,Long userId);
	 
	 void end(FlowFormAssist flowFormDeliver, FlowNodeHistories flowNodeHistories);

	void end(User user, Long flowId, String result, String description);

	 void saveDocument(Long flowId,String id,String type);
	 
	 PageInfo getDocument(Long flowId);
	 
	 void deleteDocument(Long flowId,String id,String type);
	 
	 void updateAsssitForm(FlowFormAssist flowFormAssist);
}
