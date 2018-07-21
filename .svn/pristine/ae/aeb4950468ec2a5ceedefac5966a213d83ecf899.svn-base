package org.quetzaco.archives.application.dao;

import org.apache.ibatis.annotations.Param;
import org.quetzaco.archives.model.MainDoc;
import org.quetzaco.archives.model.MainDocExample;

import java.util.List;

public interface MainDocMapper {
    long countByExample(MainDocExample example);

    int deleteByExample(MainDocExample example);

    int insert(MainDoc record);

    int insertSelective(MainDoc record);

    List<MainDoc> selectByExample(MainDocExample example);

    int updateByExampleSelective(@Param("record") MainDoc record, @Param("example") MainDocExample example);

    int updateByExample(@Param("record") MainDoc record, @Param("example") MainDocExample example);

    int insertToDocs(@Param("usrId") Long usrId, @Param("deptId") Long deptId);

    int syncAppendies();
}