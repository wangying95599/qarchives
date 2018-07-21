package org.quetzaco.security.exception.InvalidModel;

import org.quetzaco.security.exception.InvalidModelException;
import org.quetzaco.security.model.SecurityRuleSetItem;

public class InvalidSecurityRuleSetItemException extends InvalidModelException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidSecurityRuleSetItemException() {
		super(SecurityRuleSetItem.class.getName());
	}
	
}
