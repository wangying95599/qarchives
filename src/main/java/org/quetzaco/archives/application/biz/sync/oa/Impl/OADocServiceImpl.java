package org.quetzaco.archives.application.biz.sync.oa.Impl;

import org.quetzaco.archives.application.biz.DocumentService;
import org.quetzaco.archives.application.biz.sync.oa.OADocService;
import org.quetzaco.archives.application.dao.DocFilesMapper;
import org.quetzaco.archives.application.dao.FileMapper;
import org.quetzaco.archives.application.dao.MainDocMapper;
import org.quetzaco.archives.model.*;
import org.quetzaco.archives.util.runnable.TaskConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.UUID;

@Service
public class OADocServiceImpl implements OADocService {

    @Autowired
    MainDocMapper mainDocMapper;
    @Autowired
    DocFilesMapper docFilesMapper;
    @Autowired
    FileMapper fileMapper;
    @Value("${quetzaco.archive.config.file-storage}")
    private String prefixPath;
    @Value("${oa.prefix}")
    private String oaPrefixPath;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    TaskConsumer taskConsumer;
    @Autowired
    DocumentService documentService;

    @Value("${CONVERT_DOCUMENT_FORMAT}")
    String docFormat;
    @Value("${kmhk.userId}")
    Long usrId;
    @Value("${kmhk.deptId}")
    Long deptId;

    final static Logger LOGGER = LoggerFactory.getLogger(OADocServiceImpl.class);

    @Override
    public int syncDoc() {
        int a = mainDocMapper.insertToDocs(usrId, deptId);
        MainDoc mainDoc = new MainDoc();
        mainDoc.setUpdateFlag("ED");
        MainDocExample mainDocExample = new MainDocExample();
        mainDocExample.createCriteria().andUpdateFlagEqualTo("OA");
        mainDocMapper.updateByExampleSelective(mainDoc, mainDocExample);
        mainDocMapper.syncAppendies();
        return a;
    }

    @Override
    public int syncFiles() {
        int b = 0;
        try {
            b = docFilesMapper.insertToFiles();
            LOGGER.debug("sync files postgres data ");
            List<DocFiles> docFilesList = getOaLocation();
            LOGGER.debug("sync entity files start ");
            copyFiles(docFilesList);
            LOGGER.debug("sync entity files end");
            DocFiles docFiles = new DocFiles();
            docFiles.setUpdateFlag("ED");
            DocFilesExample docFilesExample = new DocFilesExample();
            docFilesExample.createCriteria().andUpdateFlagEqualTo("oa");
            docFilesMapper.updateByExampleSelective(docFiles, docFilesExample);
        } catch (Exception e) {
            e.printStackTrace();
        }

        fileMapper.updateFileType("accessory", "attachment");
        fileMapper.updateFileType("main", "document");
        LOGGER.debug("sync count:"+b);
        return b;
    }

    public void copyFiles(List<DocFiles> docFiles) {
        int pdfCount = 1;
        redisTemplate.opsForValue().set("isCopyComplete", false);
        redisTemplate.delete("task-queue");
        for (DocFiles docFiles1 : docFiles) {
            if (docFiles1.getRealPath() == null && docFiles1.getRealFilename() == null) {
                handleSyncError(docFiles1);
                continue;
            }
            String string = docFiles1.getRealPath() + "/" + docFiles1.getRealFilename();
            File source = new File(oaPrefixPath + "/" + string);

            if (!source.exists())
                continue;
            String dir = prefixPath + "/" + string.substring(0,
                    string.lastIndexOf("/"));

            if (!new File(dir).exists()) {
                (new File(dir)).mkdirs();
            }

            File dest = new File(prefixPath + "/" + string);
            if (dest.exists())
                continue;
            try {
                copyFileUsingFileChannels(source, dest);
                //oa同步的文档建立index
                /*Files files =  new Files();
                files.setFileId(docFiles1.getFid());
                files.setDocId(docFiles1.getMid());
                files.setFileType("document".equals(docFiles1.getFileType())?"main":"accessory");
                files.setUploadDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(docFiles1.getCreatedDt()));
                files.setFileName(docFiles1.getFileName());
                files.setLocation(string);
                documentService.indexFileToES(files, prefixPath + "/" + string);*/

                String suffixPath = dest.getPath().substring(dest.getPath().lastIndexOf(".") + 1);
                if (useLoop(suffixPath)) {
                    redisTemplate.opsForList().leftPush("task-queue", docFiles1);
                    if (pdfCount > 0) {
                        taskConsumer.start();
                        pdfCount--;
                    }
                }
                System.out.println("task-queue 插入" + dest);
            } catch (IOException e) {
                handleSyncError(docFiles1);
                e.printStackTrace();
            }
        }
        redisTemplate.opsForValue().set("isCopyComplete", true);
    }

    @Override
    public void copyAndTranslateFile(Long fileId) {
        Files files = fileMapper.selectByPrimaryKey(fileId);
        if (files == null || files.getLocation() == null) return;
        String location = files.getLocation();
        File localFile = new File(prefixPath + '/' + location);
        String oaDir = oaPrefixPath + '/' + location.substring(0, location.lastIndexOf('/'));
        if (!localFile.exists()) {
            File oaFile = new File(oaPrefixPath+'/'+location);
            if(!oaFile.exists()){
                String command = "convmv -f GBK -t UTF-8 --notest -r "+oaDir;
                LOGGER.debug("oaDir:"+oaDir);
                try {
                    Process process = Runtime.getRuntime().exec(command);
                    LOGGER.debug("start run cmd="+command);
                    process.waitFor();
                    LOGGER.debug("finish run cmd="+command);
                    oaFile = new File(oaPrefixPath + '/' + location);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (!oaFile.exists()) {
                LOGGER.debug("OA文件不存在");
                return;
            }
            String dir = prefixPath + '/' + location.substring(0, location.lastIndexOf('/'));
            if (!new File(dir).exists()) {
                new File(dir).mkdirs();
            }
            try {
                copyFileUsingFileChannels(oaFile, localFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        documentService.ConvertFileToSwf1(localFile.getPath());
    }

    private boolean useLoop(String targetValue) {
        String[] arr = docFormat.split(",");
        for (String s : arr) {
            if (s.equals(targetValue))
                return true;
        }
        return false;
    }

    private void handleSyncError(DocFiles docFiles1) {
        DocFiles df = new DocFiles();
        df.setUpdateFlag("error");
        DocFilesExample docFilesExample = new DocFilesExample();
        docFilesExample.createCriteria().andFidEqualTo(docFiles1.getFid());
        docFilesMapper.updateByExampleSelective(df, docFilesExample);
        fileMapper.updateFile(docFiles1.getFid());
        LOGGER.debug("HANDLESYNCERROR  :"+docFiles1.getFid());
    }

    private List<DocFiles> getOaLocation() {
        DocFilesExample docFilesExample = new DocFilesExample();
        docFilesExample.createCriteria().andUpdateFlagEqualTo("oa");
        List<DocFiles> docFiles = docFilesMapper.selectByExample(docFilesExample);
        for (DocFiles docFiles1 : docFiles) {
            String mid = docFiles1.getMid();
            String fid = docFiles1.getFid();
            if(mid!=null&&fid!=null&&mid.equals(fid)){
                String fileName = docFiles1.getFileName();

                // DocFiles repeatDocFiles = new DocFiles();
                String newFid = UUID.randomUUID().toString();
                /*repeatDocFiles.setFid(newFid);
                DocFilesExample repeatDocFilesExample = new DocFilesExample();
                repeatDocFilesExample.createCriteria().andFidEqualTo(fid).andFileNameEqualTo(fileName);
                docFilesMapper.updateByExampleSelective(repeatDocFiles, repeatDocFilesExample);*/
                Files repeatFiles = new Files();
                repeatFiles.setFileId(newFid);
                FileExample rpFileExample = new FileExample();
                rpFileExample.createCriteria().andFileIdEqualTo(fid).andFileNameEqualTo(fileName);
                fileMapper.updateByExampleSelective(repeatFiles, rpFileExample);
                docFiles1.setFid(newFid);
            }
        }
        return docFiles;
    }


    private static void copyFileUsingFileChannels(File source, File dest)
            throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }


    @Transactional
    @Scheduled(cron = "${cron.docSync}")
    protected void syncDocAndFile() {
        syncDoc();
        updateFlag();
        syncFiles();
    }

    public static String getUTF8StringFromGBKString(String gbkStr) {
        try {
            return new String(getUTF8BytesFromGBKString(gbkStr), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new InternalError();
        }
    }

    public static byte[] getUTF8BytesFromGBKString(String gbkStr) {
        int n = gbkStr.length();
        byte[] utfBytes = new byte[3 * n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            int m = gbkStr.charAt(i);
            if (m < 128 && m >= 0) {
                utfBytes[k++] = (byte) m;
                continue;
            }
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
        }
        if (k < utfBytes.length) {
            byte[] tmp = new byte[k];
            System.arraycopy(utfBytes, 0, tmp, 0, k);
            return tmp;
        }
        return utfBytes;

    }

    //同步doc_files 前先过滤掉files表中已有的，未改变update_flag状态的
    private int updateFlag(){
        int count = docFilesMapper.updateFlag();
        return  count;
    }

}
