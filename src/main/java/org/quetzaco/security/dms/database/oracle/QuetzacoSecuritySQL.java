package org.quetzaco.security.dms.database.oracle;


public class QuetzacoSecuritySQL  extends  org.quetzaco.security.dms.database.QuetzacoSecuritySQL{

    public static String strGetSpaceId = "select getspace_id(?,?) from dual";

    public static String strCancelInheritance = "call dms_cancelInheritance(?)";

    public static String strTransferAcl = "call dms_transferacl(?,?)";

    public static String strHasRight = "select dms_hasright(?,?,?,?,?,?) from dual";

    public static String strSetDefaultSecurityRuleset = "call dms_setaclbydefaultsecruleset(?, ?, ?, ?)";

    public static String strSetPredefinedRuleset = "call dms_setaclbysecruleset(?, ?, ?)";

    public static String strInheritFrom = "call dms_inherit(?)";

    public static String strDeleteAclWithSubitems = "select dms_deleteaclwithsubitems(?,?) from dual";

    public static String strHasRightForTree = "select dms_hasrightwithitmes(?,?,?,?,?,?) from dual";

    public static String strRestoreAclFromRecyclebin = "call dms_restoreaclfromrecyclebin(?,?)";

    public static String strGetAclSetting = "select obj_guid,type,inherit_flag from  table(dms_getaclsetting(?))";

    public static String strGetObjectOwnerById = "select usr_guid,'User',first_name,last_name from ((select dms_getownerguid(?,?) owner_guid  from dual) x inner join users on x.owner_guid = users.usr_guid)  union  select grp_guid,'Group',name,null from ((select dms_getownerguid(?,?)  owner_guid from dual) x inner join groups on x.owner_guid = groups.grp_guid)";

    public static String strDeleteAclByObjectId = "call dms_deleteAllObjectAcl(?)";

    public static String strCheckRuleSetNameexisted = "select sign(count(1)) from security_ruleset where name = ? ";

    public static String strCheckDefaultRuleSet = "select sign(count(1)) from security_ruleset where space_guid = ? and usr_guid = ? ";

    public static String strListCabsql = "SELECT  a.CAB_GUID,a.NAME, a.REP_GUID,  a.MOD_GUID,  a.DESCRIPTION,  a.CREATED_BY, a.CREATED_DT,  a.UPDATED_BY,  a.UPDATED_DT, "
            + "b.first_name||' '||b.last_name as author,  a.cab_type,  case  when   dms_check_cabinet_flag(?,a.CAB_GUID) = 'T' then '1'  else '0'  end as update_flag "
            + "FROM  table(dms_listcabinets(?,?)) a  inner join users b  on  b.usr_guid = a.created_by  where  a.record_flag='01' order by ordernum  , name ";

    public static String strInheritFlag = "select obj_guid,type,inherit_flag from table(dms_getaclsetting(?))";

    public static String strGetFolders = "select a.fld_guid, a.name, coalesce(a.updated_dt,a.created_dt) as updated_dt, "
            + "b.first_name||' '||b.last_name as author, case when (select dms_check_folder_flag(?,a.fld_guid) from dual) = 'T' then '1' else '0' "
            + "end as update_flag , a.description ,a.cab_guid, (SELECT name from folders where fld_guid = a.prt_guid and rownum < 2 ) as prt_name, "
            + "a.prt_guid as prt_fld_guid from table(dms_listfolders(?,?)) a inner join users b on a.created_by = b.usr_guid "
            + "where a.record_flag='01' order by ordernum ,name";
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
            + "from ( table(dms_listdocuments(?,?)) doc inner join users usr on doc.created_by = usr.usr_guid ) "
            + " inner join folders f on f.fld_guid = doc.fld_guid  "
            + " left join doc_revisons doc_rev on doc.current_rev = doc_rev.rev_GUID "
            + "where doc.record_flag = '01' and (doc_rev.record_flag='01' or doc_rev.record_flag is null) and f.record_flag = '01' and doc.current_rev is not null  order by doc.ordernum ";

    public static String strGetLinksInFolderForList = "select lnk.link_guid, lnk.name, usr.first_name, usr.last_name, "
            + "lnk.updated_dt  from   table(dms_listlinks(?,?)) lnk  inner join users usr on lnk.created_by = usr.usr_guid "
            + "where lnk.record_flag='01' and usr.record_flag='01'";

    public static String strGetDocumentsInFolderByRight = "select doc.doc_guid, doc.name, usr.first_name, usr.last_name, "
            + "doc.updated_dt, doc.lock_status, doc.current_rev, doc_rev.file_name,  doc_rev.\"size\", doc_rev.extension, case "
            + "when (select count(ref_guid) from discussions where ref_guid = 	doc.doc_guid and record_flag ='01')>0 then 1 else -1 "
            + "end as dis_indicator , doc.msg_flag , doc.created_by, case when (select count(doc_guid) from doc_updates "
            + "where usr_guid=? and doc_guid=doc.doc_guid )>0 then '1' else '0' end as update_flag, doc.doc_num, doc.ordernum, "
            + "doc.ref_doc_flag, doc_rev.revision_num, doc.doc_type, f.name as prt_name, f.fld_guid as prt_fld_guid, "
            + "doc_rev.int_filename, doc_rev.path as docpath, par.path as parpath "
            + "from ( table(dms_listdocumentsbyRight(?,?,?)) doc inner join users usr on doc.created_by = usr.usr_guid ) "
            + " inner join folders f on f.fld_guid = doc.fld_guid   left join doc_revisons doc_rev on doc.current_rev = doc_rev.rev_GUID "
            + " left join partitions par on doc_rev.par_guid = par.par_guid "
            + "where doc.record_flag = '01' and doc_rev.record_flag='01' and f.record_flag = '01' and doc.current_rev is not null and par.record_flag = '01' ";

    public static String strGetEmptyDocumentsInFolderByRight = "select doc.doc_guid, doc.name, first_name, last_name, "
            + "doc.updated_dt, doc.lock_status , doc.msg_flag ,doc.created_by,doc.ref_doc_flag,doc.doc_num, f.name as prt_name, "
            + "f.fld_guid as prt_fld_guid from ( table(dms_listdocumentsbyRight(?,?,?)) doc inner join users usr on doc.created_by = usr.usr_guid ) "
            + " inner join folders f on f.fld_guid = doc.fld_guid  where doc.record_flag = '01' and doc.current_rev is  null";

    public static String strGetLinksInFolderByRight = "select lnk.link_guid, lnk.name, usr.first_name, usr.last_name, "
            + "lnk.updated_dt  from  (table(dms_listlinksbyRight(?,?,?))） lnk  inner join users usr on lnk.created_by = usr.usr_guid "
            + "where lnk.record_flag='01' and usr.record_flag='01'";

    public static String strGetFoldersByRight = "select a.fld_guid, a.name, coalesce(a.updated_dt,a.created_dt) as updated_dt, "
            + "b.first_name||' '||b.last_name as author,  '0'  as update_flag , a.description ,"
            + "a.cab_guid, (SELECT name from folders where fld_guid = a.prt_guid and rownum < 2 ) as prt_name, a.prt_guid as prt_fld_guid "
            + "from  table(dms_listfoldersbyRight(?,?,?)) a inner join users b on a.created_by = b.usr_guid where a.record_flag='01' ";

    public static String strCheckRightsForSearchResultByRightGroup = "select obj_guid from table(dms_searchByRightgroup(?,?))";

    public static String strCheckRightsForSearchResultByRight = "select obj_guid from table(dms_searchByRight(?,?,?))";

    public static String strHasPRTVisiableRight = "select dms_checkprt_listitems_right(?,?) from dual";

    public static String strGetPrtModel = "select * from  table(dms_getprtmodel(?)) ";

    public static String strSetaclbybasemodel = "call dms_setaclbybasemodel(?,?) ";

    public static String strGetDocId = "select getdoc_id(?) from dual";

    public static String strHasRightGroup = "select dms_hasrightgroup(?,?,?,?) from dual";

    public static String strChangeInheritSecurityForMove = "call dms_change_inherit_secu_for_mv(?,?) ";

    public static String strDeleteAclForSinglePermission ="call deleteAclForSinglePermission(?,?) ";

    public static String getFolderItemsForList(boolean isCheckRights) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT obj_guid  , obj_type  , obj_name  , created_by_f_name ,");
        sb.append(" created_by_l_name,updated_dt , lock_status  , doc_current_rev  , doc_file_name  ,");
        sb.append(" obj_size  ,doc_extension  , dis_indicator  , msg_flag  , created_by_guid  , update_flag ,");
        sb.append(" doc_num  , ordernum  , ref_doc_flag  , revision_num , doc_type , prt_name  ,");
        sb.append(" prt_fld_guid  , description  , cab_guid,updated_by_f_name,updated_by_l_name,created_dt from ");
        sb.append(isCheckRights ? " table(dms_getfolderitems(?, ?))" : " table(dms_getfolderitems1(?, ?,'F'))");
        return sb.toString();
    }

    public static String strGetPermissions = "select assignee_guid, first_name, last_name ,  assignee_type, rgtgrp_guid,  rgtgrp_name,  expired_guid,  expired_date from table(dms_getaclwithexpiredate(?,?,?))";
    public static String strGetItemByRulesetId = "select b.sec_rs_guid,b.assignee_guid,b.assignee_type,u.first_name,u.last_name,grp.name,b.rgtgrp_guid from (security_ruleset_items b inner join users u on assignee_guid = u.usr_guid) inner join dms_rightgroups grp on b.rgtgrp_guid = grp.rgtgrp_guid where b.sec_rs_guid = ? UNION ALL select b.sec_rs_guid,b.assignee_guid,b.assignee_type,u.name,u.last_name,grp.name,b.rgtgrp_guid from (security_ruleset_items b inner join (select g.*,o.name as last_name  from groups g,organizations o where g.ref_guid=o.org_guid )   u on b.assignee_guid = u.grp_guid) inner join dms_rightgroups grp on b.rgtgrp_guid = grp.rgtgrp_guid where b.sec_rs_guid = ? ";
    public static String strDeleteSearchResutTempData = "delete from search_result where usr_guid = ?";

}
