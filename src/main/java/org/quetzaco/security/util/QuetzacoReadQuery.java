/******************************************************************************
 *      					Quetzaco, Inc.
 *******************************************************************************
 *  File Name	: 	QuetzacoReadQuery.java
 *  Package Name	: 	org.quetzaco.commoncomponents.global.util
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
package org.quetzaco.security.util;


import java.lang.reflect.InvocationTargetException;
import java.util.MissingResourceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuetzacoReadQuery {
	public static final String sQuetzacoSecurityQueryFile = "QuetzacoSecuritySQL";
	final static Logger logger = LoggerFactory.getLogger(QuetzacoReadQuery.class);
	private static final String DATA_SOURCE_SQL_PACKAGE = "org.quetzaco.security.database";
	
	private static QuetzacoReadQuery sInstance = null;
	public static QuetzacoReadQuery instance() throws MissingResourceException, NullPointerException {
		if (null == sInstance) {
			sInstance = new QuetzacoReadQuery();
		}
		return sInstance;
	}

	public String getQuetzacoSecurityQueryValue(String strValue) throws MissingResourceException, ClassCastException, NullPointerException {
		return getQuery(sQuetzacoSecurityQueryFile, strValue);
	}

	public String getQuery(String strClassName, String strKey) {
		return getQuery(strClassName, strKey, null, null);
	}
	public String getQuery(String strClassName, String strKey, Class[] arrParamTypes, Object[] arrParams) {
		String strActualDataSourceSQLPackage = "";
		String strQuery = null;
		strActualDataSourceSQLPackage = DATA_SOURCE_SQL_PACKAGE + (("".equals(strActualDataSourceSQLPackage)) ? "" : ("." + strActualDataSourceSQLPackage));
		logger.debug(strActualDataSourceSQLPackage+"====ClassName======="+strClassName +"========Key============="+strKey );
		try {
			Class t = fillClass(strClassName, strActualDataSourceSQLPackage);
			strQuery = fillQuery(strKey, t, arrParamTypes, arrParams);
			logger.debug("Query======="+strQuery);
		}catch (Exception e) {
			e.printStackTrace();;
		}
		if (strQuery == null)
			throw new NullPointerException("KEY  " + strKey + " in " + strClassName + " got No value");
		return strQuery;
	}

	protected String fillQuery(String strKey, Class t, Class[] arrParamTypes, Object[] arrParams) throws IllegalAccessException, InvocationTargetException,
	NoSuchFieldException, NoSuchMethodException {
			String strQuery = null;
			if (arrParams == null && arrParamTypes == null)
				try {
					strQuery = (String) t.getField(strKey).get(null);
				} catch (NoSuchFieldException e) {
					try {
						strQuery = (String) t.getMethod(strKey).invoke(null);
					} catch (NoSuchMethodException eM) {
						throw e;
					}
				}
			else {
				strQuery = (String) t.getMethod(strKey, arrParamTypes).invoke(null, arrParams);
			}
			return strQuery;
       }

	protected Class fillClass(String strClassName, String strActualDataSourceSQLPackage) throws ClassNotFoundException {
		 Class t = null;
		try {
			t = Class.forName(strActualDataSourceSQLPackage + "." + strClassName,false,this.getClass().getClassLoader());
		} catch (ClassNotFoundException e) {
			if (!"".equals(strActualDataSourceSQLPackage))
				try {
					t = Class.forName(DATA_SOURCE_SQL_PACKAGE + "." + strClassName,false,this.getClass().getClassLoader());
				} catch (ClassNotFoundException e1) {
					throw e;
				}
			else
				throw e;
		}
		return t;
	}
}
