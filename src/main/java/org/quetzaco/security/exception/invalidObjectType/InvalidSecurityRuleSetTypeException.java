package org.quetzaco.security.exception.invalidObjectType;

import org.quetzaco.security.exception.InvalidObjectTypeException;
import org.quetzaco.security.model.SecurityRuleSet;

public class InvalidSecurityRuleSetTypeException extends InvalidObjectTypeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidSecurityRuleSetTypeException() {
		super(SecurityRuleSet.class.getName());
	}
	

}
