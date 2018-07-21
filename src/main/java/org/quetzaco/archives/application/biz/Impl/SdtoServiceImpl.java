package org.quetzaco.archives.application.biz.Impl;

import org.quetzaco.archives.application.biz.FlowNodesService;
import org.quetzaco.archives.application.biz.SdtoService;
import org.quetzaco.archives.application.dao.FlowsMapper;
import org.quetzaco.archives.application.dao.UserMapper;
import org.quetzaco.archives.model.ScheduleToOa;
import org.quetzaco.archives.model.User;
import org.quetzaco.archives.util.config.PropelProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class SdtoServiceImpl implements SdtoService {
    @Autowired
    FlowsMapper flowsMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    PropelProperties propelProperties;
    @Autowired
    FlowNodesService flowNodesService;
    private final Logger LOOGER = LoggerFactory.getLogger(SdtoServiceImpl.class);

    @Scheduled(cron = "${cron.propel}")
    public void propelGiveBackToCreatedBy(){
        if(!propelProperties.isPropel()) return;
        LOOGER.debug("-----------------propelGiveBackToCreateBy    start ---------------");
        List<Map> mapList = flowsMapper.remainGiveBackToCreatedBy();
        if(mapList==null||mapList.size()==0) return;
        for(Map map :mapList){
            ScheduleToOa ss = new ScheduleToOa();
            Long createdBy = (Long) map.get("created_by");
            User user = userMapper.selectByPrimaryKey(createdBy);
            String loginName = user.getLoginName();
            String title = (String) map.get("title");
            String deadLine = (String) map.get("dead_line");
            Long flowId = (Long) map.get("id");
            String uuid = String.valueOf(UUID.randomUUID());
            StringBuffer sb = new StringBuffer();
            sb.append("您的借阅：");
            sb.append(title);
            sb.append(" 将于");
            sb.append(deadLine);
            sb.append("到期，请您及时归还");
            ss.setProcessinstname(String.valueOf(sb));
            ss.setExecutor(loginName);
            ss.setProcessinstid(String.valueOf(flowId));
            ss.setRefrenceId(uuid);
            ss.setUsermode(propelProperties.getUsermode());
            ss.setPassword(propelProperties.getPassword());
            ss.setActionUrl(propelProperties.getActionurl());
            ss.setBizsystem(propelProperties.getBizsystem());
            if(propelProperties.isPropel())
                flowNodesService.propelToOa(ss);
            flowsMapper.updatePropelFlag(flowId, uuid,"01");
            // flowsMapper.updatePropelFlagNoGuid(flowId, "01");
        }
    }
}
