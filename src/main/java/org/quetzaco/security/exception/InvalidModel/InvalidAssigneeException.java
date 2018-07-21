package org.quetzaco.security.exception.InvalidModel;

import org.quetzaco.security.exception.InvalidModelException;

public class InvalidAssigneeException extends InvalidModelException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidAssigneeException() {
		super("Assignee");
		// TODO Auto-generated constructor stub
	}

	public InvalidAssigneeException(String className) {
		super(className);
		// TODO Auto-generated constructor stub
	}

}
