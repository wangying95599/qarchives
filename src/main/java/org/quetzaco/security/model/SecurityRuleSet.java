package org.quetzaco.security.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.quetzaco.archives.model.Space;
import org.quetzaco.archives.model.User;

/**
 * 安全集
 * @author dong
 *
 */
public class SecurityRuleSet  implements Serializable{
	
	private String rulesetId = null;

	private String name = null;
	
	private Space spaceModel = null;
	
	private String rulesetType = "SY";  // default SY 
	
	private String rulesetObjectType = null;
	
	private User contextUser = null;
	
	private ArrayList<SecurityRuleSetItem> items = new ArrayList<SecurityRuleSetItem>();

	public String getRulesetType() {
		return rulesetType;
	}

	public void setRulesetType(String rulesetType) {
		this.rulesetType = rulesetType;
	}

	public String getRulesetObjectType() {
		return rulesetObjectType;
	}

	public void setRulesetObjectType(String rulesetObjectType) {
		this.rulesetObjectType = rulesetObjectType;
	}

	
	public String getRulesetId() {
		return rulesetId;
	}

	public void setRulesetId(String rulesetId) {
		this.rulesetId = rulesetId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Space getSpaceModel() {
		return spaceModel;
	}

	public void setSpaceModel(Space spaceModel) {
		this.spaceModel = spaceModel;
	}

	public User getContextUser() {
		return contextUser;
	}

	public void setContextUser(User contextUser) {
		this.contextUser = contextUser;
	}

	public ArrayList<SecurityRuleSetItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<SecurityRuleSetItem> items) {
		this.items = items;
	}

	
}
