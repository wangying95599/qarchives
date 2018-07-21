/******************************************************************************
*      					Quetzaco, Inc.
*******************************************************************************
*  File Name	: 	QuetzacoException.java
*  Package Name	: 	org.quetzaco.commoncomponents.global.model
*  Product Name	: 	Quetzaco
*  Version 		: 	2.5
*  Copyright	: 	Copyright 2003 - 2004, Quetzaco, Inc., All rights reserved.
*      					This software is the confidential and proprietary
*  information of Quetzaco, Inc. This software is provided under a license
*  agreement containing restrictions on use and disclosure and is protected
*  by copyright laws. Reverse engineering of the software is strictly
*  prohibited.
*     It is the customer's responsibility to ensure the proper use and
*  application of this  software and take all necessary and appropriate
*  measures for the safe use of such applications if the software is used
*  for such purposes.
*
******************************************************************************/
package org.quetzaco.security.exception;


public class DMSException extends Exception {

	private int iErrorCode = 0;
	private String strErrorMsg = null;
	private String strObjectId = null;
	
	public DMSException() {
		super();
	}

	public DMSException(int iErrorCode) {
		this.iErrorCode = iErrorCode;
	}

	public DMSException(String msg) {
		super(msg);
	}

	public int getErrorCode() {
		return iErrorCode;
	}

	public void setErrorCode(int iErrorCode) {
		this.iErrorCode = iErrorCode;
	}
	
	public void setErrorMsg(String strErrorMsg){
		this.strErrorMsg = strErrorMsg;
	}
	
	public String getErrorMsg(){
		return this.strErrorMsg;
	}

	public void setObjectId(String strObjectId) {
		this.strObjectId = strObjectId;
	}

	public String getObjectId() {
		return strObjectId;
	}
	
	
}
