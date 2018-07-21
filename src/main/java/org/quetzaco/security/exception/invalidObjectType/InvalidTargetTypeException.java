package org.quetzaco.security.exception.invalidObjectType;

import org.quetzaco.security.exception.InvalidObjectTypeException;

public class InvalidTargetTypeException extends InvalidObjectTypeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidTargetTypeException() {
		super("TargetModel");
	}
	
	public InvalidTargetTypeException(String className) {
		super(className);
	}

}
