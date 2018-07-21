package org.quetzaco.archives.web.restful;


import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.quetzaco.archives.application.biz.RecordService;
import org.quetzaco.archives.application.message.AccessKey;
import org.quetzaco.archives.application.message.OptionLogger;
import org.quetzaco.archives.model.*;
import org.quetzaco.archives.model.api.APIEntity;
import org.quetzaco.archives.web.config.session.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by deya on 2017/7/14.
 */
@RestController
public class RecordController extends BaseRestContoller{
    @Autowired
    RecordService recordService;

    @OptionLogger(objectType="部门",type = OptionLogger.OpType.DETAIL,description = "查看案卷")
    @RequestMapping(value = "/records/page/{deptId}",method = RequestMethod.GET)
    public HttpEntity<APIEntity<PageInfo>> getRecordsList(@AccessKey@PathVariable Long deptId,
                                                              @Param("offset") int offset,
                                                              @Param("limit") int limit,
                                                          @SessionAttribute(WebSecurityConfig.SESSION_KEY)User user){

        return buildEntity( APIEntity.create(recordService.selectRecords(deptId,offset,limit,user)));
    }

    @RequestMapping(value = "records/sortSearch/{deptId}", method = RequestMethod.POST)
    public HttpEntity<APIEntity<PageInfo>> searchRecordList(@PathVariable("deptId") Long deptId, @RequestBody Record record,@SessionAttribute(WebSecurityConfig.SESSION_KEY)User user) {
        record.setDeptId(deptId);
        return buildEntity(APIEntity.create(recordService.sortSearchRecordList(record,user)));
    }



    @OptionLogger(objectType="案卷",type = OptionLogger.OpType.ADD)
    @RequestMapping(value = "/records/{deptId}",method = RequestMethod.POST)
    public HttpEntity<APIEntity<Record>> createRecord(@PathVariable("deptId") Long deptId, Record record, @SessionAttribute(WebSecurityConfig.SESSION_KEY) User user) {
        record.setDeptId(deptId);
        record.setUserId(user.getId());
        record.setCreatedDt(new Date());
        record.setUpdatedDt(record.getCreatedDt());
        recordService.createRecord(record);

        return buildEntity(APIEntity.create(record), HttpStatus.ACCEPTED);
    }

    @OptionLogger(objectType = "案卷", type = OptionLogger.OpType.UPDATE, description = "修改案卷")
    @RequestMapping(value = "/records/{id}", method = RequestMethod.PUT)
    public HttpEntity<APIEntity> modifyRecord(@AccessKey @PathVariable("id") Long id, @RequestBody Record record) {
        record.setId(id);
        record.setRecordFlag(true);
        record.setIsArchive(false);
        record.setUpdatedDt(new Date());
        recordService.modifyRecord(record);
        return buildEntity(APIEntity.create(record), HttpStatus.ACCEPTED);
    }

    @OptionLogger(objectType = "案卷", type = OptionLogger.OpType.DETAIL, description = "获取案卷信息")
    @RequestMapping(value = "/records/detail/{recordId}",method = RequestMethod.GET )
    public HttpEntity<APIEntity<Record>>getRecordDetail(@PathVariable("recordId") Long recordId){
        return buildEntity(APIEntity.create(recordService.selectRecordDetial(recordId)));
    }

    @OptionLogger(objectType = "案卷",type = OptionLogger.OpType.DETAIL,description = "获取文件")
    @RequestMapping(value = "/documents/record/{recordId}",method = RequestMethod.GET)
    public HttpEntity<APIEntity<List<Documents>>> getDocumentFromRecord(@AccessKey @PathVariable("recordId") Long recordId) {
        return buildEntity(APIEntity.create(recordService.selectDocumentFromRecord(recordId)));
    }

    @OptionLogger(objectType = "档案", type = OptionLogger.OpType.UPDATE, description = "添加文件")
    @RequestMapping(value = "/records/archive/{recordId}/type/{type}", method = RequestMethod.POST)
    public HttpEntity<APIEntity> insertDocumentToRecord(@AccessKey @PathVariable("recordId") Long recordId, @PathVariable("type") int type,
                                                        @RequestBody List<Documents> docs,@SessionAttribute(WebSecurityConfig.SESSION_KEY)User contextUser) {

        return buildEntity(APIEntity.create(recordService.insertDocumentToRecord(recordId, docs, type,contextUser)), HttpStatus.ACCEPTED);
    }

    //回退
    @OptionLogger(objectType = "档案", type = OptionLogger.OpType.DEL, description = "回退")
    @RequestMapping(value = "/records/{recordId}/remove/{docId}", method = RequestMethod.GET)
    public HttpEntity<APIEntity<Record>> removeDocumentFromRecord(@AccessKey @PathVariable("recordId") Long recordId, @PathVariable("docId") Long docId) {
        recordService.removeDocumentFromRecord(recordId, docId);
        return buildEntity(APIEntity.create(null));
    }

    //获取归档的案卷
    @OptionLogger(objectType = "档案", type = OptionLogger.OpType.DETAIL, description = "获取归档的案卷")
    @RequestMapping(value = "records/archive/{archiveId}",method = RequestMethod.GET)
    public HttpEntity<APIEntity<PageInfo>>selectRecordFromArchive(@AccessKey@PathVariable("archiveId")Long archiveId,
                                                                  @Param("offset") int offset,
                                                                  @Param("limit") int limit){
        return buildEntity(APIEntity.create(recordService.selectRecordFromArchive(archiveId,offset,limit)));
    }

    //搜索案卷
    @RequestMapping(value = "records/search/{deptId}",method = RequestMethod.GET)
    public HttpEntity<APIEntity<PageInfo>>searchRecordList(@PathVariable("deptId")Long deptId, @Param("title")String title,
                                                           @Param("reelNum")String reelNum ,@Param("offset") int offset,@Param("limit") int limit){
        Record record = new Record();
        record.setTitle(title);

        record.setFileNum(reelNum);
        record.setDeptId(deptId);


        return buildEntity(APIEntity.create(recordService.searchRecordList(record,offset,limit)));
    }

    @RequestMapping(value = "records/searchGlobal",method = RequestMethod.GET)
    public HttpEntity<APIEntity> searchGlobalRecordList(
            @Param("archiveType") String archiveType,
            @Param("fileNum") String fileNum,
            @Param("title") String title,
            @Param("reserveDuration") String reserveDuration,
            @Param("startYear") String startYear,
            @Param("endYear") String endYear,
            @Param("importance") String importance,
            @Param("docAttr") String docAttr,
            @Param("themeWord") String themeWord,
            @Param("saveNum") String saveNum,
            @Param("responsible") String responsible,
            @Param("reserveLocation") String reserveLocation,
            @Param("place") String place,
            @Param("figure") String figure,
            @Param("photographer") String photographer,
            @Param("photographyTime") String photographyTime,
            @Param("leader") String leader,
            @Param("number") String number,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int offset,
            @RequestParam(value = "pageSize", required = false, defaultValue = "0") int limit,HttpServletRequest request,
            @SessionAttribute(WebSecurityConfig.SESSION_KEY)User contextUser) {
    	System.out.println("    searchGlobalRecordList        ");
    	System.out.println("    searchGlobalRecordList        "+request.getParameter("saveNum"));
    	System.out.println("    searchGlobalRecordList        "+request.getParameter("userId"));
    	System.out.println("    searchGlobalRecordList        "+request.getParameter("docAttr"));

        Record record = new Record();
        record.setArchiveType(archiveType);
        record.setTitle(title);
        record.setFileNum(fileNum);
        record.setReserveDuration(reserveDuration);
        record.setStartYear(startYear);
        record.setEndYear(endYear);
        record.setImportance(importance);
        record.setThemeWord(themeWord);
        PageSortSearch page = new PageSortSearch();
        page.setPageNum(offset);
        page.setPageSize(limit);
        record.setPageSortSearch(page);
        
        try {
        	record.setSaveNum(Long.parseLong(saveNum));
        }catch(Exception e) {
//        	e.printStackTrace();
        }
        record.setResponsible(responsible);
        record.setReserveLocation(reserveLocation);
            
        Documents doc = new Documents();
        doc.setDocAttr(docAttr);
        
        AcousticImage img = null;
        if(isNotEmpty(place) ||isNotEmpty(figure) ||isNotEmpty(photographer) ||
        		isNotEmpty(photographyTime) ||isNotEmpty(leader) ||isNotEmpty(number) ) {
	        img = new AcousticImage();
	        img.setPlace(place);
	        img.setFigure(figure);
	        img.setPhotographer(photographer);
	        img.setPhotographyTimeStr(photographyTime);
	        img.setLeader(leader);
	        try {
	        	img.setNumber(Integer.parseInt(number));
	        }catch(Exception e) {
	//        	e.printStackTrace(); 
	        }
        }

      return buildEntity(
          APIEntity.create(recordService.searchGlobalRecordList(record, doc, img, offset, limit)));
    }
    public boolean isNotEmpty(String property) {
    	if(property !=null && !"".equals(property)) {
      	  return true;
        }
    	return false;
    }

    @RequestMapping(value = "records/archiveto/{deptId}",method = RequestMethod.GET)
    public HttpEntity<APIEntity<List<Record>>>selectArchiveRecords(@PathVariable("deptId")Long deptId,@Param("imprortant")String imprortant){
        return buildEntity(APIEntity.create(recordService.selectArchiveRecords(deptId,imprortant)));
    };
    
    
    @RequestMapping(value = "records/selectrooms", method = RequestMethod.GET)
    public HttpEntity<APIEntity<PageInfo>> searchRooms(@Param("deptId") Long deptId,
    		@Param("title") String title,@Param("fileNum") String fileNum,
            @RequestParam(value = "offset" ,required = false,defaultValue = "1") int offset,
            @RequestParam(value = "limit",required = false,defaultValue = "10000") int limit,
        @SessionAttribute(WebSecurityConfig.SESSION_KEY)User user) {
    	
        return buildEntity(APIEntity.create(recordService.selectRoom(offset,limit,deptId,title,fileNum)));
    }
}

