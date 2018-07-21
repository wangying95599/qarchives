package org.quetzaco.archives.application.dao;

import static org.junit.Assert.*;

import com.github.pagehelper.PageHelper;
import org.junit.Test;
import org.quetzaco.archives.model.User;
import org.quetzaco.archives.qarchives.QarchivesApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description Created by dong on 2017/7/21.
 */
public class FlowFormLendingMapperTest extends QarchivesApplicationTests {


  @Autowired
  FlowFormLendingMapper flowFormLendingMapper;

  @Test
  public void selectStartByMeList() throws Exception {
    PageHelper.startPage(1, 10);
    User user = new User();
    user.setId(1l);
    //assertEquals(1, flowFormLendingMapper.selectStartByMeList(user).size());
  }

  @Test
  public void getProcessByMe() throws Exception {
    PageHelper.startPage(1, 10);
    User user = new User();
    user.setId(1l);
   // assertEquals(1, flowFormLendingMapper.selectProcessByMe(user).size());
  }


}