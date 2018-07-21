package org.quetzaco.archives.model;

public class FlowFormAssist implements   FlowsParent{
    private Long flowId;

    private String applyDays;

    private String applyUser;

    private Long assitUser;

    private String applyId;

    private String office;

    private String dept;

    private String assistType;

    private String assistContent;

    private String description;

    private String recordFlag;
    
    private Flows flows;
    
    private Long assigneeId;
    
    private String createdBy;
    
    private String iphone;

    private String title;

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIphone() {
		return iphone;
	}

	public void setIphone(String iphone) {
		this.iphone = iphone;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Long getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(Long assigneeId) {
		this.assigneeId = assigneeId;
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

    public String getApplyDays() {
        return applyDays;
    }

    public void setApplyDays(String applyDays) {
        this.applyDays = applyDays == null ? null : applyDays.trim();
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser == null ? null : applyUser.trim();
    }

    public Long getAssitUser() {
        return assitUser;
    }

    public void setAssitUser(Long assitUser) {
        this.assitUser = assitUser;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office == null ? null : office.trim();
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept == null ? null : dept.trim();
    }

    public String getAssistType() {
        return assistType;
    }

    public void setAssistType(String assistType) {
        this.assistType = assistType == null ? null : assistType.trim();
    }

    public String getAssistContent() {
        return assistContent;
    }

    public void setAssistContent(String assistContent) {
        this.assistContent = assistContent == null ? null : assistContent.trim();
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