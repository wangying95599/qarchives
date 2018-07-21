package org.quetzaco.security.model;

import java.io.Serializable;

import org.quetzaco.security.dms.model.RightGroup;


public class SecurityRuleSetItem  implements Serializable{


	
	private String  assigneeId = null;  
	
	private String assigneeName = null;
	
	private String  assigneeType = null; // usr |grp   
	
	private RightGroup rgtGroup = null;


	public RightGroup getRgtGroup() {
		return rgtGroup;
	}

	public void setRgtGroup(RightGroup rgtGroup) {
		this.rgtGroup = rgtGroup;
	}

	public String getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(String assigneeId) {
		this.assigneeId = assigneeId;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public String getAssigneeType() {
		return assigneeType;
	}

	public void setAssigneeType(String assigneeType) {
		this.assigneeType = assigneeType;
	}

}
