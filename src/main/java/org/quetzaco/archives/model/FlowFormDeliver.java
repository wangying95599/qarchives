package org.quetzaco.archives.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class FlowFormDeliver implements FlowsParent{
    private Long flowId;

    private String title;

    private String deliverUser;

    private Long receiveUser;

    private String deliverOffice;

    private Long deliverDeptId;

    private Long receiveDeptId;

    private String description;

    private String recordFlag;

    private Flows flows;

    private List<Documents> documentsList;

    private List<Record>  recordList;

    private Dept deliverDept;

    private Dept receiveDept;

    public Dept getDeliverDept() {
        return deliverDept;
    }

    public void setDeliverDept(Dept deliverDept) {
        this.deliverDept = deliverDept;
    }

    public Dept getReceiveDept() {
        return receiveDept;
    }

    public void setReceiveDept(Dept receiveDept) {
        this.receiveDept = receiveDept;
    }

    public List<Documents> getDocumentsList() {
        return documentsList;
    }

    public void setDocumentsList(List<Documents> documentsList) {
        this.documentsList = documentsList;
    }

    public List<Record> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Record> recordList) {
        this.recordList = recordList;
    }

    public Flows getFlows() {
        return flows;
    }

    public void setFlows(Flows flows) {
        this.flows = flows;
    }

    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDeliverUser() {
        return deliverUser;
    }

    public void setDeliverUser(String deliverUser) {
        this.deliverUser = deliverUser == null ? null : deliverUser.trim();
    }

    public Long getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(Long receiveUser) {
        this.receiveUser = receiveUser;
    }

    public String getDeliverOffice() {
        return deliverOffice;
    }

    public void setDeliverOffice(String deliverOffice) {
        this.deliverOffice = deliverOffice == null ? null : deliverOffice.trim();
    }

    public Long getDeliverDeptId() {
        return deliverDeptId;
    }

    public void setDeliverDeptId(Long deliverDeptId) {
        this.deliverDeptId = deliverDeptId;
    }

    public Long getReceiveDeptId() {
        return receiveDeptId;
    }

    public void setReceiveDeptId(Long receiveDeptId) {
        this.receiveDeptId = receiveDeptId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(String recordFlag) {
        this.recordFlag = recordFlag == null ? null : recordFlag.trim();
    }
}