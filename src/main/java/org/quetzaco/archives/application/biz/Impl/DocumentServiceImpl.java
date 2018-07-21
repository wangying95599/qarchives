package org.quetzaco.archives.application.biz.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.quetzaco.archives.application.biz.DocumentService;
import org.quetzaco.archives.application.biz.ElasticsearchService;
import org.quetzaco.archives.application.biz.SwiftService;
import org.quetzaco.archives.application.biz.UserService;
import org.quetzaco.archives.application.dao.*;
import org.quetzaco.archives.application.search.elastic.ElasticSearchDao;
import org.quetzaco.archives.model.*;
import org.quetzaco.archives.util.boot.RoleType;
import org.quetzaco.archives.util.config.ArchiveProperties;
import org.quetzaco.archives.util.config.ElasticsearchProperties;
import org.quetzaco.archives.util.runnable.ThreadLocalTest;
import org.quetzaco.converter.client.Conversion;
import org.quetzaco.converter.client.ConversionManager;
import org.quetzaco.converter.client.ConversionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by deya on 2017/7/13.
 */
@Service

public class DocumentServiceImpl implements DocumentService {
    private final static Logger LOGGER = LoggerFactory.getLogger(DocumentServiceImpl.class);

    @Autowired
    DocumentsMapper documentMapper;
    @Autowired
    FileMapper fileMapper;
    @Autowired
    ArchiveProperties archiveProperties;
    @Autowired
    FlowsMapper flowsMapper;
    @Autowired
    SwiftService swiftService;
    @Autowired
    AcousticImageMapper acousticImageMapper;
    @Autowired
    DeptMapper deptMapper;
    @Autowired
    ElasticSearchDao esDao;
    @Autowired
    UserService userService;
    @Autowired
    FlowFormDeliverMapper flowFormDeliverMapper;
    @Autowired
    ElasticsearchProperties elasticsearchProperties;
    @Autowired
    ElasticsearchService elasticsearchService;
    @Autowired
    @Qualifier("archivesJdbcTemplate")
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    RedisTemplate redisTemplate;
    
    @Override
    public PageInfo selectFileList(int type, Long deptId, int offset, int limit, String sortName, String sortOrder, String title, String startYear, String endYear,User user) {
        Long roleId = userService.getRoleByUsr(user.getId());

        DocumentsExample documentsExample = new DocumentsExample();
        DocumentsExample.Criteria criteria = documentsExample.createCriteria();
        //权限判断
        if (RoleType.EVERYMAN.getType().equals(roleId)) {
            return new PageInfo(null);
        }else if(RoleType.UPLOAD.getType().equals(roleId)){
            if (type != 1)return new PageInfo(null);
            criteria.andUserIdEqualTo(user.getId());
        }else if(RoleType.DEPT_ARRANGE.getType().equals(roleId)||RoleType.ARRANGE.getType().equals(roleId)){
            if(type == 3){
                criteria.andArrangedByEqualTo(user.getId());
            }
        }

        if (sortName != null) {
            sortName = "beforeNum".equals(sortName) ? "before_num" : "documentCreatime".equals(sortName) ? "document_creatime" : "archiveDate".equals(sortName) ? "archive_date" : "dataSource".equals(sortName)?"data_source":null;
            documentsExample.setOrderByClause(sortName + " " + sortOrder);
        } else
            documentsExample.setOrderByClause("document_creatime DESC ");

        if (type == 1) {
            criteria.andArrangeFlagEqualTo(false).andDataSourceNotEqualTo("OA");
        } else if (type == 2) {
            criteria.andArrangeFlagEqualTo(false).andDataSourceEqualTo("OA");
        } else if (type == 3) {
            criteria.andArrangeFlagEqualTo(true).andIsArchiveEqualTo(false);
        } else if (type == 4) {
            criteria.andArrangeFlagEqualTo(false);
        } else if (type == 5) {
            criteria.andArrangeFlagEqualTo(false).andImportanceNotEqualTo("重要");
        }

       /* if(RoleType.ARRANGE.getType().equals(roleId)||RoleType.DEPT_ARRANGE.getType().equals(roleId)){
            criteria.andUserIdEqualTo(user.getId());
        }*/
        criteria.andDeptIdEqualTo(deptId).andRecordFlagEqualTo("00");


        PageHelper.startPage(offset, limit);
        List<Documents> list = documentMapper.selectByExample(documentsExample);
        // List<Documents> list = documentMapper.selectFileList(deptId);
//用PageInfo对结果进行包装
        PageInfo page = new PageInfo(list);

        return page;
    }

//  @Override
//  public Document selectDocumentDetail(Long fildId) {
//    return documentMapper.selectDocumentDetail(fildId);
//  }

    @Override
    public Documents selectDocumentByFileCode(String fileCode) {
        return documentMapper.selectDocumentByFileCode(fileCode, "00");
    }

    @Override
    public synchronized void saveDocumentDetailInfo(Documents document,User contextUser) {
        // document.setArrangeFlag(true);
        Documents document1 = documentMapper.selectByPrimaryKey(document.getId());
        if (StringUtils.isEmpty(document1.getFileNum())) {
            String fileNum = getFileNum(document);
            document.setFileNum(fileNum);
        }
        documentMapper.updateByPrimaryKeySelective1(document);
        if(!document.getArrangeFlag()) return;

        FileExample fileExample = new FileExample();
        fileExample.createCriteria().andDocIdEqualTo(document1.getDocumentLocalId()).andRecordFlagEqualTo(true);

        //添加归档人
        if(document.getArrangeFlag()){
            documentMapper.updateArrangedBy(document.getId(), contextUser.getId());
        }

        List<Files> filesList = fileMapper.selectByExample(fileExample);
        for(Files files :filesList){
            addArchiveTypeAndDateToFile(document,files);
            files.setArrangedBy(contextUser.getId());
            indexFileToES(files, archiveProperties.getFileStorage() + "/" + files.getLocation());
        }
        // documentMapper.saveDocumentDetailInfo(document);
    }

    @Override
    public void addArchiveTypeAndDateToFile(Documents documents, Files files){
        files.setArchiveType(documents.getArchiveType());
        files.setArchiveYear(documents.getArchiveYear());
    }

    private String getFileNum(Documents document) {
        String fondsId = document.getFondsId();
        String archiveType = document.getArchiveType();
        String archiveYear = document.getArchiveYear();
        String reserveDuration = document.getReserveDuration();
        String prefix = fondsId + "-" + archiveType + "-" + archiveYear + "-" + reserveDuration;
        swiftService.getSwiftNumber(prefix,"jianji");
        int num = swiftService.saveSwiftNumber(prefix);
        return prefix + "-" + new DecimalFormat("0000").format(num);
    }

    @Override
    public int deleteDocument(List<Long> ids) {
        List<String> fileIds = documentMapper.getSubFidBydocIds(ids);
        elasticsearchService.flagToFalse(fileIds);
        DocumentsExample documentsExample = new DocumentsExample();
        documentsExample.createCriteria().andIdIn(ids);
        Documents documents = new Documents();
        documents.setRecordFlag("01");
        DateFormat dateFormat = DateFormat.getDateInstance();
        documents.setUpdatedDt(dateFormat.format(new Date()));
        return documentMapper.updateByExampleSelective(documents, documentsExample);
        // return documentMapper.deleteDocument(fileId);
    }

    @Override
    public List<Files> selectFile(String docId) {
        return fileMapper.selectFile(docId);
    }

    @Override
    public int uploadDocument(Documents document) {
        return documentMapper.insertSelective(document);
        // return documentMapper.uploadDocument(document);
    }

    @Override
    public void uploadFiles(Files files) {
        String fileName = files.getFileName();
        String baseName = fileName.substring(0, fileName.lastIndexOf("."));
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String dupBaseName = baseName + "(";
        //保证只有一个正文，（已取消）
        /*if ("main".equals(files.getFileType())) {
            fileMapper.updateMainByDocId(files.getDocId());
        }*/
        List<Files> dupFiles = documentMapper.isDuplicateFileName(files.getDocId(), dupBaseName, fileName);

        List<Files> realDupFiles = new ArrayList<>();
        // int size = dupFiles.size();
        int size = 0;
        for (Files files1 : dupFiles) {
            if (files1.getRecordFlag()) {
                String name = files1.getFileName();
                if (name.length() == fileName.length())
                    size = 1;
                else
                    size = Character.getNumericValue(name.charAt(name.lastIndexOf("(") + 1)) + 1;
                break;
            }
            // --size;
        }
        String realName;
        if (size > 0) {
            files.setFileName(baseName + "(" + size + ")" + suffix);
        }

        fileMapper.uploadFiles(files);
    }

    @Autowired
    private ConversionManager cm;
    @Value("${CONVERT_DOCUMENT_FORMAT}")
    private String docFormats;

    @Async("myTaskAsyncPool")
    public void ConvertFileToSwf(String filePath) {
        ConvertFileToSwf1(filePath);
    }

    public boolean ConvertFileToSwf1(String filePath) {
        boolean status = false;
        Conversion conv = null;
        try {
            conv = cm.getConversion();
            String[] docs = docFormats.split(",");
            String ext = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
            for (int i = 0; i < docs.length; i++) {
                if (docs[i].equalsIgnoreCase(ext)) {
                    File targetFile = new File(filePath);
                    String pdfPath = filePath.substring(0, filePath.lastIndexOf(".")) + ".pdf";
                    if (ext.equalsIgnoreCase("pdf"))
                        pdfPath = filePath.substring(0, filePath.lastIndexOf(".")) + "." + ext;
                    File pdfFile = new File(pdfPath);
                    if (!pdfFile.exists()) {
                        ConversionUtil.File_2PDF(targetFile, conv, false);
                    }
                    System.out.println("filePath---------" + filePath);
                    status = ConversionUtil.File_2SWF(pdfFile, conv, false);
                    break;
                }
            }


        } catch (java.lang.Exception e) {
        } finally {
            if (conv != null) {
                conv.close();
            }
        }

        return status;
    }

    @Override
    public void batchModify(List<Long> ids, Documents documents) {
        documents.setArrangeFlag(true);
        DocumentsExample documentsExample = new DocumentsExample();
        documentsExample.createCriteria().andIdIn(ids);
        documentMapper.updateByExampleSelective(documents, documentsExample);
    }

    @Override
    public PageInfo searchFileList(BatchDocuments batchDocuments,User user) {
        Map map = userService.loadingUser(user.getId());
        Long roleId = (Long) map.get("role_id");
        if(RoleType.EVERYMAN.getType().equals(roleId)){
            return new PageInfo(null);
        }
        int pageNum = batchDocuments.getPageNum();
        int pageSize = batchDocuments.getPageSize();
        String sortName = batchDocuments.getSortName();
        String sortOrder = batchDocuments.getSortOrder();
        int type = batchDocuments.getType();
        Documents documents = batchDocuments.getDocuments();
        String title = documents.getTitle();
        Long deptId = documents.getDeptId();
        String startYear = documents.getStartYear();
        String endYear = documents.getEndYear();
        String archiveType = documents.getArchiveType();
        String reserveDuration = documents.getReserveDuration();
        String oaSyncType = documents.getOaSyncType();
        String beforeNum = documents.getBeforeNum();
        DocumentsExample documentsExample = new DocumentsExample();
        if (sortName != null) {
            sortName = "beforeNum".equals(sortName) ? "before_num" : "documentCreatime".equals(sortName) ? "document_creatime" : "archiveDate".equals(sortName) ? "archive_date" : "dataSource".equals(sortName)?"data_source":null;
            documentsExample.setOrderByClause(sortName + " " + sortOrder);
        } else
            documentsExample.setOrderByClause("document_creatime DESC ");
        DocumentsExample.Criteria criteria = documentsExample.createCriteria();

        if (RoleType.EVERYMAN.getType().equals(roleId)) {
            return new PageInfo(null);
        }else if(RoleType.UPLOAD.getType().equals(roleId)){
            if (type != 1)return new PageInfo(null);
            criteria.andUserIdEqualTo(user.getId());
        }else if(RoleType.DEPT_ARRANGE.getType().equals(roleId)||RoleType.ARRANGE.getType().equals(roleId)){
            if(type == 3){
                criteria.andArrangedByEqualTo(user.getId());
            }
        }

        if (type == 1) criteria.andArrangeFlagEqualTo(false).andDataSourceNotEqualTo("OA");
        else if (type == 2) {
            criteria.andArrangeFlagEqualTo(false).andDataSourceEqualTo("OA");
            if(!"全部".equals(oaSyncType)) criteria.andOaSyncTypeEqualTo(oaSyncType);
            if(!StringUtils.isEmpty(beforeNum)) criteria.andBeforeNumLike("%" + beforeNum + "%");
        } else if (type == 3) {
            criteria.andArrangeFlagEqualTo(true).andIsArchiveEqualTo(false);
            if (!"ALL".equals(archiveType)) criteria.andArchiveTypeEqualTo(archiveType);
            if (!"ALL".equals(reserveDuration)) criteria.andReserveDurationEqualTo(reserveDuration);
        } else if (type == 4) {
            criteria.andArrangeFlagEqualTo(false);
        }
        if (!StringUtils.isEmpty(title))
            criteria.andTitleLike("%" + title + "%");
        if (!StringUtils.isEmpty(startYear)) {
            if (type != 3) criteria.andDocumentCreatimeGreaterThanOrEqualTo(startYear);
            else criteria.andArchiveYearGreaterThanOrEqualTo(startYear);
        }
        if (!StringUtils.isEmpty(endYear)) {
            if (type != 3) criteria.andDocumentCreatimeLessThanOrEqualTo(endYear);
            else criteria.andArchiveYearLessThanOrEqualTo(endYear);
        }
        criteria.andRecordFlagEqualTo("00").andDeptIdEqualTo(deptId);

        //下面的代码 对于 客户的需求应该是错的
        /*if(RoleType.ARRANGE.getType().equals(roleId)||RoleType.DEPT_ARRANGE.getType().equals(roleId)){
            criteria.andUserIdEqualTo(user.getId());
        }*/
        PageHelper.startPage(pageNum, pageSize);
        List<Documents> list = documentMapper.selectByExample(documentsExample);
        PageInfo pageInfo = new PageInfo(list);

        return pageInfo;
    }

    @Override
    public void saveDocumentDetailInfo1(Documents documents) {
        AcousticImage acousticImage = documents.getAcousticImage();
        if (acousticImage != null) {
            acousticImage.setId(new Integer(documents.getId().intValue()));
            acousticImageMapper.updateByPrimaryKey(acousticImage);
        }
        int documentsMapper = documentMapper.updateByPrimaryKeySelective1(documents);
    }

    @Override
    public List<Documents> getDocumentsByIds(List<Long> ids) {
        DocumentsExample documentsExample = new DocumentsExample();
        documentsExample.createCriteria().andIdIn(ids).andRecordFlagEqualTo("00");
        List<Documents> list = documentMapper.selectByExample(documentsExample);

        return list;
    }

    @Override
    public PageInfo getRecycleBin(Long deptId, int type, int pageNum, int pageSize,String sortName,String sortOrder,User user) {
        Map map = userService.loadingUser(user.getId());
        Long roleId = (Long) map.get("role_id");
        if(!(RoleType.MANAGER.getType().equals(roleId)||RoleType.ARRANGE.getType().equals(roleId)||RoleType.DEPT_MANAGER.getType().equals(roleId)||RoleType.DEPT_ARRANGE.getType().equals(roleId))){
            return new PageInfo(null);
        }
        DocumentsExample documentsExample = new DocumentsExample();
        if(sortName!=null){
            sortName = "beforeNum".equals(sortName) ? "before_num" : "documentCreatime".equals(sortName) ? "document_creatime" : "archiveDate".equals(sortName) ? "archive_date" : "dataSource".equals(sortName)?"data_source":null;
            documentsExample.setOrderByClause(sortName+" "+sortOrder);
        }else{
            documentsExample.setOrderByClause("updated_dt desc");
        }
        DocumentsExample.Criteria criteria = documentsExample.createCriteria();
        switch (type) {
            case 0:
                break;//所有
            case 1://今天
                criteria.andUpdatedDtEqualTo(getDateWithNum(0));
                break;
            case 2://昨天
                criteria.andUpdatedDtEqualTo(getDateWithNum(-1));
                break;
            case 3://前7天
                criteria.andUpdatedDtGreaterThan(getDateWithNum(-7));
                break;
            case 4://前15天
                criteria.andUpdatedDtGreaterThan(getDateWithNum(-15));
                break;
            case 5://前30天
                criteria.andUpdatedDtGreaterThan(getDateWithNum(-30));
                break;
            case 6://超过30天
                criteria.andArchiveYearLessThanOrEqualTo(getDateWithNum(-30));
                break;
            default:
                break;
        }
        criteria.andDeptIdEqualTo(deptId).andRecordFlagEqualTo("01");
        if(RoleType.ARRANGE.getType().equals(roleId)||RoleType.DEPT_ARRANGE.getType().equals(roleId)){
            criteria.andUserIdEqualTo(user.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Documents> list = documentMapper.selectByExample(documentsExample);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public int recover(List<Long> ids) {
        List<String> fields = documentMapper.getSubFidBydocIds(ids);
        elasticsearchService.flagToTrue(fields);
        return updateRecordFlag(ids, "00");
    }

    private int updateRecordFlag(List<Long> ids, String recordFlag) {
        if (ids == null || ids.size() == 0) return 0;
        DocumentsExample documentsExample = new DocumentsExample();
        documentsExample.createCriteria().andIdIn(ids);
        Documents documents = new Documents();
        documents.setUpdatedDt(DateFormat.getDateInstance().format(new Date()));
        documents.setRecordFlag(recordFlag);
        return documentMapper.updateByExampleSelective(documents, documentsExample);
    }

    @Override
    public int remove(List<Long> ids) {
        List<String> fileIds = documentMapper.getSubFidBydocIds(ids);
        boolean isSucceeded = elasticsearchService.bulkDel(fileIds);
        return updateRecordFlag(ids, "02");
    }

    @Transactional
    @Override
    public int turnOver(Long flowId,Long deptId,List<Documents> documentsList) {
        if(documentsList==null||documentsList.size()==0) return 0;
        Dept dept = deptMapper.selectByPrimaryKey(deptId);
        String simpleDeptName = dept.getDescription();
        int i=0;
        for (Documents doc :documentsList){
            Documents documents = new Documents();
            documents.setFondsId(simpleDeptName);
            documents.setDeptId(deptId);
            doc.setFondsId(simpleDeptName);
            documents.setFileNum(getFileNum(doc));
            documents.setId(doc.getId());
            documentMapper.updateByPrimaryKeySelective(documents);
            flowFormDeliverMapper.updateNewFileNum(flowId,documents.getId(),documents.getFileNum(), "documents");
            ++i;
        }
        return i;
    }

    private String getDateWithNum(int number){
        DateFormat dateFormat = DateFormat.getDateInstance();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, number);
        Date date = calendar.getTime();
        return dateFormat.format(date);
    }


    @Override
    public List<Documents> selectComplete(Long deptId, String imprortant) {
//    PageHelper.startPage(offset, limit);
//    List<Document> list = documentMapper.selectFileList(deptId);
////用PageInfo对结果进行包装
//    PageInfo page = new PageInfo(list);

        return documentMapper.selectComplete(deptId, imprortant);
        // return null;
    }

    @Override
    public PageInfo selectFromRecord(Long recordId, String archiveType, PageSortSearch pageSortSearch) {
        int pageNum = pageSortSearch.getPageNum();
        int pageSize = pageSortSearch.getPageSize();
        String sortName = pageSortSearch.getSortName();
        String sortOrder = pageSortSearch.getSortOrder();

        String order = null;
        if (sortName == null) order = "archive_date desc";
        else {
            if ("insideNum".equals(sortName)) order = "inside_num " + sortOrder;
            else if ("archiveDate".equals(sortName)) order = "archive_date " + sortOrder;
        }

        Long contextUsrId = ThreadLocalTest.getUsrTl();
        Long roleId = userService.getRoleByUsr(contextUsrId);
        LOGGER.debug("documentServiceImpl  --- selectFromRecord ---- roleId:  "+roleId);
        if(RoleType.UPLOAD.getType().equals(roleId)||RoleType.EVERYMAN.getType().equals(roleId)){
            return new PageInfo(null);
        }
        boolean isArrange = false;
        if(RoleType.ARRANGE.getType().equals(roleId)||RoleType.DEPT_ARRANGE.getType().equals(roleId)){
            isArrange = true;
        }


        PageHelper.startPage(pageNum, pageSize);
        List<Documents> list;
        if (!"SX".equals(archiveType)) list = documentMapper.selectFromRecord(recordId, order,isArrange,contextUsrId);
        else list = documentMapper.acousticFromRecord(recordId, order,isArrange,contextUsrId);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public PageInfo searchDocumentList(Documents document, int offset, int limit) {
        PageHelper.startPage(offset, limit);
        List<Documents> list = documentMapper.searchDocumentList(document);
        //用PageInfo对结果进行包装
        PageInfo page = new PageInfo(list);
        return page;
    }
    
    //TODO
    @Override
    public Map searchGlobalDocumentList(Documents document, int offset, int limit, User contextUser) {
        PageHelper.startPage(offset, limit);
//      List<Documents> list = documentMapper.searchGlobalDocumentList(document);
        Page<Files> list = new Page();
        list.setStartRow(offset);
        Map loadmap  =  userService.loadingUser(contextUser.getId());
        Long roleId = (Long) loadmap.get("role_id");
        Long realDepId = (Long) loadmap.get("dept_id");
        Long deptId = null;
        if(RoleType.MANAGER.getType().equals(roleId)||RoleType.ARRANGE.getType().equals(roleId))
            deptId=88888l;
        else if(RoleType.DEPT_MANAGER.getType().equals(roleId)||RoleType.DEPT_ARRANGE.getType().equals(roleId))
            deptId=realDepId;
        document.setDeptId(deptId);
        if(deptId==null)
            return new HashMap();
        Map esMap = elasticsearchService.createSearch(document,list);
        list = (Page<Files>) esMap.get("thisPage");
        if(list==null||list.isEmpty()) {
            esMap.put("thisPage",new PageInfo<>(list) );
            return  esMap;
        }
        // System.out.println(list.get(0).toString());

        HashSet set = new HashSet();
        String cond = "(";
        for (Files f : list) {
            if (!cond.contains(f.getDocId())) {
                cond += "'" + f.getDocId() + "',";
            }
        }
        if (cond.endsWith(",")) {
            cond = cond.substring(0, cond.length() - 1) + ")";
        }

        String sql = "select distinct d.document_local_id, d.title as dTitle,l.title as rTitle,l.theme_word,d.doc_attr,d.data_source,d.sava_location from documents d LEFT JOIN "
                + "(select r.title,l.doc_id,r.theme_word from records r,link_records_documents l where r.id=l.record_guid) l "
                + "  on d.id=l.doc_id where document_local_id in " + cond;
        System.out.println("全文检索 .sql         " + sql);

        Map<String, String> map = new HashMap<String, String>();
        List list2 = jdbcTemplate.query(sql, new RowMapper() {
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                System.out.println("rownum   " + rowNum);
                String rTitle = rs.getString("rTitle");
                String dTitle = rs.getString("dTitle");
                String sava_location = rs.getString("sava_location") == null ? "" : rs.getString("sava_location");
                String docId = rs.getString("document_local_id");
                String theme_word = rs.getString("theme_word") == null ? "" : rs.getString("theme_word");
                String doc_attr = rs.getString("doc_attr") == null ? "" : rs.getString("doc_attr");
                String data_source = rs.getString("data_source") == null ? "" : rs.getString("data_source");
                String info = "主题词:" + theme_word + "&nbsp;文件属性:" + doc_attr + "&nbsp;数据来源：" + data_source + "&nbsp;实体位置：" + sava_location;
                if (rTitle != null && rTitle.trim().length() > 0) {
                    map.put(docId, (rTitle + "/" + dTitle) + "=-=" + info);
                } else {
                    map.put(docId, dTitle + "=-=" + info);
                }
                return docId;
            }
        });
        for (Files f : list) {
            String id = f.getDocId();
            String desc = map.get(id);
            String title = desc.split("=-=")[0];
            String info = desc.split("=-=")[1];
            f.setLocation(title + "/" + f.getFileName());
            f.setInfo(info);
        }

        PageInfo page = new PageInfo(list);
        esMap.put("thisPage", page);
        return esMap;
    }

    @Override
    public void destroyReelNumByType(Long flowId, String reelNum, String reelType) {
        if ("document".equals(reelType))
            documentMapper.destroyReelNumByType("documents", "01", reelNum);
        if ("record".equals(reelType))
            documentMapper.destroyReelNumByType("records", "f", reelNum);
        if ("archive".equals(reelType))
            documentMapper.destroyReelNumByType("archives", "reel_num", reelNum);
        if ("box".equals(reelType))
            documentMapper.destroyReelNumByType("box", "reel_num", reelNum);

        Flows flows = new Flows();
        flows.setId(flowId);
        flows.setResult("DELETED");
        flows.setDeadLine(new Date());
        flowsMapper.updateByPrimaryKeySelective(flows);
    }

    @Override
    public Long getIdByReelNumAndType(String reelNum, String reelType) {
        Long id;
        switch (reelType) {
            case "document":
                id = documentMapper.getIdByReelNumAndType("documents", "document_local_id", "file_code", reelNum);
                break;
            case "record":
                id = documentMapper.getIdByReelNumAndType("records", "id", "reel_num", reelNum);
                break;
            case "archive":
                id = documentMapper.getIdByReelNumAndType("archives", "id", "reel_num", reelNum);
                break;
            case "box":
                id = documentMapper.getIdByReelNumAndType("box", "id", "reel_num", reelNum);
                break;
            default:
                id = null;
        }

        return id;
    }

    @Override
    public boolean deleteFile(Long fileId, String location, Long docId) {
        Documents document = documentMapper.selectByPrimaryKey(docId);
        if (document.getArrangeFlag()) {
            return false;
        }
        int a = documentMapper.deleteFile(fileId);
        esDao.deleteDocument(elasticsearchProperties.getIndex(),elasticsearchProperties.getType(),String.valueOf(fileId));
    /*File file = new File(location);
    if (file.exists() && file.isFile()) {
      return file.delete() ? true : false;
    } else
      return false;*/
        return a == 1 ? true : false;
    }

    @Override
    public boolean validReelNumByReelType(String reelNum, String reelType) {
        return false;
    }

    @Override
    public boolean indexFileToES(Files file,String path) {
    	org.quetzaco.archives.application.search.elastic.content.FileUtils fileUtils=
    			new org.quetzaco.archives.application.search.elastic.content.FileUtils();
    	System.setProperty("root","/opt/qarchive/elasticsearch/");
    // System.setProperty("root","D:\\localFile");
        Files f = documentMapper.getDeptIdBylocalId(file.getFileId());
        file.setDeptId(f.getDeptId());
        file.setDocId(f.getDocId());
        file.setArchiveType(f.getArchiveType());

    	String content = fileUtils.getFileContent(path);
    	System.out.println("content       "+content);
    	file.setContent(content);
    	try {
			esDao.deleteDocument(elasticsearchProperties.getIndex(), elasticsearchProperties.getType(), file.getFileId());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	esDao.createIndex(file, elasticsearchProperties.getIndex(), elasticsearchProperties.getType());
    	return true;
    }
}


