package org.quetzaco.archives.application.biz;

import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;

import org.quetzaco.archives.model.FlowFormLending;
import org.quetzaco.archives.model.FlowNodeHistories;
import org.quetzaco.archives.model.Flows;
import org.quetzaco.archives.model.User;

public interface FlowLendingService {

  void start(FlowFormLending flowFormLending);

  PageInfo selectStartByMeList(User contextUser, int pageNum, int pageSize,String[] searchInfo);

  PageInfo getProcessByMe(Long usrId,Boolean isProcessed, int pageNum, int pageSize,String[] searchInfo);

  FlowFormLending getFormDetail(FlowFormLending flowFormLending);

  FlowFormLending getFlowHistory(FlowFormLending flowFormLending);

  void end(FlowFormLending flowFormLending, FlowNodeHistories flowNodeHistories);
  void end(User user, Long flowId, String result, String description);

  Map getFlowLendingDetail(FlowFormLending flowFormLending);

  Map<String,List<User>> getAskUsers(Long deptId, Long fileDeptId);

  FlowFormLending getLendingInfo(Long flowId);

  Long start(User user,FlowFormLending flowFormLending, List<Long> docIds, List<Long> revIds,String type,Long assitFlowId);

  void proccess(Long flowId, User user, String action, String description, Long[] checks);

  int confirmGiveBack(Long flowId);
}