package org.quetzaco.security.exception.invalidObjectType;

import org.quetzaco.security.exception.InvalidObjectTypeException;
import org.quetzaco.security.model.SecurityRuleSet;

public class InvalidRuleSetObjectTypeException extends InvalidObjectTypeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidRuleSetObjectTypeException() {
		super(SecurityRuleSet.class.getName()+"的应用");
	}
	
}
