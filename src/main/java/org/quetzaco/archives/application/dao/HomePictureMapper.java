package org.quetzaco.archives.application.dao;

import org.apache.ibatis.annotations.Param;
import org.quetzaco.archives.model.HomePicture;
import org.quetzaco.archives.model.HomePictureExample;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomePictureMapper {
    long countByExample(HomePictureExample example);

    int deleteByExample(HomePictureExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HomePicture record);

    int insertSelective(HomePicture record);

    List<HomePicture> selectByExample(HomePictureExample example);

    HomePicture selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HomePicture record, @Param("example") HomePictureExample example);

    int updateByExample(@Param("record") HomePicture record, @Param("example") HomePictureExample example);

    int updateByPrimaryKeySelective(HomePicture record);

    int updateByPrimaryKey(HomePicture record);
}