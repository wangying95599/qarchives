package org.quetzaco.security.dms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quetzaco.archives.application.biz.Impl.FolderServiceImpl;
import org.quetzaco.archives.model.BaseModel;
import org.quetzaco.archives.model.Document;
import org.quetzaco.archives.model.Folder;
import org.quetzaco.archives.model.Cabinet;
import org.quetzaco.archives.model.Group;
import org.quetzaco.archives.model.Space;
import org.quetzaco.archives.model.User;
import org.quetzaco.security.exception.*;
import org.quetzaco.security.util.QuetzacoReadQuery;
import org.quetzaco.security.dms.constant.DMSConstants;
import org.quetzaco.security.dms.constant.DMSRightConstants;
import org.quetzaco.security.dms.constant.DMSSortableColumnConstants;
import org.quetzaco.security.dms.model.ACL;
import org.quetzaco.security.dms.model.Permission;
import org.quetzaco.security.dms.model.Right;
import org.quetzaco.security.dms.model.RightGroup;
import org.quetzaco.security.exception.InvalidModelException;
import org.quetzaco.security.exception.SecurityException;
import org.quetzaco.security.exception.InvalidModel.InvalidAssigneeException;
import org.quetzaco.security.exception.InvalidModel.InvalidRightException;
import org.quetzaco.security.exception.InvalidModel.InvalidRightGroupException;
import org.quetzaco.security.exception.InvalidModel.InvalidSecurityRuleSetException;
import org.quetzaco.security.exception.InvalidModel.InvalidTargetModelException;
import org.quetzaco.security.exception.invalidObjectType.InvalidAssigneeTypeException;
import org.quetzaco.security.exception.invalidObjectType.InvalidSecurityRuleSetTypeException;
import org.quetzaco.security.exception.invalidObjectType.InvalidTargetTypeException;
import org.quetzaco.security.model.SecurityRuleSet;
import org.quetzaco.security.model.SecurityRuleSetItem;
import org.quetzaco.security.util.DBHelper;
import org.quetzaco.security.util.SecurityReadQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DMSSecurityManager{
	final static Logger Logger = LoggerFactory.getLogger(DMSSecurityManager.class);

	private DMSSecurityManager (){
		initRightGroupMap();
		initRightMap();
		initDefaultSortableColumnMap();
		readQuery = SecurityReadQuery.getInstance();
	}

	private static DMSSecurityManager _instance = null;

	public static synchronized DMSSecurityManager getInstance(){
		if(_instance==null){
			_instance = new DMSSecurityManager();
		}
		return _instance;
	}

	private Map<String ,String> rightGroupMap = null;
	private Map<String ,String> rightMap = null;
	private Map<String ,String[][]> defaultSortableColumnsMap = null;
	private SecurityReadQuery readQuery = null;
	private void initRightGroupMap(){
		rightGroupMap = new HashMap<String, String>();
		rightGroupMap.put("userdefinded".toLowerCase(), "A4AAECB2-93C3-CA2C-2626-FB3735851B6C");
		rightGroupMap.put("noAccess".toLowerCase(), "B5C7AEC6-3ADC-588F-90C5-31CDF74CFBF4");
		rightGroupMap.put("viewCanCopy".toLowerCase(), "8DAB9F09-C395-7473-0C78-74F8038DEDF2");
		rightGroupMap.put("viewCanPrint".toLowerCase(), "01D87DE2-DF3E-8EBB-8054-A62717CEC390");
		rightGroupMap.put("visible".toLowerCase(), "A50E84A7-4D00-C71B-72D2-CFC6D7B7FB75");
		rightGroupMap.put("Download".toLowerCase(), "4151A22D-40E6-3CD8-73C9-B8DA52486A90");
		rightGroupMap.put("Upload".toLowerCase(), "831D41E6-C1F1-1D2D-1730-4D1C049D3C24");
		rightGroupMap.put("DownloadSrcDoc".toLowerCase(), "EA4F1730-F69A-20C8-EBFF-2724062668D5");
		rightGroupMap.put("fullControl".toLowerCase(), "8F130AE4-8DA7-D685-1B4C-E1F268FBCBB6");
		rightGroupMap.put("modify".toLowerCase(), "B85E645F-8AF8-A46D-387E-9D2458C76B59");
		rightGroupMap.put("view".toLowerCase(), "6FDDFD4C-9883-E60F-7C99-579A08275BB4");
	}

	private void initRightMap(){
		rightMap = new HashMap<String, String>();
		rightMap.put("canCopy".toLowerCase(),DMSRightConstants.CAN_COPY);
		rightMap.put("canPrint".toLowerCase(),DMSRightConstants.CAN_PRTINT);
		rightMap.put("checkInDocuments".toLowerCase(),DMSRightConstants.CHECKIN_DOCUMENTS);
		rightMap.put("checkOutDocuments".toLowerCase(),DMSRightConstants.CHECKOUT_DOCUMENTS);
		rightMap.put("copyDocuments".toLowerCase(),DMSRightConstants.COPY_DOCUMENTS);
		rightMap.put("copyFolders".toLowerCase(),DMSRightConstants.COPY_FOLDERS);
		rightMap.put("copyLinks".toLowerCase(),DMSRightConstants.COPY_LINKS);
		rightMap.put("createDocuments".toLowerCase(),DMSRightConstants.CREATE_DOCUMENTS);
		rightMap.put("createEmptyDocuments".toLowerCase(),DMSRightConstants.CREATE_EMPTYDOCUMENTS);
		rightMap.put("createFolders".toLowerCase(),DMSRightConstants.CREATE_FOLDERS);
		rightMap.put("createLinks".toLowerCase(),DMSRightConstants.CREATE_LINKS);
		rightMap.put("deleteCabinets".toLowerCase(),DMSRightConstants.DELETE_CABINETS);
		rightMap.put("deleteDocuments".toLowerCase(),DMSRightConstants.DELETE_DOCUMENTS);
		rightMap.put("deleteFolders".toLowerCase(),DMSRightConstants.DELETE_FOLDERS);
		rightMap.put("deleteLinks".toLowerCase(),DMSRightConstants.DELETE_LINKS);
		rightMap.put("editCabinets".toLowerCase(),DMSRightConstants.EDIT_CABINETS);
		rightMap.put("editFolders".toLowerCase(),DMSRightConstants.EDIT_FOLDERS);
		rightMap.put("editLinks".toLowerCase(),DMSRightConstants.EDIT_LINKS);
		rightMap.put("getCabinetDetails".toLowerCase(),DMSRightConstants.GET_CABINET_DETAILS);
		rightMap.put("getDocumentDetails".toLowerCase(),DMSRightConstants.GET_DOCUMENT_DETAILS);
		rightMap.put("getDocumentRevisions".toLowerCase(),DMSRightConstants.GET_DOCUMENT_REVISIONS);
		rightMap.put("getFolderDetails".toLowerCase(),DMSRightConstants.GET_FOLDER_DETAILS);
		rightMap.put("getFolderItems".toLowerCase(),DMSRightConstants.GET_FOLDER_ITEMS);
		rightMap.put("getItems".toLowerCase(),DMSRightConstants.GET_ITEMS);
		rightMap.put("getLinkDetails".toLowerCase(),DMSRightConstants.GET_LINK_DETAILS);
		rightMap.put("getSecurity".toLowerCase(),DMSRightConstants.GET_SECURITY);
		rightMap.put("lockDocuments".toLowerCase(),DMSRightConstants.LOCK_DOCUMENTS);
		rightMap.put("moveDocuments".toLowerCase(),DMSRightConstants.MOVE_DOCUMENTS);
		rightMap.put("moveFolders".toLowerCase(),DMSRightConstants.MOVE_FOLDERS);
		rightMap.put("moveLinks".toLowerCase(),DMSRightConstants.MOVE_LINKS);
		rightMap.put("setSecurity".toLowerCase(),DMSRightConstants.SET_SECURITY);
		rightMap.put("unlockDocuments".toLowerCase(),DMSRightConstants.UNLOCK_DOCUMENTS);
		rightMap.put("updateDocuments".toLowerCase(),DMSRightConstants.UPDATE_DOCUMENTS);
		rightMap.put("viewDocumentDetails".toLowerCase(),DMSRightConstants.VIEW_DOCUMEN_TDETAILS);
		rightMap.put("viewDocuments".toLowerCase(),DMSRightConstants.VIEW_DOCUMENTS);
		rightMap.put("showAccessLog".toLowerCase(),DMSRightConstants.SHOW_ACCESSLOG);
		rightMap.put("viewDiscussion".toLowerCase(),DMSRightConstants.VIEW_DISCUSSION);
		rightMap.put("createDiscussion".toLowerCase(),DMSRightConstants.CREATE_DISCUSSION);
		rightMap.put("checkinFolders".toLowerCase(),DMSRightConstants.CHECKIN_FOLDERS);
		rightMap.put("createCabinets".toLowerCase(),DMSRightConstants.CREATE_CABINET);
		/**
		 * no using
		 */
		rightMap.put("editDocuments".toLowerCase(),DMSRightConstants.UPDATE_DOCUMENTS);//FunctionConstants 	static String EDIT_DOCUMENT = "updateDocuments";
	}

	private void initDefaultSortableColumnMap(){
		defaultSortableColumnsMap = new HashMap<String, String[][]>();
		String[][] cabsortCols = {{DMSSortableColumnConstants.Cabinet_OrderNum,DMSConstants.ASCEND},{DMSSortableColumnConstants.Cabinet_Name,DMSConstants.ASCEND}};
		String[][] fldsortCols = {{DMSSortableColumnConstants.Folder_OrderNum,DMSConstants.ASCEND},{DMSSortableColumnConstants.Folder_Name,DMSConstants.ASCEND}};
		String[][] allsortCols ={{DMSSortableColumnConstants.OrderNum,DMSConstants.ASCEND},{DMSSortableColumnConstants.ObjectName,DMSConstants.ASCEND}};

		defaultSortableColumnsMap.put(DMSConstants.OBJ_TYPE_CABINET, cabsortCols);
		defaultSortableColumnsMap.put(DMSConstants.OBJ_TYPE_FOLDER, fldsortCols);
		defaultSortableColumnsMap.put(DMSConstants.OBJ_TYPE_ALL, allsortCols);
	}

	public String getrgtgrpIdByName(String rgtgrpName){
		return rightGroupMap.get(rgtgrpName.toLowerCase());
	}
	public String getRightIdByName(String rightName){
		return rightMap.get(rightName.toLowerCase());
	}


	public static void main(String[] args) {
	}

	/**
	 * 取消dms对象和对象下子节点的继承状态
	 * @param targetModel 对象为设置继承的dms根节点，类型一般为fld 或者 cab;
	 * 					targetModel 必须设置ObjectId 属性。
	 * @throws InvalidTargetModelException
	 * 			传入取消继承的对象为空或者其ObjectId不存在
	 * @throws SQLException
	 * 			数据库执行过程中发生错误
	 * @throws SecurityException
	 * 			其他异常
	 * @throws DMSException
	 * 			关闭数据库链接失败 -ErrorCode:1151
	 */
	public void cancelInheritance(BaseModel targetModel)
			throws InvalidTargetModelException ,SQLException,SecurityException,DMSException{
		if(targetModel==null||targetModel.getId()==null)
			throw new InvalidTargetModelException();
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		try {
			connection = DBHelper.getConnection();
			preparedStmt = connection.prepareStatement(QuetzacoReadQuery.instance().getQuetzacoSecurityQueryValue("strCancelInheritance"));
			preparedStmt.setString(1, targetModel.getId());
			Logger.debug("开始执行cancelInheritance..............");
			preparedStmt.execute();
			Logger.debug("cancelInheritance执行完成..............");

		} finally {
			DBHelper.cleanup(connection, preparedStmt);
		}
	}

}