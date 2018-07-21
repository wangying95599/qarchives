package org.quetzaco.archives.application.biz;

import org.quetzaco.archives.model.User;

import java.util.Map;

public interface RedisService {
    void setEsMark(User user,String value);

    String getEsMark(User user);

    void delEsMark(User user);

    void setInsideJump(User user,String value,String key);

    Map<String,String> getInsideJump(User user);

    void delInsideJump(User user);

    void delEsMarkAndSetJumpData(User user, String value);
}
