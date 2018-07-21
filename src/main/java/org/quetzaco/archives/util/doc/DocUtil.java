package org.quetzaco.archives.util.doc;

public class DocUtil {

	/**
	 * 
	 * @return
	 */
	public static String getGUID() {
		RandomGUID randomGuid = new RandomGUID();
		return randomGuid.getGUID();
	}
	
	
}
