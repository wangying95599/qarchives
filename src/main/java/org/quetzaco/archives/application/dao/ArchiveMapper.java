package org.quetzaco.archives.application.dao;

import org.apache.ibatis.annotations.Param;
import org.quetzaco.archives.model.Archive;
import org.quetzaco.archives.model.Record;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchiveMapper {

  int deleteByPrimaryKey(Long id);

  int insert(Archive record);

  int insertSelective(Archive record);

  Archive selectByPrimaryKey(Long id);

  int updateByPrimaryKeySelective(Archive record);

  int updateByPrimaryKey(Archive record);


  List<Archive> selectArchives(Long deptId);

  void createArchive(Archive archive);

  Archive selectArchiveDetial(Long archiveId);

  List<Record> selectRecordFromArchive(Long archiveId);

 // Archive selectByFileCode(String reelNum);
  //将案卷归档到档案里面
  void insertRecordToArchive(@Param("archiveId")Long archiveId, @Param("recordId") Long recordId);

  //获取归档到全宗的档案
  List<Archive>selectArchiveFromBox(Long boxId);

  //更新
    void archiveToArchive(Long archiveId);

    List<Archive>searchArchiveList(Archive archive);

    //全局搜素
    List<Archive>searchGlobalArchiveList(Archive archive);

    List<Archive>selectArchiveArchive(@Param("deptId")Long deptId,@Param("important")String important);

    Archive selectByFileCode(@Param("reelNum") String reelNum,@Param("recordFlag") boolean recordFlag);

    int removeRecordFromArchive(@Param("archiveId") Long archiveId, @Param("recordId") Long recordId);

    void boxToArchive(Long archiveId);

    Record isDuplicateTitle(@Param("archiveId") Long archiveId, @Param("title") String title);
}