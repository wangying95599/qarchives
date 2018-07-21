package org.quetzaco.archives.application.biz;

import org.junit.Test;
import org.quetzaco.archives.model.User;
import org.quetzaco.archives.qarchives.QarchivesApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description Created by dong on 2017/7/21.
 */
public class UserServiceTest extends QarchivesApplicationTests {
    @Autowired
    UserService userService;

   
    @Test
    public void remainToBeDone() {
       User user = new User();
       user.setId(1L);
      userService.remainToBeDone(user);
    }
}