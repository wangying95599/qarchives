package org.quetzaco.archives.application.dao;

import org.apache.ibatis.annotations.Param;
import org.quetzaco.archives.model.Documents;
import org.quetzaco.archives.model.DocumentsExample;
import org.quetzaco.archives.model.Files;

import java.util.List;

public interface DocumentsMapper {
    long countByExample(DocumentsExample example);

    int deleteByExample(DocumentsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Documents record);

    int insertSelective(Documents record);

    List<Documents> selectByExample(DocumentsExample example);

    Documents selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Documents record, @Param("example") DocumentsExample example);

    int updateByExample(@Param("record") Documents record, @Param("example") DocumentsExample example);

    int updateByPrimaryKeySelective(Documents record);

    int updateByPrimaryKey(Documents record);

    List<Documents> selectFileList(Long deptId);

    // Document selectDocumentDetail(Long fildId);

    void saveDocumentDetailInfo(Documents document);

    int deleteDocument(Long fileId);

    //修改详细信息
    // Document modifyDocumentDetailInfo(Document document);

    //上传文件
    int uploadDocument(Documents document);

    //搜索文档
    List<Documents> searchDocumentList(Documents document);

    Documents selectDocumentByFileCode(@Param("fileNum") String fileNum, @Param("recordFlag") String recordFlag);

    //查看归档文件
    List<Documents> selectComplete(@Param("deptId") Long deptId, @Param("important") String important);

    //查看可以归档的文件
    List<Documents> selectFromRecord(@Param("recordId") Long recordId, @Param("order") String order,@Param("isArrange")boolean isArrange,@Param("usrId")Long usrId);

    List<Documents> acousticFromRecord(@Param("recordId") Long recordId, @Param("order") String order,@Param("isArrange")boolean isArrange,@Param("usrId")Long usrId);


    //更新归档以后的状体
    void documentToRecord(Long docId);

    //全局搜素
    List<Documents> searchGlobalDocumentList(Documents document);


    void destroyReelNumByType(@Param("tableName") String documents, @Param("recordFlag") String recordFlag, @Param("reelNum") String reelNum);

    Long getIdByReelNumAndType(@Param("tableName") String documents, @Param("id") String document_local_id, @Param("colName") String file_code, @Param("reelNum") String reelNum);

    int deleteFile(Long fileId);

    int recordToDocument(Long docId);

    List<Files> isDuplicateFileName(@Param("docId") String docId, @Param("dupBaseName") String dupBaseName, @Param("fileName") String fileName);

    int updateByPrimaryKeySelective1(Documents documents);
    
    List<Documents> searchDocumentListByIds(@Param("flowId")Long flowId);

    Files getDeptIdBylocalId(String fileId);

    List<String> getSubFidBydocIds(@Param("docIds") List<Long> docIds);

    int updateArrangedBy(@Param("docId")Long docId,@Param("arrangedBy")Long arrangedBy);
}