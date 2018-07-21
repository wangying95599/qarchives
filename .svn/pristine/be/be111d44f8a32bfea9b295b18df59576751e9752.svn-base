package org.quetzaco.archives.web.restful;

import org.quetzaco.archives.application.biz.AccessLogService;
import org.quetzaco.archives.model.AccessLog;
import org.quetzaco.archives.model.User;
import org.quetzaco.archives.model.api.APIEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccessLogController extends BaseRestContoller {

    @Autowired
    AccessLogService accessLogService;

    @RequestMapping(value = "/accessLog/list", method = RequestMethod.POST)
    public HttpEntity<APIEntity> getAccessLogById(AccessLog accessLog, @RequestParam(value = "name", required = false) String name) {
        if (name != null) {
            User user = new User();
            user.setName(name);
            accessLog.setUser(user);
        }
        return buildEntity(APIEntity.create(accessLogService.getAccessLogById(accessLog)), HttpStatus.OK);
    }
}
