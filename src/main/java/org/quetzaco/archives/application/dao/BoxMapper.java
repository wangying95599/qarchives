package org.quetzaco.archives.application.dao;

import org.apache.ibatis.annotations.Param;
import org.quetzaco.archives.model.Archive;
import org.quetzaco.archives.model.Box;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BoxMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Box record);

    int insertSelective(Box record);

    Box selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Box record);

    int updateByPrimaryKey(Box record);

    List<Box> selectBoxs (Long deptId);

    void createBox(Box box);

    Box selectBoxDetial(Long boxId);

    List<Archive> selectArchiveFromBox(Long boxId);

    void insertArchiveToBox(@Param("boxId")Long boxId, @Param("archiveId") Long archiveId);

    List<Box>searchBoxList(Box box);

    List<Box>searchGlobalBoxList(Box box);

    Box selectByFileCode(@Param("reelNum") String reelNum, @Param("recordFlag") boolean b);

    int removeArchiveFromBox(@Param("boxId") Long boxId, @Param("archiveId") Long archiveId);

    Archive isDuplicateTitle(@Param("boxId") Long boxId, @Param("title") String title);
}