package org.quetzaco.archives.application.biz;

import com.github.pagehelper.PageInfo;
import org.quetzaco.archives.model.*;
import org.quetzaco.archives.model.api.APIEntity;
import org.springframework.http.HttpEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FlowDeliverService {
    PageInfo getTurnOverFromMe(User user, int pageNum, int pageSize);

    PageInfo getTurnOverToMe(User user, String type, int pageNum, int pageSize);

    Long start(FlowFormDeliver flowFormDeliver,List<Long> docIds,List<Long> revIds);

    void end(FlowFormDeliver flowFormDeliver, FlowNodeHistories flowNodeHistories);

    List<Documents> getDocumentsList(Long id);

    List<Record> getRecordList(Long id);

    void insertLinkFlowDoc(List<Long> docIds, List<Long> revIds, Long flowId);

    void process(User user, Long flowId, String action, String description, Long leader, Long receiveUser, Long deptId);

    FlowFormDeliver getDeliverInfo(Long flowId);

    List<User> getManagerInReceiveDept(Long deptId);

    List<Long> getDuplicateInTurnOver(String fileType, Long[] docIds);

    int startForC2C(User user, FlowFormDeliver flowFormDeliver);
}
