package org.quetzaco.archives.application.dao;

import org.apache.ibatis.annotations.Param;
import org.quetzaco.archives.model.DocFiles;
import org.quetzaco.archives.model.DocFilesExample;

import java.util.List;

public interface DocFilesMapper {
    long countByExample(DocFilesExample example);

    int deleteByExample(DocFilesExample example);

    int insert(DocFiles record);

    int insertSelective(DocFiles record);

    List<DocFiles> selectByExample(DocFilesExample example);

    int updateByExampleSelective(@Param("record") DocFiles record, @Param("example") DocFilesExample example);

    int updateByExample(@Param("record") DocFiles record, @Param("example") DocFilesExample example);

    int insertToFiles();

    int updateFlag();
}