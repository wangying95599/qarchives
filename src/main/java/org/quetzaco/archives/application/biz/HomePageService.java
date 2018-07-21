package org.quetzaco.archives.application.biz;

import com.github.pagehelper.PageInfo;
import org.quetzaco.archives.model.HomePage;

import java.util.List;

public interface HomePageService {

  void insert(String textarea, String type,String docId);
  
  PageInfo getContent(int PageNum, int PageSize,String type);

  PageInfo getLink(int PageNum, int PageSize);

  PageInfo getInformation(int PageNum, int PageSize);


  void updateContent(HomePage homePage);

  void deleteHomePage(List<Long> list);
}
