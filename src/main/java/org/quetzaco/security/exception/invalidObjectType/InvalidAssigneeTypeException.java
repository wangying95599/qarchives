package org.quetzaco.security.exception.invalidObjectType;

import org.quetzaco.security.exception.InvalidObjectTypeException;

public class InvalidAssigneeTypeException extends InvalidObjectTypeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidAssigneeTypeException() {
		super("Assignee");
	}
	
	public InvalidAssigneeTypeException(String className) {
		super(className);
	}

}
