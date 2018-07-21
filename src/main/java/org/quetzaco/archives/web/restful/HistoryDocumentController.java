package org.quetzaco.archives.web.restful;

import com.github.pagehelper.PageInfo;
import org.quetzaco.archives.application.biz.HistoryDocumentService;
import org.quetzaco.archives.model.api.APIEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/historydocument")
public class HistoryDocumentController extends BaseRestContoller {


  @Autowired
  HistoryDocumentService historyDocumentService;


  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public HttpEntity<APIEntity<PageInfo>> selectWSDocument(
          @RequestParam(value = "tm", required = false) String tm,
          @RequestParam(value = "dh", required = false) String dh,
          @RequestParam(value = "pageNum",required = false,defaultValue = "1") int offset,
          @RequestParam(value = "pageSize" ,required = false,defaultValue = "0")  int limit) {
    return buildEntity(
        APIEntity.create(historyDocumentService.selectWSDocument(tm, dh, offset, limit)),
        HttpStatus.OK);
  }

  @RequestMapping(value = "/list2", method = RequestMethod.GET)
  public HttpEntity<APIEntity<PageInfo>> selectWGDDocument(
      @RequestParam(value = "tm", required = false) String tm,
      @RequestParam(value = "dh", required = false) String dh,
      @RequestParam(value = "pageNum",required = false,defaultValue = "1") int offset, 
      @RequestParam(value = "pageSize" ,required = false,defaultValue = "0")  int limit) {
    return buildEntity(
        APIEntity.create(historyDocumentService.selectWGDDocument(tm, dh, offset, limit)),
        HttpStatus.OK);
  }

  @RequestMapping(value = "/list3", method = RequestMethod.GET)
  public HttpEntity<APIEntity<PageInfo>> selectSXDocument(
      @RequestParam(value = "tm", required = false) String tm,
      @RequestParam(value = "dh", required = false) String dh,
      @RequestParam(value = "pageNum",required = false,defaultValue = "1") int offset, 
      @RequestParam(value = "pageSize" ,required = false,defaultValue = "0")  int limit ){
    return buildEntity(
        APIEntity.create(historyDocumentService.selectSXDocument(tm, dh, offset, limit)),
        HttpStatus.OK);
  }

  @RequestMapping(value = "/list4", method = RequestMethod.GET)
  public HttpEntity<APIEntity<PageInfo>> selectSWDocument(
      @RequestParam(value = "tm", required = false) String tm,
      @RequestParam(value = "dh", required = false) String dh,
      @RequestParam(value = "pageNum",required = false,defaultValue = "1") int offset, 
      @RequestParam(value = "pageSize" ,required = false,defaultValue = "0")  int limit ){

    return buildEntity(
        APIEntity.create(historyDocumentService.selectSWDocument(tm, dh, offset, limit)),
        HttpStatus.OK);
  }

  @RequestMapping(value = "/list5", method = RequestMethod.GET)
  public HttpEntity<APIEntity<List<Map>>> selectTMDocument(
      @RequestParam(value = "tmid") String tmid
  ) {
    return buildEntity(APIEntity.create(historyDocumentService.selectTMDocument(tmid)),
        HttpStatus.OK);
  }


}