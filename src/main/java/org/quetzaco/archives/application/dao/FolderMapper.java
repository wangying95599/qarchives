package org.quetzaco.archives.application.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.quetzaco.archives.model.Folder;
import org.quetzaco.archives.model.FolderExample;

public interface FolderMapper {
    long countByExample(FolderExample example);

    int deleteByExample(FolderExample example);

    int insert(Folder record);

    int insertSelective(Folder record);

    List<Folder> selectByExample(FolderExample example);

    int updateByExampleSelective(@Param("record") Folder record, @Param("example") FolderExample example);

    int updateByExample(@Param("record") Folder record, @Param("example") FolderExample example);
    
    
    
    @Select({"select id from folder where id in (select distinct parent_id from folder) "})
    List<String> getHaveChildDept();
}