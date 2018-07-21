package org.quetzaco.archives.model;

import java.util.List;

public class FlowFormLending implements FlowsParent{
    private Long flowId;

    private String title;

    private Long depId;

    private String lendingUser;

    private String lendingPermission;

    private String description;

    private Boolean recordFlag;

    private Long managerId;

    private String lendingType;//=01时借阅本部门 02 时借阅外部门

    private Long hostId;

    private Long loanDeptId;

    private Flows flows;

    private List<Documents> documentsList;

    private List<Record> recordList;

    public Flows getFlows() {
        return flows;
    }

    public void setFlows(Flows flows) {
        this.flows = flows;
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

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }

    public String getLendingUser() {
        return lendingUser;
    }

    public void setLendingUser(String lendingUser) {
        this.lendingUser = lendingUser == null ? null : lendingUser.trim();
    }

    public String getLendingPermission() {
        return lendingPermission;
    }

    public void setLendingPermission(String lendingPermission) {
        this.lendingPermission = lendingPermission == null ? null : lendingPermission.trim();
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

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getLendingType() {
        return lendingType;
    }

    public void setLendingType(String lendingType) {
        this.lendingType = lendingType == null ? null : lendingType.trim();
    }

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public Long getLoanDeptId() {
        return loanDeptId;
    }

    public void setLoanDeptId(Long loanDeptId) {
        this.loanDeptId = loanDeptId;
    }

    public ScheduleToOa getScheToOA(){
        ScheduleToOa scheduleToOa = new ScheduleToOa();
        // scheduleToOa.setProccessid(flowId.toString());
        return  scheduleToOa;
    }
}