package org.quetzaco.archives.web.restful;

import org.quetzaco.archives.application.biz.HomePictureService;
import org.quetzaco.archives.model.Files;
import org.quetzaco.archives.model.HomePicture;
import org.quetzaco.archives.model.api.APIEntity;
import org.quetzaco.archives.util.config.ArchiveProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
public class HomePictureController extends BaseRestContoller {

    @Autowired
    ArchiveProperties archiveProperties;
    @Autowired
    HomePictureService homePictureService;

    @Value("${cbs.absPath}")
    private String mImagesPath;

    @RequestMapping(value = "/homePictures/upload")
    public HttpEntity<APIEntity> uploadPicture(@RequestParam("content") String content, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Calendar now = Calendar.getInstance();
        String reserveLocation =
                mImagesPath;
        File reserveLocationFile = new File(reserveLocation);
        if (!reserveLocationFile.exists()) {
            reserveLocationFile.mkdirs();
        }
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //新建文件

        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {

            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //取得request中的所有文件名

            Map<String, MultipartFile> map = multiRequest.getFileMap();

            for (MultipartFile file : map.values()) {

                //记录上传过程起始时的时间，用来计算上传时间
                long pre = System.currentTimeMillis();
                Files files = new Files();
                if (file != null) {
                    //取得当前上传文件的文件名称
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    //重命名上传后的文件名
                    String fileName = file.getOriginalFilename();
                    String suffixName = fileName.substring(fileName.lastIndexOf("."));
                    //定义上传路径
                    String fileId = UUID.randomUUID().toString();
                    String path = reserveLocation + fileId + suffixName;
                    File localFile = new File(path);
                    file.transferTo(localFile);
                    HomePicture homePicture = new HomePicture();
                    homePicture.setCreatedDt(new Date());
                    homePicture.setContent(content);
                    homePicture.setUpdatedDt(new Date());
                    homePicture.setLocation(fileId + suffixName);

                    homePictureService.uploadPicture(homePicture);
                }

                //记录上传该文件后的时间
                int finaltime = (int) System.currentTimeMillis();
                System.out.println(finaltime - pre);
            }

        }
        return buildEntity(APIEntity.create(null), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/homePictures", method = RequestMethod.GET)
    public HttpEntity<APIEntity> getPicture() {
        List<HomePicture> list = homePictureService.getPicture();
        for (HomePicture homePicture : list) {
            String location = homePicture.getLocation();
            homePicture.setLocation("images/" + location);
        }
        return buildEntity(APIEntity.create(list), HttpStatus.OK);
    }
}
