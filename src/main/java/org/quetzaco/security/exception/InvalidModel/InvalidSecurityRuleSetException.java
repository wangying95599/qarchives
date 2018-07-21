package org.quetzaco.security.exception.InvalidModel;

import org.quetzaco.security.exception.InvalidModelException;
import org.quetzaco.security.model.SecurityRuleSet;

public class InvalidSecurityRuleSetException extends InvalidModelException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidSecurityRuleSetException() {
		super(SecurityRuleSet.class.getName());
	}
	
}
