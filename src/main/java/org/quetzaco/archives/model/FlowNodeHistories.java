package org.quetzaco.archives.model;

import java.util.Date;

public class FlowNodeHistories {
    private Long nodeId;

    private String type;

    private Long flowId;

    private String action;

    private String description;

    private Long assigneeId;

    private Date createdDt;

    private Date deadLine;

    private Long assigneeBy;

    private Boolean recordFlag;

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action == null ? null : action.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Long getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Long assigneeId) {
        this.assigneeId = assigneeId;
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public Long getAssigneeBy() {
        return assigneeBy;
    }

    public void setAssigneeBy(Long assigneeBy) {
        this.assigneeBy = assigneeBy;
    }

    public Boolean getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(Boolean recordFlag) {
        this.recordFlag = recordFlag;
    }
}