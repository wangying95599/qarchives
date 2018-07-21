package org.quetzaco.security.exception.InvalidModel;

import org.quetzaco.security.dms.model.RightGroup;
import org.quetzaco.security.exception.InvalidModelException;

public class InvalidRightGroupException extends InvalidModelException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidRightGroupException() {
		super(RightGroup.class.getName());
	}
	
	public InvalidRightGroupException(String paramName) {
		super(RightGroup.class.getName()+"  "+paramName);
	}
	
}
