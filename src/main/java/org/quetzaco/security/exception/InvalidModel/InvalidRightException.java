package org.quetzaco.security.exception.InvalidModel;

import org.quetzaco.security.dms.model.Right;
import org.quetzaco.security.exception.InvalidModelException;

public class InvalidRightException extends InvalidModelException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidRightException() {
		super(Right.class.getName());
	}
	
}
