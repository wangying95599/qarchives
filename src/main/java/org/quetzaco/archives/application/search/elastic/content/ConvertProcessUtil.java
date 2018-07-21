/**
 * Copyright (c) 2004-2011 Deya Tech Inc. All rights reserved.
 */
package org.quetzaco.archives.application.search.elastic.content;

import java.io.File;

import org.quetzaco.server.utils.Configuration;

/**
 * 
 * @author Tony Liu
 * @version 1.0 2011/08/19
 */
public class ConvertProcessUtil extends ProcessUtil {

	public ConvertProcessUtil() {
		long timeout = Long.parseLong(Configuration.getProperty("CONVERT_PROCESS_TIMEOUT"));
		this.setTimeout(timeout);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Get system process command from Configuration.
	 * @param id - The Key in configure file.
	 * @param home - The default home path.
	 * @param tool - command program name.
	 * @return
	 */
	public static String getToolCMD(String id,String home,String tool){
		String path;
		if(Configuration.getProperty(id).equalsIgnoreCase("")){
			path = tool;
		}else if(Configuration.getProperty(id).equalsIgnoreCase(".")){
			path=System.getProperty("root")+File.separatorChar+"modules"+File.separatorChar+home+File.separatorChar+tool;
		}else{
			path=Configuration.getProperty(id)+File.separatorChar+tool;
		}	


		return path;
	}
}
