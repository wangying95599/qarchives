package org.quetzaco.archives.application.biz;

import com.github.pagehelper.PageInfo;
import org.quetzaco.archives.model.*;

import java.util.List;
import java.util.Map;

/**
 * Created by deya on 2017/7/13.
 */
public interface DocumentService {
    PageInfo selectFileList(int type, Long deptId, int offset, int limit, String sortName, String sortOrder, String title, String startYear, String endYear,User user);

   // Document selectDocumentDetail(Long fildId);
   Documents selectDocumentByFileCode(String fileCode);

    void saveDocumentDetailInfo(Documents document,User contextUser);

    void addArchiveTypeAndDateToFile(Documents documents, Files files);

    int deleteDocument(List<Long> fileIds);

    List<Files> selectFile(String docId);

    //上传文件
    public int uploadDocument(Documents document);

    //上传附件正文
    void uploadFiles(Files files);

    //归档文件
    List<Documents> selectComplete(Long deptId, String important);

    //查看已经进入案卷的文档
    PageInfo selectFromRecord(Long recordId, String archiveType, PageSortSearch pageSortSearch);

    //查询文件
    PageInfo searchDocumentList(Documents document, int offset, int limit);

    Map searchGlobalDocumentList(Documents document, int offset, int limit, User contextUser);

 void destroyReelNumByType(Long flowId, String reelNum, String reelType);

 Long getIdByReelNumAndType(String reelNum, String reelType);

 boolean deleteFile(Long fileId, String location, Long docId);

    boolean validReelNumByReelType(String reelNum, String reelType);

    void ConvertFileToSwf(String filePath);

    boolean ConvertFileToSwf1(String filePath);

    void batchModify(List<Long> ids, Documents documents);

    PageInfo searchFileList(BatchDocuments batchDocuments,User user);

    void saveDocumentDetailInfo1(Documents documents);

    List<Documents> getDocumentsByIds(List<Long> ids);

    PageInfo getRecycleBin(Long deptId, int type, int pageNum, int pageSize,String sortName,String sortOrder,User user);

    int recover(List<Long> ids);

    int remove(List<Long> ids);

    int turnOver(Long flowId,Long deptId,List<Documents> documentsList);

    boolean indexFileToES(Files file,String path);
}
