package org.quetzaco.archives.application.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.quetzaco.archives.model.FileExample;
import org.quetzaco.archives.model.Files;

public interface FileMapper {
    long countByExample(FileExample example);

    int deleteByExample(FileExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Files record);

    int insertSelective(Files record);

    List<Files> selectByExample(FileExample example);

    Files selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Files record, @Param("example") FileExample example);

    int updateByExample(@Param("record") Files record, @Param("example") FileExample example);

    int updateByPrimaryKeySelective(Files record);

    int updateByPrimaryKey(Files record);

    List<Files> selectFile(String docId);

    void uploadFiles(Files files);

    int deleteFile(Long fileId);

    int updateMainByDocId(String docId);

    int updateFileType(@Param("doc") String docFileType, @Param("oa") String oaFileType);

    int updateFile(String fileId);
}