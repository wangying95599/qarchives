package org.quetzaco.security.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SecurityReadQuery {
	final static Logger logger = LoggerFactory.getLogger(SecurityReadQuery.class);
	private static SecurityReadQuery _instance= null;
	public static SecurityReadQuery getInstance()
		throws MissingResourceException, NullPointerException {
		if (null == _instance) {
			_instance = new SecurityReadQuery();
		}
		return _instance;
	}
	
	private SecurityReadQuery(){
		_sortBundle = ResourceBundle.getBundle(sortFileName);
	}
	
	private String sortFileName = "sortablecolumns";
	
	private ResourceBundle _sortBundle = null;

	public String getSortQueryValue(String strValue)
			throws MissingResourceException, ClassCastException {
			logger.debug((_sortBundle.getString(strValue).trim()));
			return (_sortBundle.getString(strValue).trim());
		}
}
