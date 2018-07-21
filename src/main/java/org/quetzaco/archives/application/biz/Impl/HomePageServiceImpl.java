package org.quetzaco.archives.application.biz.Impl;

import java.util.Date;
import java.util.List;

import org.quetzaco.archives.application.biz.HomePageService;
import org.quetzaco.archives.application.dao.FileMapper;
import org.quetzaco.archives.application.dao.HomePageMapper;
import org.quetzaco.archives.model.FileExample;
import org.quetzaco.archives.model.Files;
import org.quetzaco.archives.model.HomePage;
import org.quetzaco.archives.model.HomePageExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class HomePageServiceImpl implements HomePageService {

  @Autowired
  HomePageMapper homePageMapper;
  
  @Autowired
  FileMapper fileMapper;


  public void insert(String textarea, String type,String docId) {
    HomePage homePage = new HomePage();
    homePage.setContent(textarea);
    homePage.setType(type);
    homePage.setUpdateDt(new Date());
    homePage.setDocId(docId);
      
    FileExample fe = new FileExample();
    fe.createCriteria().andDocIdEqualTo(docId);
    List<Files> files=  fileMapper.selectByExample(fe);
    if(files!=null && files.size()>0) {
    	homePage.setFileId(files.get(0).getId());
    }
    homePageMapper.insertSelective(homePage);

  }
  
  @Override
  public PageInfo getContent(int pageNum, int pageSize,String type) {
    HomePageExample example = new HomePageExample();
      example.createCriteria().andTypeEqualTo(type).andRecordFlagEqualTo(true);
      example.setOrderByClause("update_dt desc");
      PageHelper.startPage(pageNum, pageSize);
      return new PageInfo(homePageMapper.selectByExample(example));
  }

  @Override
  public PageInfo getLink(int pageNum, int pageSize) {
    HomePageExample example = new HomePageExample();
      example.createCriteria().andTypeEqualTo("link").andRecordFlagEqualTo(true);
      example.setOrderByClause("update_dt desc");
      PageHelper.startPage(pageNum, pageSize);
      return new PageInfo(homePageMapper.selectByExample(example));
  }

  @Override
  public PageInfo getInformation(int pageNum, int pageSize) {
    HomePageExample example = new HomePageExample();
      example.createCriteria().andTypeEqualTo("information").andRecordFlagEqualTo(true);
      example.setOrderByClause("update_dt desc");
      PageHelper.startPage(pageNum, pageSize);
      return new PageInfo(homePageMapper.selectByExample(example));
  }

    @Override
    public void updateContent(HomePage homePage) {
        homePageMapper.updateByPrimaryKeySelective(homePage);
    }

    @Override
    public void deleteHomePage(List<Long> list) {
        HomePage homePage = new HomePage();
        homePage.setUpdateDt(new Date());
        homePage.setRecordFlag(false);
        HomePageExample homePageExample = new HomePageExample();
        homePageExample.createCriteria().andIdIn(list);
        homePageMapper.updateByExampleSelective(homePage, homePageExample);
  }

}
