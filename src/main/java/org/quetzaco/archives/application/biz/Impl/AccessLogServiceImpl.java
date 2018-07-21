package org.quetzaco.archives.application.biz.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.quetzaco.archives.application.biz.AccessLogService;
import org.quetzaco.archives.application.dao.AccessLogMapper;
import org.quetzaco.archives.model.AccessLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessLogServiceImpl implements AccessLogService {

    @Autowired
    AccessLogMapper accessLogMapper;

    @Override
    public void insertLog(AccessLog accessLog) {
        accessLogMapper.insert(accessLog);
    }

    @Override
    public PageInfo getAccessLogById(AccessLog accessLog) {
        /*AccessLogExample accessLogExample = new AccessLogExample();
        AccessLogExample.Criteria  criteria = accessLogExample.or();
        if(objId!=null)
            criteria.andObjIdEqualTo(objId);
        else if(strId!=null)
            criteria.andStrIdEqualTo(strId);
        return    accessLogMapper.selectByExample(accessLogExample);*/

        //查出需要查询的用户列表 （list)
       /* AccessLogExample accessLogExample  = new AccessLogExample();
        accessLogExample.createCriteria().andAccessTypeEqualTo().andUsrIdIn()*/

        PageHelper.startPage(accessLog.getPageNum(), accessLog.getPageSize());
        List<AccessLog> maps = accessLogMapper.getAccessLogById(accessLog);
        PageInfo pageInfo = new PageInfo(maps);
        return pageInfo;
    }
}
