package org.quetzaco.security.dms.model;

import java.io.Serializable;
import java.sql.Timestamp;

import org.quetzaco.archives.model.BaseModel;
/**
 * 被授予的权限条目
 * @author dong
 *
 */
public class Permission  implements Serializable{
	/**
	 * 被授予权限的人的ID
	 */
	private String  assigneeId = null ; //  assignee model
	/**
	 * 被授予权限的人的名称
	 */
	private String assigneeName = null;
	/**
	 * 被授予权限的人的类型
	 */
	private String  assigneeType = null ; 
	/**
	 * 被授予的权限
	 */
	private RightGroup rgtGroup = null;
	/**
	 * 被授予权限的过期时间ID 
	 */
	private String expiredId= null ;
	/**
	 * 被授予权限的过期时间
	 */
	private Timestamp expiredDate = null ; // no expired Date   will be  null   mean  will not be expired


	private BaseModel bTargetModel = null ; // no expired Date   will be  null   mean  will not be expired

	
	/**
	 * 被授予权限的人的ID ，
	 * 有可能是User 或者group
	 * @return
	 */
	public String getAssigneeId() {
		return assigneeId;
	}
	/**
	 * 被授予权限的人的ID ，
	 * 有可能是User 或者group
	 * @param assigneeId 
	 */
	public void setAssigneeId(String assigneeId) {
		this.assigneeId = assigneeId;
	}
	/**
	 * 被授予权限的人的名称 ，
	 * 有可能是User 或者group
	 * @return
	 */
	public String getAssigneeName() {
		return assigneeName;
	}
	/**
	 * 被授予权限的人的名称 ，
	 * 有可能是User 或者group
	 * @param assigneeName
	 */
	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}
	/**
	 * 被授予权限的人的类型 ，
	 * 有可能是User 或者group
	 * @return  usr 或者 grp
	 */
	public String getAssigneeType() {
		return assigneeType;
	}
	/**
	 * 被授予权限的人的类型 ，
	 * 有可能是User 或者group
	 * assigneeType  值只可以赋予 usr 或者 grp;
	 * 					默认为usr
	 */
	public void setAssigneeType(String assigneeType) {
		if("usr".equalsIgnoreCase(assigneeType))
			this.assigneeType = assigneeType;
		else if("grp".equalsIgnoreCase(assigneeType))
			this.assigneeType = assigneeType;
		else
			this.assigneeType = "usr";
	}
	/**
	 * 被授予的权限组
	 * @return
	 */
	public RightGroup getRgtGroup() {
		return rgtGroup;
	}
	/**
	 * 被授予的权限组
	 * @param rgtGroup
	 */
	public void setRgtGroup(RightGroup rgtGroup) {
		this.rgtGroup = rgtGroup;
	}
	/**
	 * 授予权限的过期时间Id
	 * @param expiredId
	 */
	public void setExpiredId(String expiredId) {
		this.expiredId = expiredId;
	}
	/**
	 * 授予权限的过期时间Id
	 * @return 如果此权限永不过期 将返回NULL
	 */
	public String getExpiredId() {
		return expiredId;
	}
	/**
	 * 授予权限的过期时间
	 * @return  如果此权限永不过期 将返回NULL
	 */
	public Timestamp getExpiredDate() {
		return expiredDate;
	}
	/**
	 * 授予权限的过期时间
	 * @param expiredDate 
	 */
	public void setExpiredDate(Timestamp expiredDate) {
		this.expiredDate = expiredDate;
	}
	

	public String getAssigneeFirstName(){
		return assigneeName.split(" ")[0];
	}
	public String getAssigneeLastName(){
		return assigneeName.split(" ").length>=2?assigneeName.substring(assigneeName.indexOf(" "),assigneeName.length()):"";
	}
	public BaseModel getTargetModel() {
		return bTargetModel;
	}
	public void setTargetModel(BaseModel targetModel) {
		this.bTargetModel = targetModel;
	}
}
