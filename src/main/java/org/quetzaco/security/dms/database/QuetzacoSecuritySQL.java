package org.quetzaco.security.dms.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuetzacoSecuritySQL {
	final static Logger logger = LoggerFactory.getLogger(QuetzacoSecuritySQL.class);
	public static String strCancelInheritance = "select dms_cancelInheritance(?)";

	public static String strTransferAcl = "select dms_transferacl(?,?)";

	public static String strGetPermissions = "select assignee_guid, first_name, last_name ,  assginee_type, rgtgrp_guid,  rgtgrp_name,  expired_guid,  expired_date   from  dms_getaclwithexpiredate(?,?,?)  as (assignee_guid VARCHAR,first_name VARCHAR,last_name VARCHAR,assginee_type varchar ,rgtgrp_guid VARCHAR, rgtgrp_name VARCHAR,expired_guid VARCHAR ,expired_date timestamp  ) ";

	public static String strHasRight = "select dms_hasright(?,?,?,?,?,?)";

	public static String strSetDefaultSecurityRuleset = "select dms_setaclbydefaultsecruleset(?, ?, ?, ?)";

	public static String strSetPredefinedRuleset = "select dms_setaclbysecruleset(?, ?, ?)";

	public static String strInheritFrom = "select dms_inherit(?)";

	public static String strDeleteAclWithSubitems = "select dms_deleteaclwithsubitems(?,?)";

	public static String strHasRightForTree = "select dms_hasrightwithitmes(?,?,?,?,?,?)";

	public static String strRestoreAclFromRecyclebin = "select dms_restoreaclfromrecyclebin(?,?)";

	public static String strCreateSecurityRuleset = "insert into security_ruleset values (?,?,?,?,?,?)";

	public static String strUpdateSecurityRuleSet = "UPDATE security_ruleset set name = ?,type = ?,obj_type = ?, space_guid = ?,usr_guid = ? where sec_rs_guid = ?";

	public static String strDelSecurityRuleset = "delete from security_ruleset where sec_rs_guid = ?";

	public static String strInsertACLSQL = "INSERT into dms_acl(obj_guid,assignee_guid,assignee_type,expired_guid,rgtgrp_guid,udrg_guid) values (?,?,?,?,?,?)";

	public static String strInsertACLSettingSQL = "INSERT INTO dms_acl_settings(obj_guid,inherit_flag,type) VALUES (?,?,?)";

	public static String strInsertACLNoaccess = "insert into dms_acl_noaccess (obj_guid,assignee_guid,assignee_type,expired_guid) VALUES (?,?,?,?)";

	public static String strInsertUserDefinedRightSQL = "INSERT into dms_userdefined_rights (udrg_guid,rgt_id) VALUES (?,?)";

	public static String strGetDefaultSecurityRulesetList = "select sec_rs_guid,name,type,obj_type,space_guid,usr_guid from security_ruleset where usr_guid = ? and space_guid = ? and type = 'SY'";

	public static String strGetPredefinedSecurityRulesetList = "select sec_rs_guid,name,type,obj_type,space_guid,usr_guid from security_ruleset where usr_guid = ? and space_guid = ? and type = 'UD'";

	public static String strInsertSecurityRuleSetItems = "insert into security_ruleset_items VALUES (?,?,?,?)";

	public static String strDeleteSecurityRuleSetItems = "delete from security_ruleset_items where sec_rs_guid = ?";

	public static String strGetAclSetting = "select obj_guid,type,inherit_flag from dms_getaclsetting(?)";

	public static String strGetItemByRulesetId = "select b.sec_rs_guid,b.assignee_guid,b.assignee_type,u.first_name,u.last_name,grp.name,b.rgtgrp_guid from (security_ruleset_items b inner join users u on assignee_guid = u.usr_guid) inner join dms_rightgroups grp on b.rgtgrp_guid = grp.rgtgrp_guid where b.sec_rs_guid = ? UNION ALL select b.sec_rs_guid,b.assignee_guid,b.assignee_type,u.name,u.last_name,grp.name,b.rgtgrp_guid from (security_ruleset_items b inner join (select g.*,o.name as last_name  from groups g,organizations o where g.ref_guid=o.org_guid ) as  u on b.assignee_guid = u.grp_guid) inner join dms_rightgroups grp on b.rgtgrp_guid = grp.rgtgrp_guid where b.sec_rs_guid = ? ";

	public static String strGetObjectOwnerById = "select usr_guid,'User',first_name,last_name from ((select dms_getownerguid(?,?))x inner join users on dms_getownerguid = users.usr_guid)a union  select grp_guid,'Group',name,null from ((select dms_getownerguid(?,?))x inner join groups on dms_getownerguid = groups.grp_guid)a";

	public static String strDeleteAclByObjectId = "select dms_deleteAllObjectAcl(?)";

	public static String strInsertExpireddate = "insert into dms_permission_expireddate values (?,?,?)";

	public static String strGetDefaultSecurityRuleset = "select sec_rs_guid,name,type,obj_type,space_guid,usr_guid from security_ruleset where usr_guid = ? and space_guid = ? and type = 'SY' and obj_type = ?";

	public static String strCheckInheritanceChangedGetDefaultSecurityRuleset = "select  inherit_flag from dms_acl_settings where obj_guid = ? ";

	public static String strGetSpaceId = "select getspace_id(?,?)";

	public static String strInsertDmsInherit = "insert into dms_acl_inherit values (?,?)";

	public static String strCheckRuleSetNameexisted = "select exists (select 1 from security_ruleset where name = ? )";

	public static String strGetSecurityRuleSetById = "select sec_rs_guid,name,type,obj_type,space_guid,usr_guid from security_ruleset where sec_rs_guid = ?";

	public static String strCreateDefaultDMSRuleSet = "select sec_rs_guid,name,type,obj_type,space_guid,usr_guid from security_ruleset where sec_rs_guid = ?";

	public static String strCheckDefaultRuleSet = "select exists ( select 1 from security_ruleset where space_guid = ? and usr_guid = ? )";

	public static String strSelectDefaultRuleSet = " select name,value from ref_rulesetvalue";

	public static String strRuleSetSql = "insert into security_ruleset values (?,?,?,?,?,?)";

	public static String strDeletePermission = "delete from dms_acl where obj_guid = ? and assignee_guid = ?";

	public static String strDeletePermissionForNoAccess = "delete from dms_acl_noaccess where obj_guid = ? and assignee_guid = ?";

	public static String strInsertPermission = "INSERT into dms_acl(obj_guid,assignee_guid,assignee_type,rgtgrp_guid) VALUES (?,?,?,?)";

	public static String strInsertPermissionForNoAccess = "INSERT into dms_acl_noaccess(obj_guid,assignee_guid,assignee_type) VALUES (?,?,?)";

	public static String strRecycleBinSql = "SELECT obj_guid ,obj_type from recycle_bins where usr_guid = ? and obj_type in ('Document','Folder','Link','Cabinet') and record_flag = '01'";

	public static String strListCabsql = "SELECT  a.CAB_GUID,a.NAME, a.REP_GUID,  a.MOD_GUID,  a.DESCRIPTION,  a.CREATED_BY, a.CREATED_DT,  a.UPDATED_BY,  a.UPDATED_DT, "
			+ "b.first_name||' '||b.last_name as author,  a.cab_type,  '0' as update_flag "
			+ "FROM  dms_listcabinets(?,?) a  inner join users b  on  b.usr_guid = a.created_by  where  a.record_flag='01' order by";


	public static String strGetInheritRootGuidForfolder = "select dms_getprtguid_forfolder(?)";

	public static String strInheritFlag = "select obj_guid,type,inherit_flag from dms_getaclsetting(?)";

	public static String strDeleteCabOrFld = "select dms_deleteCabOrFld(?,?,?,?)";

	public static String strDeleteAclForAnonymousUser = "delete from  dms_acl where obj_guid = ? and assignee_guid = '1' and assignee_type = 'usr'";

	public static String strInsertAclForSinglePermission = "insert into dms_acl values(?,?,?,?,?,?)";

	public static String strUpdateAclByRightGroup = "update dms_acl set rgtgrp_guid = ? where obj_guid = ? and  rgtgrp_guid = ?  ; ";

	public static String strDeleteAclByRightGroup = "DELETE FROM  dms_acl where obj_guid = ? and  rgtgrp_guid = ?  ; ";

	public static String strDeleteAclByAssignee = "DELETE FROM  dms_acl where obj_guid = ? and  assignee_guid = ?  ; ";
	public static String strGetFolders = "select a.fld_guid, a.name, coalesce(a.updated_dt,a.created_dt) as updated_dt, "
			+ "b.first_name||' '||b.last_name as author, '0' "
			+ " as update_flag , a.description ,a.cab_guid, (SELECT name from folders where fld_guid = a.prt_guid LIMIT 1 ) as prt_name, "
			+ "a.prt_guid as prt_fld_guid from dms_listfolders(?,?) a inner join users b on a.created_by = b.usr_guid "
			+ "where a.record_flag='01' order by ";

	public static String strGetFolders4Offical = "select a.fld_guid, a.name, coalesce(a.updated_dt,a.created_dt) as updated_dt, "
			+ "b.first_name||' '||b.last_name as author,'0' "
			+ "as update_flag , a.description ,a.cab_guid, (SELECT name from folders where fld_guid = a.prt_guid LIMIT 1 ) as prt_name, "
			+ "a.prt_guid as prt_fld_guid from dms_listfolders(?,?) a inner join users b on a.created_by = b.usr_guid "
			+ "where a.record_flag='01' and a.showinhomepage='1' order by ordernum ";

	public static String strGetDocumentsInFolderForList = "select "
			+ "doc.doc_guid, "
			+ "doc.name, "
			+ "usr.first_name, "
			+ "usr.last_name, "
			+ "doc.updated_dt, "
			+ "doc.lock_status, "
			+ "doc.current_rev, "
			+ "doc_rev.file_name,  "
			+ "doc_rev.\"size\", "
			+ "doc_rev.extension, "
			+ "case "
			+ "when "
			+ "(select count(ref_guid) from discussions where ref_guid = 	doc.doc_guid and record_flag ='01')>0 "
			+ "then 1 "
			+ "else -1 "
			+ "end as dis_indicator , "
			+ "doc.msg_flag , "
			+ "doc.created_by, "
			+ "case "
			+ "when "
			+ "(select count(doc_guid) "
			+ "from doc_updates "
			+ "where usr_guid=? "
			+ "and doc_guid=doc.doc_guid )>0 "
			+ "then '1' "
			+ "else '0' "
			+ "end as update_flag, "
			+ "doc.doc_num, "
			+ "doc.ordernum, "
			+ "doc.ref_doc_flag, "
			+ "doc_rev.revision_num, "
			+ "doc.doc_type, "
			+ "f.name as prt_name, "
			+ "f.fld_guid as prt_fld_guid "
			+ "from (dms_listdocuments(?,?) doc inner join users usr on doc.created_by = usr.usr_guid ) "
			+ " inner join folders f on f.fld_guid = doc.fld_guid  "
			+ " left join doc_revisons doc_rev on doc.current_rev = doc_rev.rev_GUID "
			+ "where doc.record_flag = '01' and (doc_rev.record_flag='01' or doc_rev.record_flag is null) and f.record_flag = '01' and doc.current_rev is not null  order by doc.ordernum,doc.name ";

	public static String strGetLinksInFolderForList = "select lnk.link_guid, lnk.name, usr.first_name, usr.last_name, "
			+ "lnk.updated_dt  from  dms_listlinks(?,?) lnk  inner join users usr on lnk.created_by = usr.usr_guid "
			+ "where lnk.record_flag='01' and usr.record_flag='01'";

	public static String strGetDocumentsInFolderByRight = "select doc.doc_guid, doc.name, usr.first_name, usr.last_name, "
			+ "doc.updated_dt, doc.lock_status, doc.current_rev, doc_rev.file_name,  doc_rev.\"size\", doc_rev.extension, case "
			+ "when (select count(ref_guid) from discussions where ref_guid = 	doc.doc_guid and record_flag ='01')>0 then 1 else -1 "
			+ "end as dis_indicator , doc.msg_flag , doc.created_by, case when (select count(doc_guid) from doc_updates "
			+ "where usr_guid=? and doc_guid=doc.doc_guid )>0 then '1' else '0' end as update_flag, doc.doc_num, doc.ordernum, "
			+ "doc.ref_doc_flag, doc_rev.revision_num, doc.doc_type, f.name as prt_name, f.fld_guid as prt_fld_guid, "
			+ "doc_rev.int_filename, doc_rev.\"path\" as docpath, par.\"path\" as parpath "
			+ "from (dms_listdocumentsbyRight(?,?,?) doc inner join users usr on doc.created_by = usr.usr_guid ) "
			+ " inner join folders f on f.fld_guid = doc.fld_guid   left join doc_revisons doc_rev on doc.current_rev = doc_rev.rev_GUID "
			+ " left join \"partitions\" par on doc_rev.par_guid = par.par_guid "
			+ "where doc.record_flag = '01' and doc_rev.record_flag='01' and f.record_flag = '01' and doc.current_rev is not null and par.record_flag = '01' ";

	public static String strGetEmptyDocumentsInFolderByRight = "select doc.doc_guid, doc.name, first_name, last_name, "
			+ "doc.updated_dt, doc.lock_status , doc.msg_flag ,doc.created_by,doc.ref_doc_flag,doc.doc_num, f.name as prt_name, "
			+ "f.fld_guid as prt_fld_guid from (dms_listdocumentsbyRight(?,?,?) doc inner join users usr on doc.created_by = usr.usr_guid ) "
			+ " inner join folders f on f.fld_guid = doc.fld_guid  where doc.record_flag = '01' and doc.current_rev is  null";

	public static String strGetLinksInFolderByRight = "select lnk.link_guid, lnk.name, usr.first_name, usr.last_name, "
			+ "lnk.updated_dt  from  dms_listlinksbyRight(?,?,?) lnk  inner join users usr on lnk.created_by = usr.usr_guid "
			+ "where lnk.record_flag='01' and usr.record_flag='01'";

	public static String strGetFoldersByRight = "select a.fld_guid, a.name, coalesce(a.updated_dt,a.created_dt) as updated_dt, "
			+ "b.first_name||' '||b.last_name as author,  '0'  as update_flag , a.description ,"
			+ "a.cab_guid, (SELECT name from folders where fld_guid = a.prt_guid LIMIT 1 ) as prt_name, a.prt_guid as prt_fld_guid "
			+ "from dms_listfoldersbyRight(?,?,?) a inner join users b on a.created_by = b.usr_guid where a.record_flag='01' ";

	public static String strCheckRightsForSearchResultByRightGroup = "select obj_guid from dms_searchByRightgroup(?,?) AS (obj_guid varchar)";
	
	public static String strCheckRightsForSearchResultByRight = "select obj_guid from dms_searchByRight(?,?,?) AS (obj_guid varchar)";
	
	public static String strCheckAnonymousRightsForSearchResult = "select distinct x.obj_guid from search_result x inner join dms_acl y on x.obj_guid = y.obj_guid and y.assignee_guid = '1'";
	
	public static String strSetSearchResutTempData = "insert into search_result values (?,?)";
	
	public static String strDeleteSearchResutTempData = "delete from search_result where usr_guid = ?;";
	
	public static String strHasPRTVisiableRight = "select dms_checkprt_listitems_right(?,?)";
	
	public static String strGetPrtModel = "select * from  dms_getprtmodel(?) as (prt_guid varchar, prt_type varchar) ";
	
	public static String strSetaclbybasemodel = "select dms_setaclbybasemodel(?,?) ";
	
	public static String strGetDocId = "select getdoc_id(?)";
	
	public static String strHasRightGroup = "select dms_hasrightgroup(?,?,?,?)";

	public static String getFolderItemsForList(boolean isCheckRights,String[][] sortableColumns) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT obj_guid  , obj_type  , obj_name  , created_by_f_name ,");
		sb.append(" created_by_l_name,updated_dt , lock_status  , doc_current_rev  , doc_file_name  ,");
		sb.append(" obj_size  ,doc_extension  , dis_indicator  , msg_flag  , created_by_guid  , update_flag ,");
		sb.append(" doc_num  , ordernum  , ref_doc_flag  , revision_num , doc_type , prt_name  ,");
		sb.append(" prt_fld_guid  , description  , cab_guid,updated_by_f_name,updated_by_l_name,created_dt ,tags_guid from ");
		sb.append(isCheckRights ? " dms_getfolderitems(?, ?)" : " dms_getfolderitems(?, ?,false)");
		sb.append(" as (obj_guid VARCHAR , obj_type text , obj_name VARCHAR , created_by_f_name VARCHAR, created_by_l_name VARCHAR ,");
		sb.append(" updated_dt TIMESTAMP , lock_status numeric , doc_current_rev VARCHAR , doc_file_name VARCHAR , obj_size NUMERIC ,");
		sb.append(" doc_extension VARCHAR , dis_indicator INTEGER , msg_flag VARCHAR , created_by_guid VARCHAR , update_flag INTEGER,");
		sb.append(" doc_num VARCHAR , ordernum numeric , ref_doc_flag VARCHAR , revision_num VARCHAR, doc_type VARCHAR, prt_name VARCHAR ,");
		sb.append(" prt_fld_guid VARCHAR , description VARCHAR , cab_guid VARCHAR,updated_by_f_name VARCHAR,updated_by_l_name VARCHAR,created_dt TIMESTAMP" + ",tags_guid VARCHAR) ");
		if(sortableColumns !=null){
			logger.debug("--------------------" + sortableColumns[0][0] + "-------------" + sortableColumns[0][1]);
			if(sortableColumns[0][1]!=null)
				sortableColumns[0][1]=	sortableColumns[0][1].replace("END","");
			sb.append(" order by obj_type , "+switchTrueName(sortableColumns[0][0])+ " "+ (sortableColumns[0][1] == null ? "ASC": sortableColumns[0][1]));
		}
		return sb.toString();
	}

	private static String switchTrueName(String s) {
		if("displayName".equalsIgnoreCase(s)){
			return "convert_to(obj_name,'GBK') ";
		}else if("modifieddate".equalsIgnoreCase(s)){
			return "updated_dt ";
		}
		else if("createdBy.name".equalsIgnoreCase(s)){
			return "convert_to(created_by_f_name,'GBK') ";
		} else if("currentRevision.showSize".equalsIgnoreCase(s)){
			return "obj_size ";
		}

		return s;
	}
	public static String getFolderPagingItemsForList(boolean isCheckRights) {
		String docsql = "SELECT obj_guid  , obj_type   from dms_getfolderpagingitems(?, ?)as (obj_guid VARCHAR , obj_type text ) ";
		if (!isCheckRights) {
			docsql = "SELECT obj_guid  , obj_type   from dms_getfolderpagingitems(?, ?,false)as (obj_guid VARCHAR , obj_type text  ) ";
		}
		return docsql;
	}

    public static String strChangeInheritSecurityForMove = "select dms_change_inherit_security_for_move(?,?)";

	public static String strDeleteAclForSinglePermission ="select deleteAclForSinglePermission(?,?)";

}
