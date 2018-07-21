package org.quetzaco.archives.application.dao;

import org.apache.ibatis.annotations.Param;
import org.quetzaco.archives.model.AcousticImage;
import org.quetzaco.archives.model.AcousticImageExample;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcousticImageMapper {
    long countByExample(AcousticImageExample example);

    int deleteByExample(AcousticImageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AcousticImage record);

    int insertSelective(AcousticImage record);

    List<AcousticImage> selectByExample(AcousticImageExample example);

    AcousticImage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AcousticImage record, @Param("example") AcousticImageExample example);

    int updateByExample(@Param("record") AcousticImage record, @Param("example") AcousticImageExample example);

    int updateByPrimaryKeySelective(AcousticImage record);

    int updateByPrimaryKey(AcousticImage record);
}