package org.quetzaco.archives.web.restful;

import com.github.pagehelper.PageInfo;
import com.itextpdf.text.DocumentException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.quetzaco.archives.application.biz.DocumentService;
import org.quetzaco.archives.application.biz.SwiftService;
import org.quetzaco.archives.application.biz.UserService;
import org.quetzaco.archives.application.dao.DocumentsMapper;
import org.quetzaco.archives.application.message.AccessKey;
import org.quetzaco.archives.application.message.MultiAccessKey;
import org.quetzaco.archives.application.message.OptionLogger;
import org.quetzaco.archives.application.message.OptionLogger.OpType;
import org.quetzaco.archives.model.*;
import org.quetzaco.archives.model.api.APIEntity;
import org.quetzaco.archives.util.config.ArchiveProperties;
import org.quetzaco.archives.util.watermark.WaterMarkTwoLineRunner;
import org.quetzaco.archives.util.watermark.WaterMarkUtil;
import org.quetzaco.archives.web.config.session.WebSecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by deya on 2017/7/13.
 */
@RestController
public class DocumentController extends BaseRestContoller {

  //上载
  final static Logger LOGGER = LoggerFactory.getLogger(FileController.class);
  @Autowired
  DocumentService documentService;
  @Autowired
  ArchiveProperties archiveProperties;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    SwiftService swiftService;
    @Autowired
    DocumentsMapper documentsMapper;
    @Value("${WATER_MARK_POSITION}")
    String waterMarkPosition;
    @Value("${WATER_MARK_TEXT}")
    String waterMarkText;
    @Autowired
    UserService userService;

  @OptionLogger(objectType = "部门",type = OpType.DETAIL,description = "查看文档")
  @RequestMapping(value = "/documents/page/{deptId}/type/{type}", method = RequestMethod.GET)
  public HttpEntity<APIEntity<PageInfo>> getDocumentList(@AccessKey @PathVariable("deptId") Long deptId, @PathVariable("type") int type,
                                                         @RequestParam(value = "pageNum",required = false,defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",required = false,defaultValue = "0") int pageSize,
                                                         @RequestParam(value = "sortName", required = false) String sortName,
                                                         @RequestParam(value = "sortOrder", required = false) String sortOrder,
                                                         @RequestParam(value = "title", required = false) String title,
                                                         @RequestParam(value = "startYear", required = false) String startYear,
                                                         @RequestParam(value = "endYear", required = false) String endYear,
                                                         @SessionAttribute(WebSecurityConfig.SESSION_KEY)User user) {

      return buildEntity(APIEntity.create(documentService.selectFileList(type, deptId, pageNum, pageSize, sortName, sortOrder, title, startYear, endYear,user)),
        HttpStatus.OK);
  }

    @OptionLogger(objectType = "部门", type = OpType.DETAIL, description = "查看文档")
    @RequestMapping(value = "/documents/sortSearch/{deptId}/type/{type}", method = RequestMethod.POST)
    public HttpEntity<APIEntity<PageInfo>> searchDocumentList(@AccessKey @PathVariable("deptId") Long deptId, @PathVariable("type") int type, @RequestBody BatchDocuments batchDocuments,@SessionAttribute(WebSecurityConfig.SESSION_KEY)User user) {

        batchDocuments.getDocuments().setDeptId(deptId);
        batchDocuments.setType(type);
        return buildEntity(APIEntity.create(documentService.searchFileList(batchDocuments,user)),
                HttpStatus.OK);
    }


  //    @RequestMapping(value = "/documents/{fileId}",method = RequestMethod.GET)
//    public HttpEntity<APIEntity<Document>>getDocumentDetail(@PathVariable Long fileId){
//        return buildEntity( APIEntity.create(documentService.selectDocumentDetail(fileId)));
//    }
// "deptid":deptid,
//            "status":"已完成",
//            "doucumentId":documentId,
//            "filenum":fileNum,
//            "documentLocalId":documentLocalId,
//            "documentCreateTime":documentCreateTime,

  @OptionLogger(objectType = "文档", type = OpType.UPDATE, description = "修改信息")
  @RequestMapping(value = "documents/save/{fileId}/type/{type}", method = RequestMethod.POST)
  public HttpEntity<APIEntity<Documents>> documentSave(@AccessKey @PathVariable("fileId") Long fileId, @PathVariable("type") int type, @RequestBody Documents documents) {
      documents.setId(fileId);
      documents.setFileName(documents.getTitle());
      documents.setRecordFlag("00");
      if (type == 1) documents.setArrangeFlag(true);
      else if (type == 2) documents.setArrangeFlag(false);
      documents.setLinkFilesId("");
      User contextUser = userService.getContextUser();

      documentService.saveDocumentDetailInfo(documents,contextUser);

      return buildEntity(APIEntity.create(documents), HttpStatus.OK);
  }

  @OptionLogger(objectType = "文档", type = OpType.UPDATE, description = "修改信息")
  @RequestMapping(value = "documents/place/{fileId}", method = RequestMethod.POST)
  public HttpEntity<APIEntity<Documents>> documentPlace(@PathVariable("fileId") Long fileId, @RequestBody Documents documents) {
    documents.setId(fileId);
    documents.setFileName(documents.getTitle());
    
    documentService.saveDocumentDetailInfo1(documents);

    return buildEntity(APIEntity.create(documents), HttpStatus.OK);
  }

    @RequestMapping(value = "documents/batchSave", method = RequestMethod.POST)
    public HttpEntity<APIEntity<Documents>> documentBatchSave(@RequestBody List<Documents> documentsList) {
        /*Documents documents = batchDocuments.getDocuments();
        List<Long> ids = batchDocuments.getIds();*/
        /*documentService.batchModify(ids, documents);*/
        Documents doc1 = documentsList.get(1);
        String fondsId = doc1.getFondsId();
        String archiveType = doc1.getArchiveType();
        String archiveYear = doc1.getArchiveYear();
        String reserveDuration = doc1.getReserveDuration();
        String prefix = fondsId + "-" + archiveType + "-" + archiveYear + "-" + reserveDuration;
        swiftService.getSwiftNumber(prefix, "jianji");
        Collections.sort(documentsList);
        for (Documents documents : documentsList) {
            documents.setArrangeFlag(true);
            User contextUser = userService.getContextUser();
            documentService.saveDocumentDetailInfo(documents,contextUser);
        }
        Documents documents = new Documents();
        return buildEntity(APIEntity.create(documents), HttpStatus.OK);
    }


  /*@OptionLogger(objectType = "文档", type = OpType.UPDATE, description = "修改信息")
  @RequestMapping(value = "documents/save/{fileId}", method = RequestMethod.PUT)
  public HttpEntity<APIEntity<Documents>> documentSave(@PathVariable("fileId") Long fileId,
                                                       @Param("title") String title,
                                                       @Param("fileCode") String fileCode,
                                                       @Param("themeWord") String themeWord,
                                                       @Param("responsible") String responsible,
                                                       @Param("fondsId") String fondsId, @Param("importance") String importance,
                                                       @Param("appendices") Long appendices, @Param("archiveType") String archiveType,
                                                       @Param("comeOffice") String comeOffice, @Param("beloadDepartment") String beloadDepartment,
                                                       @Param("archiveYear") String archiveYear, @Param("reserveDuration") String reserveDuration,
                                                       @Param("saveName") Long saveName, @Param("filecreatetime") String filecreatetime,
                                                       @Param("reserveLocation") String reserveLocation, @Param("beforeNum") String beforeNum,
                                                       @Param("archiveDate") String archiveDate, @Param("archiveNum") String archiveNum,
                                                       @Param("remark") String remark, @Param("deptid") long deptid,
                                                       @Param("filenum") String filenum, @AccessKey @Param("documentLocalId") String documentLocalId,
                                                       @Param("documentCreateTime") String documentCreateTime,
                                                       @Param("recordFlag") Boolean recordFlag) {
    Documents document = new Documents();
    document.setFileName(title);
    document.setTitle(title);
    document.setDeptId(deptid);
    document.setId(fileId);
    document.setArrangeFlag(true);
    document.setFileNum(fileCode);
    document.setDocumentLocalId(documentLocalId);
    document.setDocumentCreateTime(documentCreateTime);
    document.setThemeWord(themeWord);
    document.setResponsible(responsible);
    document.setFondsId(fondsId);
    document.setImportance(importance);
    document.setAppendices(appendices);
    document.setArchiveType(archiveType);
    document.setComeOffice(comeOffice);
    document.setBeloadDepartment(beloadDepartment);
    document.setArchiveYear(archiveYear);
    document.setReserveDuration(reserveDuration);
    document.setSaveNum(saveName);
    document.setFileCreatetime(filecreatetime);
    document.setReserveLocation(reserveLocation);
    document.setBeforeNum(beforeNum);
    document.setArchiveDate(archiveDate);
    document.setArchiveNum(archiveNum);
    document.setRemark(remark);
    document.setRecordFlag(recordFlag);
    documentService.saveDocumentDetailInfo(document);

    return buildEntity(APIEntity.create(document), HttpStatus.OK);
  }*/

    @OptionLogger(objectType = "文档",type = OpType.DEL,description = "文件中心删除")
    @RequestMapping(value = "documents", method = RequestMethod.PUT)
    public HttpEntity<APIEntity<Long>> deleteDocument(@MultiAccessKey@RequestParam("ids[]") List<Long> ids) {
        int row = documentService.deleteDocument(ids);
        return buildEntity(APIEntity.create(null), row > 0 ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE);
  }

  @OptionLogger(objectType = "文档", type = OpType.DEL, description = "删除正文/文件")
  @RequestMapping(value = "documents/files", method = RequestMethod.POST)
  public HttpEntity<APIEntity> deleteFile(@RequestParam("id") Long fileId, @RequestParam("location") String location, @AccessKey @RequestParam("docId") Long docId) {
    boolean bool = documentService.deleteFile(fileId, location, docId);
    return buildEntity(APIEntity.create(null), bool ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
  }

  //双击打开文件接口
  @OptionLogger(objectType = "文档",type = OpType.DETAIL,description = "打开文件")
  @RequestMapping(value = "files/{docId}", method = RequestMethod.GET)
  public HttpEntity<APIEntity<List<Files>>> selectFile(@AccessKey @PathVariable String docId) {
    return buildEntity(APIEntity.create(documentService.selectFile(docId)));
  }

    @OptionLogger(objectType = "文档", type = OpType.ADD, description = "创建文件")
  @RequestMapping(value = "documents/file/{deptId}", method = RequestMethod.POST)
    public HttpEntity<APIEntity<Documents>> uploadDocument(@PathVariable Long deptId,
                                                           Documents currentDocument, @SessionAttribute(WebSecurityConfig.SESSION_KEY) User user) {
        currentDocument.setFileName(currentDocument.getTitle());
    currentDocument.setDeptId(deptId);
        currentDocument.setArrangeFlag(false);
        currentDocument.setUserId(user.getId());
        currentDocument.setDataSource(user.getName());
    String documentLocalId = UUID.randomUUID().toString();
        // currentDocument.setBeloadDepartment(deptName);
    //String filePath = archiveProperties.getFileStorage()+"/documents/"+documentLocalId;
    // File documentFile = new File(filePath);
    // if(!documentFile.exists()){
    //   documentFile.mkdirs();
    // }

    currentDocument.setDocumentLocalId(documentLocalId);
    // currentDocument.setSavaLocation(filePath);
    Timestamp d = new Timestamp(System.currentTimeMillis());
        currentDocument.setDocumentCreatime(d.toString());
    documentService.uploadDocument(currentDocument);
    return buildEntity(APIEntity.create(currentDocument));
  }

  //查看卷宗
  @OptionLogger(objectType = "部门",type = OpType.DETAIL,description = "查看案卷")
  @RequestMapping(value = "documents/records/{deptId}", method = RequestMethod.GET)
  public HttpEntity<APIEntity<List<Documents>>> selectComplete(@AccessKey @PathVariable("deptId") Long deptId,
                                                               @Param("imprortant") String imprortant) {
    return buildEntity(APIEntity.create(documentService.selectComplete(deptId, imprortant)));
  }

//    @RequestMapping(value = "files/upload/{docId}",method = RequestMethod.POST)
//    public HttpEntity<APIEntity<Files>>uploadFiles(@PathVariable String docId,@Param("fileType")String fileType,
//                                                     @Param("location")String location,
//                                                     @Param("uploadDate")String uploadDate){
//        Files files = new Files();
//        files.setDocId(docId);
//        files.setFileType(fileType);
//        files.setLocation(location);
//        documentService.uploadFiles(files);
//        return buildEntity(APIEntity.create(null),HttpStatus.OK);
//    }

  @OptionLogger(objectType = "案卷",type = OpType.DETAIL,description = "查看案卷下的文件")
  @RequestMapping(value = "documents/lookrecords/{recordId}/type/{archiveType}", method = RequestMethod.GET)
  public HttpEntity<APIEntity<PageInfo>> lookRecordDocument(
          @AccessKey @PathVariable("recordId") Long recordId, @PathVariable("archiveType") String archiveType, PageSortSearch pageSortSearch) {
      return buildEntity(APIEntity.create(documentService.selectFromRecord(recordId, archiveType, pageSortSearch)));
  }

  @RequestMapping(value = "documents/search/{deptId}", method = RequestMethod.GET)
  public HttpEntity<APIEntity<PageInfo>> searchDocumentList(@PathVariable("deptId") Long deptId,
      @Param("title") String title, @Param("fileCode") String fileCode, @Param("offset") int offset,
      @Param("limit") int limit) {
      Documents document = new Documents();
    document.setFileName(title);
    document.setTitle(title);
    document.setDeptId(deptId);
    // document.setId(fileId);
    document.setFileNum(fileCode);
    // document.setRecordFlag(recordFlag);
    return buildEntity(
        APIEntity.create(documentService.searchDocumentList(document, offset, limit)));
  }



  @RequestMapping(value = "documents/searchGlobal", method = RequestMethod.GET)
  public HttpEntity<APIEntity> searchGlobalDocumentList(
          @Param("title") String title,
          @Param("fileCode") String fileCode,
          @Param("option") String option,
          @Param("archiveYear") String archiveYear,
          @Param("archiveType") String archiveType,
          @RequestParam(value = "pageNum", required = false, defaultValue = "1") int offset,
          @RequestParam(value = "pageSize", required = false, defaultValue = "10000") int limit,
          @SessionAttribute(WebSecurityConfig.SESSION_KEY)User contextUser) {

      Documents document = new Documents();
    document.setTitle(title);
    document.setFileNum(fileCode);
    document.setOption(option);
    System.out.println("           archiveType   "+archiveType);
    if(archiveType!=null&&!"".equals(archiveType)) {
    	if(archiveType.contains("ALL")) {
    		archiveType="ALL";
    	}
    }
    document.setArchiveType(archiveType);
    document.setArchiveYear(archiveYear);
    return buildEntity(
        APIEntity.create(documentService.searchGlobalDocumentList(document, offset, limit,contextUser)));
  }

  @OptionLogger(objectType = "文档",type = OpType.UPDATE,description = "上传正文/附件")
  @RequestMapping("/fileUpload")
  // @ResponseBody
  public HttpEntity<APIEntity<String>> upload(@AccessKey@Param("docId") String docId, HttpServletRequest request, HttpServletResponse response)
      throws IllegalStateException, IOException {

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

          DocumentsExample documentsExample = new DocumentsExample();
          documentsExample.createCriteria().andDocumentLocalIdEqualTo(docId).andRecordFlagEqualTo("00");
          Documents documents = documentsMapper.selectByExample(documentsExample).get(0);
          if(documents.getArrangeFlag()||documents.getIsArchive()){
            files.setRecordFlag(true);
            files.setArchiveYear(String.valueOf(LocalDate.now().getYear()));
            files.setArrangedBy(documents.getArrangedBy());
              documentService.indexFileToES(files, path);
          }
        }
        //记录上传该文件后的时间
        int finaltime = (int) System.currentTimeMillis();
        System.out.println(finaltime - pre);
      }

    }
    return buildEntity(APIEntity.create(docId),HttpStatus.ACCEPTED);
  }



  //文件下载相关代码
  @OptionLogger(objectType = "文件",type = OpType.DOWNLOAD,description = "下载")
  @RequestMapping("/fileDownload")
  public String downloadFile(@AccessKey @RequestParam("fileId")String fileId , org.apache.catalina.servlet4preview.http.HttpServletRequest request,
                             HttpServletResponse response) throws IOException, DocumentException {
    System.out.println("insert     fileDownload    aaaaa   ");
      String filePath = archiveProperties.getFileStorage() + "/" + request.getParameter("localtion");
      String fileName = URLDecoder.decode(request.getParameter("fileName"), "UTF-8");
    // String path = filePath + "/" + fileName;
    if (fileName != null) {
      //当前是从该工程的WEB-INF//File//下获取文件(该目录可以在下面一行代码配置)然后下载到C:\\users\\downloads即本机的默认下载的目录
      fileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".pdf";
//            String realPath = request.getServletContext().getRealPath(
//                    "//WEB-INF//");
      String basePath = filePath.substring(0,filePath.lastIndexOf("."));
      String waterMarkPath = basePath+"_wm.pdf";
      File waterMarkFile = new File(waterMarkPath);
      if(!waterMarkFile.exists()){
        String pdfPath = basePath+".pdf";
        if(new File(pdfPath).exists()){
          LOGGER.debug("PDF 加水印中。。。");
          WaterMarkUtil.addWaterMark(pdfPath, waterMarkPath,
                  //根据需要创建不同的实例传入即可  必须都是WaterMarkRunner的子类
                  new WaterMarkTwoLineRunner(WaterMarkUtil.getFont(waterMarkPosition),waterMarkText)) ;
          LOGGER.debug("PDF 加水印结束。。。");
        }
      }
      File file = new File(waterMarkPath);
      if (file.exists()) {
        //获得浏览器代理信息
        final String userAgent = request.getHeader("USER-AGENT");
        if (StringUtils.contains(userAgent, "MSIE") || StringUtils
            .contains(userAgent, "Trident")) {//IE浏览器
          try {
            fileName = URLEncoder.encode(fileName, "UTF8");
          } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
          }
          System.out.println("IE浏览器");
        } else if (StringUtils.contains(userAgent, "Mozilla")) {//google,火狐浏览器
          try {
            fileName = new String(fileName.getBytes(), "ISO8859-1");
          } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
          }
        } else {
          try {
            fileName = URLEncoder.encode(fileName, "UTF8");//其他浏览器
          } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
          }
        }
        response.setContentType("application/force-download");// 设置强制下载不打开
        response.addHeader("Content-Disposition",
            "attachment;fileName=" + fileName);// 设置文件名
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
          fis = new FileInputStream(file);
          bis = new BufferedInputStream(fis);
          OutputStream os = response.getOutputStream();
          int i = bis.read(buffer);
          while (i != -1) {
            os.write(buffer, 0, i);
            i = bis.read(buffer);
          }
          System.out.println("success");
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          if (bis != null) {
            try {
              bis.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
          if (fis != null) {
            try {
              fis.close();
            } catch (IOException e) {
              e.printStackTrace();
            }

          }
        }
      }
    }
    return null;
  }


  //文件下载相关代码
  @OptionLogger(objectType = "文件",type = OpType.DOWNLOAD,description = "下载zip")
  @RequestMapping("/fileZipDownload")
  public String downloadZip(@RequestParam("localtion")String localPath,@AccessKey@RequestParam("fileId")String fileId, org.apache.catalina.servlet4preview.http.HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    //压缩文件初始设置
    String path = archiveProperties.getFileStorage();
    String base_name = request.getParameter("fileName");
    localPath = request.getParameter("localtion");
    // fileId = request.getParameter("fileId");
    String fileZip = base_name + ".zip"; // 拼接zip文件S
    String filePath = path + "\\" + fileZip;//之后用来生成zip文件

    List<Files> files = new ArrayList<>();
    if (localPath.equals("wenJian")) {
      files = documentService.selectFile(fileId);
    } else if (localPath.equals("record")) {

    }
    //filePathArr为根据前台传过来的信息，通过数据库查询所得出的pdf文件路径集合（具体到后缀），此处省略
    //files = new File[fileNameArr.size()];/
//        File file = new File(localPath);
//        File[] sourceFiles = file.listFiles();
//        for(int i=0;i<fileNameArr.size();i++){
//            files[i]=new File(fileNameArr.get(i).get("filePath"));//获取所有需要下载的pdf
//        }

    // 创建临时压缩文件
    try {
      BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
      ZipOutputStream zos = new ZipOutputStream(bos);
      ZipEntry ze = null;
      for (int i = 0; i < files.size(); i++) {//将所有需要下载的pdf文件都写入临时zip文件
        Files files1 = files.get(i);
        File file = new File(files1.getLocation());
        if (file.exists()) {
          BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
          ze = new ZipEntry(file.getName());
          zos.putNextEntry(ze);
          int s = -1;
          while ((s = bis.read()) != -1) {
            zos.write(s);
          }
          bis.close();
        }

      }
      zos.flush();
      zos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    //以上，临时压缩文件创建完成

    //进行浏览器下载
    //获得浏览器代理信息
    final String userAgent = request.getHeader("USER-AGENT");
    //判断浏览器代理并分别设置响应给浏览器的编码格式
    String finalFileName = null;
    if (StringUtils.contains(userAgent, "MSIE") || StringUtils
        .contains(userAgent, "Trident")) {//IE浏览器
      finalFileName = URLEncoder.encode(fileZip, "UTF8");
      System.out.println("IE浏览器");
    } else if (StringUtils.contains(userAgent, "Mozilla")) {//google,火狐浏览器
      finalFileName = new String(fileZip.getBytes(), "ISO8859-1");
    } else {
      finalFileName = URLEncoder.encode(fileZip, "UTF8");//其他浏览器
    }
    response.setContentType("application/x-download");//告知浏览器下载文件，而不是直接打开，浏览器默认为打开
    response
        .setHeader("Content-Disposition", "attachment;filename=\"" + finalFileName + "\"");//下载文件的名称

    ServletOutputStream servletOutputStream = response.getOutputStream();
    DataOutputStream temps = new DataOutputStream(
        servletOutputStream);

    DataInputStream in = new DataInputStream(
        new FileInputStream(filePath));//浏览器下载文件的路径
    byte[] b = new byte[2048];
    File reportZip = new File(filePath);//之后用来删除临时压缩文件
    try {
      while ((in.read(b)) != -1) {
        temps.write(b);
      }
      temps.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (temps != null) {
        temps.close();
      }
      if (in != null) {
        in.close();
      }
      if (reportZip != null) {
        reportZip.delete();//删除服务器本地产生的临时压缩文件
      }
      servletOutputStream.close();
    }
    return null;
  }

  @RequestMapping("/document/destroy")
  public HttpEntity<APIEntity> destroyReelNumByType(@RequestParam("flowId") Long flowId, @RequestParam("reelNum") String reelNum, @RequestParam("reelType") String reelType) {
    documentService.destroyReelNumByType(flowId, reelNum, reelType);
    return buildEntity(APIEntity.create(null),HttpStatus.OK);
  }


  @RequestMapping("/document/{reelNum}/valid/{reelType}")
  public HttpEntity<APIEntity> validReelNumByReelType(@PathVariable("reelNum") String reelNum, @PathVariable("reelType") String reelType) {
    Long id = documentService.getIdByReelNumAndType(reelNum, reelType);
    return buildEntity(APIEntity.create(id == null ? false : true), HttpStatus.OK);
  }

  @RequestMapping(value = "/documents/{deptId}/recycle/{type}",method = RequestMethod.GET)
  public HttpEntity<APIEntity<PageInfo>>  getRecycleBin(@PathVariable("deptId") Long deptId,@PathVariable("type") int type,
                                                        @RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize,
                                                        @RequestParam(value = "sortName" ,required = false)String sortName,@RequestParam("sortOrder")String sortOrder,
                                                        @SessionAttribute(WebSecurityConfig.SESSION_KEY)User user){
    return buildEntity(APIEntity.create(documentService.getRecycleBin(deptId, type,pageNum,pageSize,sortName,sortOrder,user)), HttpStatus.OK);
  }

  @OptionLogger(objectType = "文档",type = OpType.UPDATE,description = "回收站恢复")
  @RequestMapping(value = "/documents/recover",method = RequestMethod.PUT)
  public HttpEntity<APIEntity> recover(@MultiAccessKey@RequestParam("ids[]") List<Long> ids){
    return buildEntity(APIEntity.create(documentService.recover(ids)), HttpStatus.OK);
  }

  @OptionLogger(objectType = "文档",type = OpType.DEL,description = "回收站删除")
  @RequestMapping(value = "/documents/remove",method = RequestMethod.PUT)
  public HttpEntity<APIEntity> remove(@MultiAccessKey@RequestParam("ids[]") List<Long> ids){
    return buildEntity(APIEntity.create(documentService.remove(ids)), HttpStatus.OK);
  }

}

