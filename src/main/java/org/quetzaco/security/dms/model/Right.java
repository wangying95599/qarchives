package org.quetzaco.security.dms.model;

import java.io.Serializable;

/**
 * 权限类
 * @author dong
 *
 */
public class Right  implements Serializable{
	/**
	 * 权限Id
	 */
	private String rightId = null;
	/**
	 * 权限名称
	 */
	private String name = null;
	/**
	 * 权限Id
	 * @return
	 */
	public String getRightId() {
		return rightId;
	}
	/**
	 * 权限Id
	 * @param rightId
	 */
	public void setRightId(String rightId) {
		this.rightId = rightId;
	}
	/**
	 * 权限名称
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * 权限名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

}
