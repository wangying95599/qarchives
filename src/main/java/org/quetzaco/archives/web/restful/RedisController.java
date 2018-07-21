package org.quetzaco.archives.web.restful;

import org.quetzaco.archives.application.biz.RedisService;
import org.quetzaco.archives.model.User;
import org.quetzaco.archives.model.api.APIEntity;
import org.quetzaco.archives.util.config.WebAppConfig;
import org.quetzaco.archives.web.config.session.WebSecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisController extends BaseRestContoller {

    final static Logger LOGGER = LoggerFactory.getLogger(RedisController.class);
    @Autowired
    RedisService redisService;
    @RequestMapping(value = "",method = RequestMethod.POST)
    public HttpEntity<APIEntity> setEsMark(@SessionAttribute(WebSecurityConfig.SESSION_KEY)User user,@RequestParam("esMark") String value){
        LOGGER.debug("esMark post :"+value);
        redisService.setEsMark(user,value);
        return buildEntity(APIEntity.create("CREATED"), HttpStatus.CREATED);
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    public HttpEntity<APIEntity> getEsMark(@SessionAttribute(WebSecurityConfig.SESSION_KEY)User user){
        return buildEntity(APIEntity.create(redisService.getEsMark(user)), HttpStatus.OK);
    }

    @RequestMapping(value = "",method = RequestMethod.PUT)
    public HttpEntity<APIEntity> delEsMark(@SessionAttribute(WebSecurityConfig.SESSION_KEY)User user){
        redisService.delEsMark(user);
        return buildEntity(APIEntity.create("DELETE"), HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/insJump",method = RequestMethod.POST)
    public HttpEntity<APIEntity> setInsideJump(@SessionAttribute(WebSecurityConfig.SESSION_KEY)User user,@RequestParam("jumpData") String value){
        LOGGER.debug("jumpData post :"+value);
        redisService.setInsideJump(user,value,"00");
        return buildEntity(APIEntity.create("CREATED"), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/insJump",method = RequestMethod.GET)
    public HttpEntity<APIEntity> getInsideJump(@SessionAttribute(WebSecurityConfig.SESSION_KEY)User user){
        return buildEntity(APIEntity.create(redisService.getInsideJump(user)), HttpStatus.OK);
    }

    @RequestMapping(value = "/insJump",method = RequestMethod.PUT)
    public HttpEntity<APIEntity> delInsideJump(@SessionAttribute(WebSecurityConfig.SESSION_KEY)User user){
        redisService.delInsideJump(user);
        return buildEntity(APIEntity.create("DELETE"), HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/cd",method = RequestMethod.PUT)
    public HttpEntity<APIEntity> delEsMarkAndSetJumpData(@SessionAttribute(WebSecurityConfig.SESSION_KEY)User user,@RequestParam("jumpData")String value){
        redisService.delEsMarkAndSetJumpData(user,value);
        return buildEntity(APIEntity.create("DELETE"), HttpStatus.NO_CONTENT);
    }


}
