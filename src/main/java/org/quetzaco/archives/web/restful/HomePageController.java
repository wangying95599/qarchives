package org.quetzaco.archives.web.restful;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quetzaco.archives.application.biz.DocumentService;
import org.quetzaco.archives.application.biz.HomePageService;
import org.quetzaco.archives.application.dao.DocumentsMapper;
import org.quetzaco.archives.application.message.OptionLogger;
import org.quetzaco.archives.application.message.OptionLogger.OpType;
import org.quetzaco.archives.model.Files;
import org.quetzaco.archives.model.HomePage;
import org.quetzaco.archives.model.api.APIEntity;
import org.quetzaco.archives.util.config.ArchiveProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/homePage")
public class HomePageController extends BaseRestContoller {
	  
  final  Logger LOGGER = LoggerFactory.getLogger(HomePageController.class);
  
  @Autowired
  ArchiveProperties archiveProperties;
  
  @Autowired
  HomePageService homePageService;
  
  @Autowired
  DocumentService documentService;
  
  @Autowired
  DocumentsMapper documentsMapper;


  @RequestMapping(value = "/link", method = RequestMethod.POST)
  public HttpEntity<APIEntity<?>> Link(@RequestParam("content") String textarea,@RequestParam("docId") String docId) {
      String type = "link";
    homePageService.insert(textarea, type,docId );
    return buildEntity(APIEntity.create(null), HttpStatus.OK);

  }

  @RequestMapping(value = "/information", method = RequestMethod.POST)
  public HttpEntity<APIEntity<?>> information(@RequestParam("content") String textarea,@RequestParam("docId") String docId) {
      String type = "information";
    homePageService.insert(textarea, type,docId );
    return buildEntity(APIEntity.create(null), HttpStatus.OK);
  }
  
  @RequestMapping(value = "/fire", method = RequestMethod.POST)
  public HttpEntity<APIEntity<?>> fire(@RequestParam("content") String textarea,@RequestParam("docId") String docId) {
      String type = "fire";
    homePageService.insert(textarea, type,docId );
    return buildEntity(APIEntity.create(null), HttpStatus.OK);

  }

  @RequestMapping(value = "/room", method = RequestMethod.POST)
  public HttpEntity<APIEntity<?>> room(@RequestParam("content") String textarea,@RequestParam("docId") String docId) {
      String type = "room";
    homePageService.insert(textarea, type,docId );
    return buildEntity(APIEntity.create(null), HttpStatus.OK);
  }

  @RequestMapping(value = "/getRoom", method = RequestMethod.GET)
  public HttpEntity<APIEntity<PageInfo>> getRoom(@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                                                 @RequestParam(value = "pageSize", required = false, defaultValue = "100") int pageSize) {
      return buildEntity(APIEntity.create(homePageService.getContent(pageNum, pageSize,"room")), HttpStatus.OK);
  }
  
  @RequestMapping(value = "/getFire", method = RequestMethod.GET)
  public HttpEntity<APIEntity<PageInfo>> getFire(@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                                                 @RequestParam(value = "pageSize", required = false, defaultValue = "100") int pageSize) {
      return buildEntity(APIEntity.create(homePageService.getContent(pageNum, pageSize,"fire")), HttpStatus.OK);
  }

  @RequestMapping(value = "/getLink", method = RequestMethod.GET)
  public HttpEntity<APIEntity<PageInfo>> getLink(@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                                                 @RequestParam(value = "pageSize", required = false, defaultValue = "100") int pageSize) {
      return buildEntity(APIEntity.create(homePageService.getLink(pageNum, pageSize)), HttpStatus.OK);
  }

  @RequestMapping(value = "/getInformation", method = RequestMethod.GET)
  public HttpEntity<APIEntity<PageInfo>> getInformation(@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                                                        @RequestParam(value = "pageSize", required = false, defaultValue = "100") int pageSize) {
      return buildEntity(APIEntity.create(homePageService.getInformation(pageNum, pageSize)), HttpStatus.OK);
  }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public HttpEntity<APIEntity> updateContent(HomePage homePage) {
        homePage.setUpdateDt(new Date());
        homePageService.updateContent(homePage);
        return buildEntity(APIEntity.create(homePage), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.PUT)
    public HttpEntity<APIEntity> deleteHomePage(@RequestParam("ids[]") List<Long> list) {
        homePageService.deleteHomePage(list);
        return buildEntity(APIEntity.create(null), HttpStatus.OK);
    }


    @OptionLogger(objectType = "文档",type = OpType.UPDATE,description = "上传通知文件")
    @RequestMapping(value="/homepageUpload", method=RequestMethod.POST)
    // @ResponseBody
    public HttpEntity<APIEntity<String>> upload( HttpServletRequest request, HttpServletResponse response)
        throws IllegalStateException, IOException {
    	String docId = UUID.randomUUID().toString();
      //reserveLocation
      //docId
      //fileType
      // String reserveLocation = request.getParameter("reserveLocation");
      Calendar now = Calendar.getInstance();
        String ymd = now.get(Calendar.YEAR) + "/" + (
              now.get(Calendar.MONTH) + 1) + "/" +
              now.get(Calendar.DAY_OF_MONTH);
        String reserveLocation =
                archiveProperties.getFileStorage() + "/" + ymd;
      File reserveLocationFile = new File(reserveLocation);
      if (!reserveLocationFile.exists()) {
        reserveLocationFile.mkdirs();
      }
      // String docId = request.getParameter("docId");
      String fileType = request.getParameter("fileType");
      CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
          request.getSession().getServletContext());
      //新建文件

      //判断 request 是否有文件上传,即多部分请求
      // multipartResolver.setDefaultEncoding("utf-8");
      LOGGER.debug("------------------" + multipartResolver.isMultipart(request));
      if (multipartResolver.isMultipart(request)) {

        //转换成多部分request
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        //取得request中的所有文件名

        Map<String, MultipartFile> map = multiRequest.getFileMap();
        LOGGER.debug("------------------" + map.size());

        for (MultipartFile file : map.values()) {

          //记录上传过程起始时的时间，用来计算上传时间
          long pre = System.currentTimeMillis();
          LOGGER.debug("------------------" + file);
          Files files = new Files();
          if (file != null) {
            //取得当前上传文件的文件名称
            //如果名称不为“”,说明该文件存在，否则说明该文件不存在
            //重命名上传后的文件名
            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            //定义上传路径
            String fileId = UUID.randomUUID().toString();
            String path = reserveLocation + "/" + fileId+suffixName;
              String location = ymd + "/" + fileId + suffixName;
            File localFile = new File(path);
            file.transferTo(localFile);
              documentService.ConvertFileToSwf(path);
            files.setDocId(docId);
            files.setFileType(fileType);
              files.setLocation(location);
            files.setFileName(fileName);
            files.setFileId(fileId);
            files.setUploadDate(new Date());

            documentService.uploadFiles(files);

          }
          //记录上传该文件后的时间
          int finaltime = (int) System.currentTimeMillis();
          System.out.println(finaltime - pre);
        }

      }
      return buildEntity(APIEntity.create(docId),HttpStatus.ACCEPTED);
    }
}
