package org.quetzaco.security.exception;

import java.io.Serializable;

/**
 * 安全总异常
 * @author dong
 *
 */
public class SecurityException extends Exception  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SecurityException() {
		super();
		
	}

	public SecurityException(String message, Throwable cause) {
		super(message, cause);
	}

	public SecurityException(String message) {
		super(message);
	}

	public SecurityException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "[SecurityException]:"+super.getMessage();
	}

	
}
