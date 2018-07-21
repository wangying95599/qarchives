package org.quetzaco.security.dms.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.quetzaco.archives.model.BaseModel;
/**
 * dms对象权限设置
 * @author dong
 *
 */
public class ACL  implements Serializable{

	/**
	 * 使用的安全集类型  。
	 * 限定使用类型为-
	 * CD ：自定义安全集，默认；
	 * SY ： 默认安全集；
	 * UD ： 预定义安全集。
	 */
	private String rulesetType = "CD";   // default CD  | SY |UD 
	/**
	 * 是否设置继承-
	 * 0：非继承状态 ，默认
	 * 1：设置为继承（继承自别人或自己是继承根节点）
	 */
	private int isInherit  = 0;          // default 0   is not inherit from the  other
	/**
	 * 是否继承根节点-
	 * 0：不是继承跟节点，默认
	 * 1：是继承根节点
	 */
	private int isInheritRoot  = 0;          // default 0   is not inherit root
	/**
	 * 是否拥有匿名权限-
	 * 0：没有匿名权限，默认
	 * 1：拥有匿名权限
	 */
	private int hasAnonymousSec = 0;		// default 0   no Anonymous User in securityList
	
	/**
	 * dms 对象拥有的具体权限集合
	 * 
	 */
	private ArrayList<Permission> permissionList = new ArrayList<Permission>(); 
	
	/**
	 * dms 对象Owner
	 * 
	 */
	private BaseModel owner = null; 
	
	
	/**
	 * 继承根对象的ID
	 * 
	 */
	private String strInheritRootGuid = null; 
	

	/**
	 * 继承根对象的ID  
	 * 只有当此对象权限不为继承状态(isInherit==0)才起作用。
	 * @return
	 */
	public String getInheritRootGuid() {
		if(this.isInherit ==0)
			 this.strInheritRootGuid=null;
		return this.strInheritRootGuid;
	}
	/**
	 * 继承根对象的ID  
	 * 只有当此对象权限为继承状态(isInherit==1)的时候才起作用。
	 * @param strInheritRootGuid 只有当此对象权限为继承状态(isInherit==1)的时候，才可以赋予
	 */
	public void setInheritRootGuid(String strInheritRootGuid) {
		if(this.isInherit ==1)
			this.strInheritRootGuid = strInheritRootGuid;
		else
			this.strInheritRootGuid = null;
	}
	
	/**
	 * dms 对象Owner
	 * @return
	 */
	public BaseModel getOwner() {
		return owner;
	}
	/**
	 * dms 对象Owner
	 * @param BaseModel
	 */
	public void setOwner(BaseModel owner) {
		this.owner = owner;
	}
	/**
	 * 使用的安全集类型  。
	 * @return 
	 * CD ：自定义安全集，默认；
	 * SY ： 默认安全集；
	 * UD ： 预定义安全集。
	 */
	public String getRulesetType() {
		return rulesetType;
	}
	/**
	 * 使用的安全集类型  。
	 * @param rulesetType 
	 * CD ：自定义安全集，默认；
	 * SY ： 默认安全集；
	 * UD ： 预定义安全集。
	 */
	public void setRulesetType(String rulesetType) {
		if("CD".equalsIgnoreCase(rulesetType)
				||"SY".equalsIgnoreCase(rulesetType)
				||"UD".equalsIgnoreCase(rulesetType))
			this.rulesetType = rulesetType;
	}
	/**
	 * 是否设置继承
	 * @return
	 * 0：非继承状态 ，默认;
	 * 1：设置为继承（继承自别人或自己是继承根节点）
	 */
	public int isInheritance() {
		 if(this.isInheritRoot==1)
			this.isInherit = 1;
		 return this.isInherit==1?1:0;
	}
	/**
	 * 是否设置继承
	 * @param isInherit
	 * 0：非继承状态 ，默认;
	 * 1：设置为继承（继承自别的dms对象或自己是继承根节点）
	 */
	public void setInheritance(int isInherit) {
		if(1==isInherit)
			this.isInherit = isInherit;
	}
	/**
	 * 是否继承根节点 
	 * @return
	 * 0：不是继承跟节点，默认;
	 * 1：是继承根节点
	 */
	public int isInheritRoot() {
		return isInheritRoot==1?1:0;
	}
	/**
	 * 是否继承根节点 
	 * @param isInheritRoot
	 * 0：不是继承跟节点，默认;
	 * 1：是继承根节点
	 */
	public void setInheritRoot(int isInheritRoot) {
		if(1==isInheritRoot){
			this.isInheritRoot = isInheritRoot;
			this.isInherit = isInheritRoot;
		}
	}
	/**
	 * 是否拥有匿名权限-
	 * @return
	 * 0：没有匿名权限，默认;
	 * 1：拥有匿名权限
	 */
	public int hasAnonymousSec() {
		return hasAnonymousSec==1?1:0;
	}
	/**
	 * 是否拥有匿名权限-
	 * @param hasAnonymousSec
	 * 0：没有匿名权限，默认;
	 * 1：拥有匿名权限
	 */
	public void setAnonymousSec(int hasAnonymousSec) {
		if(1==hasAnonymousSec)
			this.hasAnonymousSec = hasAnonymousSec;
	}
	/**
	 * dms 对象拥有的具体权限集合
	 * @return 
	 */
	public ArrayList<Permission> getPermissionList() {
		return permissionList;
	}
	/**
	 * dms 对象拥有的具体权限集合
	 * @param permissionList 
	 */
	public void setPermissionList(ArrayList<Permission> permissionList) {
		this.permissionList = permissionList;
	}

}
