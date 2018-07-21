package org.quetzaco.security.exception.InvalidModel;

import org.quetzaco.security.exception.InvalidModelException;

public class InvalidTargetModelException extends InvalidModelException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidTargetModelException() {
		super("TargetModel");
	}
	
	public InvalidTargetModelException(String className) {
		super(className);
	}

}
