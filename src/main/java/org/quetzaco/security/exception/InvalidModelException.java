package org.quetzaco.security.exception;

import java.io.Serializable;

public class InvalidModelException extends SecurityException  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidModelException() {
		super("无效的对象");
	}

	public InvalidModelException(String clasName) {
		super("无效的"+clasName+"对象");
		// TODO Auto-generated constructor stub
	}

	
}
