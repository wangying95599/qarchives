package org.quetzaco.archives.application.biz.Impl;

import org.quetzaco.archives.application.biz.EipJumpService;
import org.quetzaco.archives.application.dao.UserMapper;
import org.quetzaco.archives.model.User;
import org.quetzaco.archives.web.config.session.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;

@Service
public class EipJumpServiceImpl implements EipJumpService {

    @Autowired
    UserMapper userMapper;
    @Override
    public User checkSSO(String userId, HttpSession httpSession) {
        String loginName = userId.split("-")[0];
        User user = userMapper.getUserByLoginName(loginName);
        if(user==null){
            return null;
        }else {
            httpSession.setAttribute(WebSecurityConfig.SESSION_KEY,user);
            return user;
        }
    }
}
