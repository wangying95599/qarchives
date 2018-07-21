package org.quetzaco.archives.application.biz.Impl;

import org.quetzaco.archives.application.biz.RedisService;
import org.quetzaco.archives.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    RedisTemplate redisTemplate;
    public static final String isAssist = "assist_Mark_";
    public static final String INSIDE_JUMP = "inside_jump_";
    public static final String JUMP_TO = "jump_to";
    public static final String JUMP_DATA = "jump_data";

    @Override
    public void setEsMark(User user, String value) {
        Long usrId = user.getId();
        String key = isAssist+usrId;
        redisTemplate.opsForValue().set(key,value,10*60, TimeUnit.SECONDS);
    }

    @Override
    public String getEsMark(User user) {
        String key = isAssist+user.getId();
        String value = (String) redisTemplate.opsForValue().get(key);
        return value;
    }

    @Override
    public void delEsMark(User user) {
        String key = isAssist+user.getId();
        redisTemplate.delete(key);
    }

    //insideKey 00(协查时内部跳转使用)  01（待办内部跳转使用）
    @Override
    public void setInsideJump(User user, String value,String insideKey) {
        String key = INSIDE_JUMP+user.getId();
        redisTemplate.opsForHash().put(key,JUMP_TO,insideKey);
        redisTemplate.opsForHash().put(key,JUMP_DATA,value);
    }

    @Override
    public Map<String, String> getInsideJump(User user) {
        String key = INSIDE_JUMP +user.getId();
        Map<String,String> map = redisTemplate.opsForHash().entries(key);
        return map;
    }

    @Override
    public void delInsideJump(User user) {
        String key = INSIDE_JUMP+user.getId();
        redisTemplate.delete(key);
    }

    @Override
    public void delEsMarkAndSetJumpData(User user, String value) {
        delEsMark(user);
        setInsideJump(user,value,"00");
    }
}
