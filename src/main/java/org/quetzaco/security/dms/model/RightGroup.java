package org.quetzaco.security.dms.model;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * 权限组
 * @author dong
 *
 */
public class RightGroup  implements Serializable{

	public RightGroup(String rgtgrpId){
		this.rgtgrpId = rgtgrpId;
	}
	public RightGroup(){
	}
	/**
	 * 权限组Id
	 */
	private String rgtgrpId = null;
	/**
	 * 权限组名称
	 */
	private String name = null;
	/**
	 * 权限列表
	 */
	private ArrayList<Right> rights = new ArrayList<Right>();
	/**
	 * 权限组ID 
	 * @return
	 */
	public String getRgtgrpId() {
		return rgtgrpId;
	}
	/**
	 * 权限组ID 
	 * @param rgtgrpId
	 */
	public void setRgtgrpId(String rgtgrpId) {
		this.rgtgrpId = rgtgrpId;
	}
	/**
	 * 权限组名称
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * 权限组名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 权限组包含的权限
	 * @return
	 */
	public ArrayList<Right> getRights() {
		return rights;
	}
	/**
	 * 权限组包含的权限
	 * @param rights
	 */
	public void setRights(ArrayList<Right> rights) {
		this.rights = rights;
	}

}
