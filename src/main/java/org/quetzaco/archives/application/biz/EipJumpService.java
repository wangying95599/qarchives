package org.quetzaco.archives.application.biz;

import org.quetzaco.archives.model.User;

import javax.servlet.http.HttpSession;

public interface EipJumpService {

    User checkSSO(String userId, HttpSession httpSession);
}
