package org.quetzaco.archives.application.biz.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.quetzaco.archives.application.biz.DocumentService;
import org.quetzaco.archives.application.biz.RecordService;
import org.quetzaco.archives.application.biz.SwiftService;
import org.quetzaco.archives.application.biz.UserService;
import org.quetzaco.archives.application.dao.*;
import org.quetzaco.archives.model.*;
import org.quetzaco.archives.util.boot.ArchiveType;
import org.quetzaco.archives.util.boot.RoleType;
import org.quetzaco.archives.util.config.ArchiveProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by deya on 2017/7/14.
 */
@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    RecordMapper recordMapper;
    @Autowired
    DocumentsMapper documentMapper;
    @Autowired
    SwiftService swiftService;
    @Autowired
    AcousticImageMapper acousticImageMapper;
    @Autowired
    DeptMapper deptMapper;
    @Autowired
    FileMapper fileMapper;
    @Autowired
    DocumentService documentService;
    @Autowired
    ArchiveProperties archiveProperties;
    @Autowired
    UserService userService;
    @Autowired
    FlowFormDeliverMapper flowFormDeliverMapper;
    @Value("${kmhk.deptId}")
    Long KMHK_DEPTID;


    @Autowired
    @Qualifier("archivesJdbcTemplate")
    protected JdbcTemplate jdbcTemplate;

    final static org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RecordServiceImpl.class);

	  protected static Map<String,String> archiveTypeMap_jian = new HashMap<String,String>();
	  protected static Map<String,String> archiveTypeMap_juan = new HashMap<String,String>();
	  static {
	  	//documents
//	      archiveTypeMap_jian.put("ALL","全部");
	      archiveTypeMap_jian.put("WS","文书档案");
	      archiveTypeMap_jian.put("KJ","会计档案");
	      archiveTypeMap_jian.put("RS","人事档案");
	      archiveTypeMap_jian.put("SW","实物档案");
	      archiveTypeMap_jian.put("HT","合同档案");
	      archiveTypeMap_jian.put("KW","刊物档案");
	      archiveTypeMap_jian.put("YX","印信档案");
	      archiveTypeMap_jian.put("FC","房产档案");
	      archiveTypeMap_jian.put("ZJ","证件档案");
	      archiveTypeMap_jian.put("DJ","单机档案");

	      //record
//	      archiveTypeMap_juan.put("ALL","全部");
	      archiveTypeMap_juan.put("JJ","基建档案");
	      archiveTypeMap_juan.put("SX","声像档案");
	      archiveTypeMap_juan.put("SJJC","审计监察档案");
	      archiveTypeMap_juan.put("AJ","案件档案");
	      archiveTypeMap_juan.put("SG","生产安全事故档案");
	      archiveTypeMap_juan.put("XX","信息化建设档案");
	      archiveTypeMap_juan.put("ZTB","招投标档案");
	  }

    @Override
    public PageInfo selectRecords(Long deptId,int offset, int limit,User user) {
        PageHelper.startPage(offset, limit);
        List<Record> list = recordMapper.selectRecords(deptId);
        //用PageInfo对结果进行包装
        PageInfo page = new PageInfo(list);
        return page;
    }

    @Override
    public synchronized void createRecord(Record record) {
        String fileNum = getFileNum(record);
        record.setFileNum(fileNum);
        recordMapper.insertSelective(record);
        // recordMapper.createRecord(record);
    }

    private String getFileNum(Record record) {
        String fondsId = record.getFondsId();
        String archiveType = record.getArchiveType();
        String prefix;
        if ("JJ".equals(archiveType)) {
            String itemNum = record.getItemNum();
            prefix = fondsId + "-" + archiveType + "-" + itemNum;
        } else {
            String archiveYear = record.getArchiveYear();
            String reserveDuration = record.getReserveDuration();
            prefix = fondsId + "-" + archiveType + "-" + archiveYear + "-" + reserveDuration;
        }
        swiftService.getSwiftNumber(prefix, "juanji");
        int num = swiftService.saveSwiftNumber(prefix);
        return prefix + "-" + new DecimalFormat("000").format(num);
    }

    @Override
    public Record selectRecordDetial(Long recordId) {
        return recordMapper.selectRecordDetial(recordId);
    }

    @Override
    public List<Documents> selectDocumentFromRecord(Long recordId) {
        return recordMapper.selectDocumentFromRecord(recordId);
    }

    @Override
    public List<String> insertDocumentToRecord(Long recordId, List<Documents> docs, int type,User contextUser) {
        List<String> dupTitles = new ArrayList<String>();
        List<Long> docIds = new ArrayList<>();

        if (docs.size() > 1) Collections.sort(docs);
        for (int i = 0; i < docs.size(); i++) {
            LOGGER.debug("creattime  :    " + docs.get(i).getDocumentCreatime());
            Documents document = docs.get(i);
            Documents dupDoc = recordMapper.isDuplicateTitle(recordId, document.getFileName());

            if (dupDoc == null) {
                Long docId = document.getId();
                // Document document1 = documentMapper.selectDocumentDetail(docId);
                //  Record record = recordMapper.selectRecordDetial(recordId);
                recordMapper.insertDocumentToRecord(recordId, docId);
                String importance = type == 1 ? "重要" : "一般";
                Documents documents = new Documents();
                documents.setImportance(importance);
                documents.setArchiveType(document.getArchiveType());
                documents.setIsArchive(true);
                documents.setArrangeFlag(true);
                documents.setArchiveDate((new SimpleDateFormat("yyyy-MM-dd")).format(new Date()));
                documents.setId(docId);
                documents.setArrangedBy(contextUser.getId());//添加归档人
                documentMapper.updateByPrimaryKeySelective(documents);
                if ("SX".equals(document.getArchiveType())) {
                    AcousticImage acousticImage = new AcousticImage();
                    acousticImage.setId(new Integer(document.getId().intValue()));
                    acousticImageMapper.insertSelective(acousticImage);
                }
                documentMapper.updateArrangedBy(docId, contextUser.getId());
                docIds.add(docId);
            } else {
                dupTitles.add(document.getFileName());
            }
        }
        if(docIds!=null&&docIds.size()>0){
            Record record = recordMapper.selectByPrimaryKey(recordId);

            DocumentsExample documentsExample = new DocumentsExample();
            documentsExample.createCriteria().andIdIn(docIds).andRecordFlagEqualTo("00");
            List<Documents> documentsList = documentMapper.selectByExample(documentsExample);
            List<String> docLocalId = new ArrayList<>();
            for(Documents documents :documentsList){
                docLocalId.add(documents.getDocumentLocalId());
            }
            FileExample fileExample = new FileExample();
            fileExample.createCriteria().andDocIdIn(docLocalId).andRecordFlagEqualTo(true);
            List<Files> filesList = fileMapper.selectByExample(fileExample);
            for(Files files:filesList){
                files.setArchiveYear(record.getArchiveYear());
                files.setArchiveType(record.getArchiveType());
                files.setArrangedBy(contextUser.getId());

                documentService.indexFileToES(files, archiveProperties.getFileStorage() + "/" + files.getLocation());
            }
        }

        return dupTitles;
    }


    @Override
    public PageInfo selectRecordFromArchive(Long archiveId,int offset, int limit) {
        PageHelper.startPage(offset, limit);
        List<Record> list = recordMapper.selectRecordFromArchive(archiveId);
        //用PageInfo对结果进行包装
        PageInfo page = new PageInfo(list);
        return page;
    }


    @Override
    public PageInfo searchRecordList(Record record, int offset, int limit) {
        PageHelper.startPage(offset,limit);
        List<Record> list = recordMapper.searchRecordList(record);
        PageInfo page = new PageInfo(list);
        return page;
    }

    private boolean isOnlyDoc(String docAttr,String archiveType){
	      boolean isOnlyDoc = false;
        if(!StringUtils.isEmpty(docAttr)){
            isOnlyDoc = true;
            return isOnlyDoc;
        }
        if(!("".equals(archiveType) || "ALL".equals(archiveType)) && ArchiveType.DocumentType.isDocumentType(archiveType)){
            isOnlyDoc = true;
            return  isOnlyDoc;
        }
        return isOnlyDoc;
    }

    private boolean isOnlyRec(String themeWord,Long saveNum,String responsible,String archiveType,AcousticImage acousticImage){
        boolean isOnlyRec = false;
        if(!("".equals(archiveType) || "ALL".equals(archiveType)) && ArchiveType.RecordType.isRecordType(archiveType)){
            isOnlyRec = true;
            return isOnlyRec;
        }
        if(!StringUtils.isEmpty(themeWord)){
            isOnlyRec = true;
            return isOnlyRec;
        }
        if(saveNum!=null){
            isOnlyRec = true;
            return isOnlyRec;
        }
        if(!StringUtils.isEmpty(responsible)){
            isOnlyRec = true;
            return isOnlyRec;
        }
        if(acousticImage!=null){
            isOnlyRec = true;
            return isOnlyRec;
        }
        return isOnlyRec;
    }

    //全局搜索接口
    @Override
    public Map searchGlobalRecordList(Record record, Documents documents,AcousticImage acousticImage,int offset, int limit) {
      PageHelper.startPage(offset, limit);
//      List<Record> list = recordMapper.searchGlobalRecordList(record);
      List<Record> list = null;
      Page pageList = new Page();
      Integer otherTotal = 0;
      boolean isManager = true;

      Long roleId = userService.getRoleByUsr();
      if(!(RoleType.MANAGER.getType().equals(roleId)||RoleType.ARRANGE.getType().equals(roleId)||RoleType.DEPT_MANAGER.getType().equals(roleId)||RoleType.DEPT_ARRANGE.getType().equals(roleId))){
          Map map = new HashMap();
          map.put("thisPage",new PageInfo(null));
          map.put("otherHave", otherTotal);
          map.put("isManager", isManager);
          return map;
      }

        boolean isOnlyDoc = isOnlyDoc(documents.getDocAttr(),record.getArchiveType());
      boolean isOnlyRec = isOnlyRec(record.getThemeWord(),record.getSaveNum(),record.getResponsible(),record.getArchiveType(),acousticImage);
      if(isOnlyDoc && isOnlyRec){
          Map map = new HashMap();
          map.put("thisPage",new PageInfo(null));
          map.put("otherHave", otherTotal);
          map.put("isManager", isManager);
          return map;
      }

	try {
        String recSql="select distinct id, file_num,title,theme_word,responsible, title as location,r.archive_date,r.importance,r.archive_type,r.reserve_location,'record' as type from records r where 1=1 ";
        // if(documents != null) {
        //     recSql="select distinct r.id,r.file_num,r.title,r.theme_word,r.responsible, r.title as location,r.archive_date,r.importance,r.archive_type,r.reserve_location,'record' as type  from records r,documents d,link_records_documents lrd where "
        //             + " r.id=lrd.record_guid and d.id=doc_id  ";
        // }
        if(acousticImage !=null) {
            recSql="select distinct r.id, r.file_num,r.title,r.theme_word,r.responsible, r.title as location,r.archive_date,r.importance,r.archive_type,r.reserve_location,'record' as type   from records r,documents d,link_records_documents lrd, acoustic_image a where "
                    + " r.id=lrd.record_guid and d.id=doc_id and d.id=a.id ";
        }

        recSql = getSqlAndCer(record, documents, acousticImage, recSql,false);

        String docSql="select distinct r.id,r.file_num,r.title,'' as theme_word,r.responsible, r.title as location,r.archive_date,r.importance,r.archive_type, r.sava_location as reserve_location,'recdoc' as type  from  documents r "
                + " where r.is_archive='f' and r.arrange_flag = 't' " ;
        docSql = getSqlAndCer(record, documents, acousticImage, docSql,true);
        String sql = "";

        User contextUser = userService.getContextUser();
        Role role = contextUser.getRole();
        Dept dept = contextUser.getDept();

//      List<Record> list = new ArrayList<Record>();
        String archiveType = record.getArchiveType();
        String otherRecSql = "";
        String otherDocSql = "";
        String otherSql = "";
        if((RoleType.DEPT_MANAGER.getType().equals(role.getId())||RoleType.DEPT_ARRANGE.getType().equals(role.getId()))&&!KMHK_DEPTID.equals(dept.getId())){
            otherDocSql = docSql;
            otherRecSql = recSql;
            recSql += getSql(" r.dept_id ",dept.getId());
            docSql += getSql(" r.dept_id  ", dept.getId());
            otherDocSql += getSql(" r.dept_id", dept.getId(), "!=");
            otherRecSql += getSql(" r.dept_id", dept.getId(), "!=");
        }
        if(RoleType.ARRANGE.getType().equals(role.getId())||RoleType.DEPT_ARRANGE.getType().equals(role.getId())){
            docSql += getSql("r.arranged_by", contextUser.getId());
        }
        if(!isOnlyDoc && !isOnlyRec) {
            if(acousticImage== null && !isNotEmpty(record.getThemeWord()) && !isNotEmpty(record.getSaveNum())) {
                sql ="select * from ("+recSql+" union all "+docSql+") f ";
                otherSql = "select * from ("+otherRecSql+" union all "+otherDocSql+") f";
            }
        }else if(isOnlyDoc){
                sql = docSql;
                otherSql = otherDocSql;
        }else if(isOnlyRec){
                sql = recSql;
                otherSql = otherRecSql;
        }

        if((RoleType.DEPT_MANAGER.getType().equals(role.getId())||RoleType.DEPT_ARRANGE.getType().equals(role.getId()))&&!KMHK_DEPTID.equals(dept.getId())){
            String otherTotalSql = "select count(title) as cnt from ("+otherSql+") d ";
            otherTotal = jdbcTemplate.queryForObject(otherTotalSql, Integer.class, new Object[]{});
            isManager = false;
        }


		  String totalSql="select count(title) as cnt from ("+sql+") d ";
		  Integer total = jdbcTemplate.queryForObject(totalSql, Integer.class, new Object[]{});
		  System.out.println(total);

		  sql +=" limit "+record.getPageSortSearch().getPageSize();
		  sql +=" offset "+(record.getPageSortSearch().getPageNum()-1)*10;
		  System.out.println("searchGlobalRecordList.sql         "+sql);
		  list = jdbcTemplate.query(sql, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					System.out.println("rownum   "+rowNum);
					Record record = new Record();
					record.setId(rs.getLong("id"));
					record.setFileNum(rs.getString("file_num"));
					record.setTitle(rs.getString("title"));
					record.setThemeWord(rs.getString("theme_word"));
					record.setResponsible(rs.getString("responsible"));
					record.setLocation(rs.getString("location"));
					record.setType(rs.getString("type"));
					record.setImportance(rs.getString("importance"));
					record.setArchiveDate(rs.getString("archive_date"));
					record.setReserveLocation(rs.getString("reserve_location"));
					if("record".equalsIgnoreCase(record.getType())) {
						record.setArchiveType(archiveTypeMap_juan.get(rs.getString("archive_type")));
					}else {
						record.setArchiveType(archiveTypeMap_jian.get(rs.getString("archive_type")));
					}

					return record;
				}
		 });
	     pageList.setTotal(Long.parseLong(total+""));
	     for(Record re:list) {
	   	  	pageList.add(re);
	     }
	} catch (Exception e) {
		e.printStackTrace();
	}

      PageInfo page = new PageInfo(pageList);
      Map map = new HashMap();
      map.put("thisPage",page);
        map.put("otherHave", otherTotal);
        map.put("isManager", isManager);
      return map;
    }
    
    public String getSqlAndCer(Record record, Documents documents,AcousticImage acousticImage, String sql,boolean isDoc) {
        String archiveType = record.getArchiveType();
		  String fileNum = record.getFileNum();
		  String title = record.getTitle();
		  String reserveDuration = record.getReserveDuration();
		  String startYear = record.getStartYear();
		  String endYear = record.getEndYear();
		  String importance = record.getImportance();
		  String themeWord=record.getThemeWord();
		  Long saveNum=record.getSaveNum();
		  String reserveLocation=record.getReserveLocation();
		  String responsible = record.getResponsible();

		  if(!isNotEmpty(archiveType) || "ALL".equals(archiveType)) {

          }else {
			  sql += getSql("r.archive_type",archiveType,"=");
		  }
		  if(isNotEmpty(fileNum)) {
			  sql += getSql("r.file_num",fileNum);
		  }
		  if(isNotEmpty(title)) {
			  sql += getSql("r.title",title);
		  }
		  if(isNotEmpty(reserveDuration)) {
			  sql += getSql("r.reserve_duration",reserveDuration);
		  }
		  if(isNotEmpty(startYear)) {
			  if(startYear.trim().length()==4) {
				  sql += getSql("r.archive_year",startYear,">=");
			  }else {
				  sql += getSql("r.archive_date",startYear,">=");
			  }
		  }
		  if(isNotEmpty(endYear)) {
			  if(endYear.trim().length()==4) {
				  sql += getSql("r.archive_year",endYear,"<=");
			  }else {
				  sql += getSql("r.archive_date",endYear,"<=");
			  }
		  }
		  if(isNotEmpty(importance)) {
			  sql += getSql("r.importance",importance);
		  }
		  if(!isDoc) {
			  if(isNotEmpty(themeWord)) {
				  sql += getSql("r.theme_word",themeWord);
			  }
			  if(isNotEmpty(saveNum)) {
				  sql += getSql("r.save_num",saveNum);
			  }
		  }
		  if(isDoc) {
			  if(isNotEmpty(reserveLocation)) {
				  sql += getSql("r.sava_location",reserveLocation);
			  }
		  }else {
			  if(isNotEmpty(reserveLocation)) {
				  sql += getSql("r.reserve_location",reserveLocation);
			  }
		  }
		  if(isNotEmpty(responsible)) {
			  sql += getSql("r.responsible",responsible);
		  }
		  if(documents != null&&isNotEmpty(documents.getDocAttr())) {
			  sql += getSql("doc_attr",documents.getDocAttr());
		  }

		  if(acousticImage != null) {
		      String place = acousticImage.getPlace();
		      String figure = acousticImage.getFigure();
		      String photographer= acousticImage.getPhotographer();
		      String photographyTime= acousticImage.getPhotographyTimeStr();
		      String leader= acousticImage.getLeader();
		      Integer number = acousticImage.getNumber();

		      if(isNotEmpty(place)) {
		    	  sql += getSql("place",place);
		      }
		      if(isNotEmpty(figure)) {
		    	  sql += getSql("figure",figure);
		      }
		      if(isNotEmpty(photographer)) {
		    	  sql += getSql("photographer",photographer);
		      }
		      if(isNotEmpty(photographyTime)) {

		    	  sql += getSql("to_char(photography_time,'YYYY-MM-DD')",photographyTime,"=");
		      }
		      if(isNotEmpty(leader)) {
		    	  sql += getSql("leader",leader);
		      }
		      if(isNotEmpty(number)) {
		    	  sql += getSql("number",number);
		      }
		  }

		  return sql;
    }
    
    public String getSql(String name,String value,String operation) {
    	return " and "+name+operation+"'"+value+"'";
    }
    public String getSql(String name,String value) {
    	return " and "+name+" like '%"+value+"%'";
    }
    public String getSql(String name,Long value,String operation) {
        return " and "+name+operation+value;
    }
    public String getSql(String name,Long value) {
    	return " and "+name+"="+value;
    }
    public String getSql(String name,Integer value) {
    	return " and "+name+"="+value;
    }
    public String getSql(String name,Date value) {
    	return " and "+name+"="+value;
    }

    public boolean isNotEmpty(Long property) {
    	if(property !=null) {
      	  return true;
        }
    	return false;
    }
    public boolean isNotEmpty(Integer property) {
    	if(property !=null) {
      	  return true;
        }
    	return false;
    }
    public boolean isNotEmpty(Date property) {
    	if(property !=null) {
      	  return true;
        }
    	return false;
    }
    public boolean isNotEmpty(String property) {
    	if(property !=null && !"".equals(property)) {
      	  return true;
        }
    	return false;
    }

    @Override
    public List<Record> selectArchiveRecords(Long deptId, String important) {
        return recordMapper.selectArchiveRecords(deptId,important);
    }

    @Override
    public Record selectByFileCode(String reelNum) {
        return recordMapper.selectByFileCode(reelNum,true);
    }

    @Override
    public void removeDocumentFromRecord(Long recordId, Long docId) {
        recordMapper.removeDocumentFromRecord(recordId, docId);
        documentMapper.recordToDocument(docId);
    }

    @Override
    public void modifyRecord(Record record) {
        recordMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageInfo sortSearchRecordList(Record record,User user) {
        Map map = userService.loadingUser(user.getId());
        Long roleId = (Long) map.get("role_id" );
        if(!(RoleType.MANAGER.getType().equals(roleId)||RoleType.ARRANGE.getType().equals(roleId)||RoleType.DEPT_MANAGER.getType().equals(roleId)||RoleType.DEPT_ARRANGE.getType().equals(roleId))){
            return new PageInfo(null);
        }
        PageSortSearch pageSortSearch = record.getPageSortSearch();
        int pageNum = pageSortSearch.getPageNum();
        int pageSize = pageSortSearch.getPageSize();
        String sortName = pageSortSearch.getSortName();
        String sortOrder = pageSortSearch.getSortOrder();
        String startYear = record.getStartYear();
        String endYear = record.getEndYear();
        String title = record.getTitle();
        Long deptId = record.getDeptId();
        String archiveType = record.getArchiveType();
        String reserveDuration = record.getReserveDuration();

        RecordExample recordExample = new RecordExample();
        if (!StringUtils.isEmpty(sortName)) {
            sortName = "fileNum".equals(sortName) ? "file_num" : "archiveDate".equals(sortName) ? "archive_date" : null;
            recordExample.setOrderByClause(sortName + " " + sortOrder);
        } else
        recordExample.setOrderByClause("updated_dt desc");
        RecordExample.Criteria criteria = recordExample.createCriteria();
        if (!"ALL".equals(archiveType)) criteria.andArchiveTypeEqualTo(archiveType);
        if (!"ALL".equals(reserveDuration)) criteria.andReserveDurationEqualTo(reserveDuration);
        if (!StringUtils.isEmpty(title)) criteria.andTitleLike("%" + title + "%");
        if (!StringUtils.isEmpty(startYear)) criteria.andArchiveYearGreaterThanOrEqualTo(startYear);
        if (!StringUtils.isEmpty(endYear)) criteria.andArchiveYearLessThanOrEqualTo(endYear);
        criteria.andRecordFlagEqualTo(true);
        criteria.andDeptIdEqualTo(deptId);
        if(RoleType.ARRANGE.getType().equals(roleId)||RoleType.DEPT_ARRANGE.getType().equals(roleId)){
            criteria.andUserIdEqualTo(user.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Record> list = recordMapper.selectByExample(recordExample);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public List<Record> getRecordsByIds(List<Long> ids) {
        RecordExample recordExample =  new RecordExample();
        recordExample.createCriteria().andIdIn(ids).andRecordFlagEqualTo(true);
        List<Record> list = recordMapper.selectByExample(recordExample);
        return list;
    }

    @Override
    public int turnOver(Long flowId,Long deptId, List<Record> recordList) {
        if(recordList==null||recordList.size()==0) return 0;
        Dept dept = deptMapper.selectByPrimaryKey(deptId);
        String fondsId = dept.getDescription();
        int i = 0;
        for (Record rec : recordList) {
            Record record = new Record();
            record.setFondsId(fondsId);
            record.setId(rec.getId());
            rec.setFondsId(fondsId);
            record.setFileNum(getFileNum(rec));
            record.setDeptId(deptId);
            recordMapper.updateByPrimaryKeySelective(record);
            flowFormDeliverMapper.updateNewFileNum(flowId,record.getId(),record.getFileNum(),"record");
            ++i;
        }
        return i;
    }
   
    //TODO
    @Override
    public PageInfo selectRoom(int offset, int limit,Long deptId,String title,String fileNum) {
        PageHelper.startPage(offset, limit);
        //确定是否 user是什么管理员,
        
        Record record = new Record();
        record.setTitle(title);
        record.setFileNum(fileNum);
        record.setDeptId(deptId);
        
        List<Record> list = recordMapper.selectRooms(record);
        //用PageInfo对结果进行包装
        PageInfo page = new PageInfo(list);
        return page;
    }
}
