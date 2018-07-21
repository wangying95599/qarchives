package org.quetzaco.security.exception;

public class InvalidObjectTypeException extends SecurityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidObjectTypeException() {
		super("无效的对象类型");
	}

	public InvalidObjectTypeException(String className) {
		super("无效的"+className+"对象类型");
	}
	

}
