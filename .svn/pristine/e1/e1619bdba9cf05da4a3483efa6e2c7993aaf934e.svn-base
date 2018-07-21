package org.quetzaco.archives.application.dao;

import org.apache.ibatis.annotations.Param;
import org.quetzaco.archives.model.HomePage;
import org.quetzaco.archives.model.HomePageExample;

import java.util.List;

public interface HomePageMapper {
    long countByExample(HomePageExample example);

    int deleteByExample(HomePageExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HomePage record);

    int insertSelective(HomePage record);

    List<HomePage> selectByExample(HomePageExample example);

    HomePage selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HomePage record, @Param("example") HomePageExample example);

    int updateByExample(@Param("record") HomePage record, @Param("example") HomePageExample example);

    int updateByPrimaryKeySelective(HomePage record);

    int updateByPrimaryKey(HomePage record);
}