package org.quetzaco.security.dms.constant;

public interface DMSRightConstants {
	/**
	 *  每个key对应的值 都与数据库存储的 权限ID相对应
	 */
	
	static String CAN_COPY = "001";
	static String CAN_PRTINT = "002";
	
	static String CHECKIN_DOCUMENTS = "003";
	static String CHECKOUT_DOCUMENTS = "004";
	
	static String COPY_DOCUMENTS = "005";
	static String COPY_FOLDERS = "006";
	static String COPY_LINKS = "007";
	
	static String CREATE_DOCUMENTS = "008";
	static String CREATE_EMPTYDOCUMENTS = "009";
	static String CREATE_FOLDERS = "010";
	static String CREATE_LINKS = "011";
	
	static String DELETE_CABINETS = "012";
	static String DELETE_DOCUMENTS = "013";
	static String DELETE_FOLDERS = "014";
	static String DELETE_LINKS = "015";
	
	static String EDIT_CABINETS = "016";
	static String EDIT_FOLDERS = "017";
	static String EDIT_LINKS = "018";
	
	static String GET_CABINET_DETAILS = "019";
	static String GET_DOCUMENT_DETAILS = "020";
	static String GET_DOCUMENT_REVISIONS = "021";
	static String GET_FOLDER_DETAILS = "022";
	static String GET_FOLDER_ITEMS = "023";
	static String GET_ITEMS = "024";
	static String GET_LINK_DETAILS = "025";
	static String GET_SECURITY = "026";
	static String LOCK_DOCUMENTS = "027";
	
	static String MOVE_DOCUMENTS = "028";
	static String MOVE_FOLDERS = "029";
	static String MOVE_LINKS = "030";
	static String SET_SECURITY = "031";
	static String UNLOCK_DOCUMENTS = "032";
	
	static String UPDATE_DOCUMENTS = "033";
	static String VIEW_DOCUMEN_TDETAILS = "034";
	static String VIEW_DOCUMENTS = "035";
	static String SHOW_ACCESSLOG = "036";
	static String VIEW_DISCUSSION = "037";
	static String CREATE_DISCUSSION = "038";
	static String CHECKIN_FOLDERS = "039";
	
	static String CREATE_CABINET = "040";
	//static String EDIT_DOCUMENTS = "041";

}
