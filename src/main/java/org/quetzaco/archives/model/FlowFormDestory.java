package org.quetzaco.archives.model;

import java.util.List;

public class FlowFormDestory  implements PrimaryKey ,FlowsParent {
    private Long flowId;

    private String title;

    private String reelNum;

    private String reelType;

    private Integer reelSize;

    private String  destoryUser;

    private String superviseUser;

    private String description;

    private List<Documents> documentsList;

    private List<Record> recordList;

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

    private boolean recordFlag = true;
    private Flows flows;
    private Object destoryModel;
    private Long deptId;

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

    public String getReelNum() {
        return reelNum;
    }

    public void setReelNum(String reelNum) {
        this.reelNum = reelNum == null ? null : reelNum.trim();
    }

    public String getReelType() {
        return reelType;
    }

    public void setReelType(String reelType) {
        this.reelType = reelType == null ? null : reelType.trim();
    }

    public Integer getReelSize() {
        return reelSize;
    }

    public void setReelSize(Integer reelSize) {
        this.reelSize = reelSize;
    }

    public String getDestoryUser() {
        return destoryUser;
    }

    public void setDestoryUser(String destoryUser) {
        this.destoryUser = destoryUser;
    }

    public String getSuperviseUser() {
        return superviseUser;
    }

    public void setSuperviseUser(String superviseUser) {
        this.superviseUser = superviseUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Boolean getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(Boolean recordFlag) {
        this.recordFlag = recordFlag;
    }

    public Flows getFlows() {
        return flows;
    }

    public void setFlows(Flows flows) {
        this.flows = flows;
    }

    public void setDestoryModel(Object destoryModel) {
        this.destoryModel = destoryModel;
    }

    public <T> T getDestoryModel() {
        return (T)destoryModel;
    }

    @Override
    public Long getId() {
        return getFlowId();
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}