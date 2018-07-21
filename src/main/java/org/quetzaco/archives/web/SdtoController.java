package org.quetzaco.archives.web;

import org.apache.commons.lang3.StringUtils;
import org.quetzaco.archives.application.biz.EipJumpService;
import org.quetzaco.archives.application.biz.FlowService;
import org.quetzaco.archives.application.biz.RedisService;
import org.quetzaco.archives.model.Flows;
import org.quetzaco.archives.model.User;
import org.quetzaco.archives.util.propelToOA.DecryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;

;

@Controller
public class SdtoController {

    @Autowired
    EipJumpService eipJumpService;
    @Autowired
    RedisService redisService;
    @Autowired
    FlowService flowService;

    private final Logger LOGGER = LoggerFactory.getLogger(SdtoController.class);
    //从第三方代办系统 进入 处理 待办
    @RequestMapping(value = "/sdto",method = RequestMethod.GET)
    public String handleSd(HttpServletRequest request, HttpSession httpSession) throws Exception {
        /*encPortalUserID：加密的门户工号
                   encWorkItemID： 加密的门户工作项ID
                   encProcessinstID：加密的流程实例ID
                   encActInstID：   加密的环节实例ID
                   encKey：        加密解密KEY
                   encCuurTime     加密的时间戳*/
        String encPortalUserID = request.getParameter("encPortalUserID");
        String encWorkItemID = request.getParameter("encWorkItemID");
        String encProcessinstID = request.getParameter("encProcessinstID");
        String encActInstID = request.getParameter("encActInstID");
        String encKey = request.getParameter("encKey");
        String encCuurTime = request.getParameter("encCuurTime");

        LOGGER.debug("+++++++++++++++++++sdto ctrl  start ++++++++++++++");
        LOGGER.debug("encPortalUserID:"+encPortalUserID);
        LOGGER.debug("encWorkItemID:"+encWorkItemID);
        LOGGER.debug("encProcessinstID:"+encProcessinstID);
        LOGGER.debug("encActInstID:"+encActInstID);
        LOGGER.debug("enckey:"+encKey);
        LOGGER.debug("encCuurTime:"+encCuurTime);
        if(StringUtils.isEmpty(encPortalUserID)||StringUtils.isEmpty(encWorkItemID)||StringUtils.isEmpty(encProcessinstID)||StringUtils.isEmpty(encActInstID)||StringUtils.isEmpty(encCuurTime))
            return "/";

        String portalUserID = URLDecoder.decode(DecryptUtil.decrypt(encKey, encPortalUserID), "utf-8");
        String workItemID = URLDecoder.decode(DecryptUtil.decrypt(encKey,encWorkItemID),"utf-8");
        String processinstID = URLDecoder.decode(DecryptUtil.decrypt(encKey, encProcessinstID), "utf-8");
        String actInstID = URLDecoder.decode(DecryptUtil.decrypt(encKey, encActInstID), "utf-8");
        String cuurTime = URLDecoder.decode(DecryptUtil.decrypt(encKey, encCuurTime), "utf-8");

        LOGGER.debug("portalUserId:"+portalUserID+"  workItemId:"+workItemID +" processinstID:"+processinstID+" actInstID:"+actInstID+" cuurTime:"+cuurTime);

        if((System.currentTimeMillis()-Long.valueOf(cuurTime))/1000 >10){
            return "/";
        }

        User user = eipJumpService.checkSSO(portalUserID,httpSession);
        if(user!=null){
            Flows flows = new Flows();
            flows.setId(Long.valueOf(processinstID));
            flows = flowService.detail(flows);
            // String value = "processinstID="+processinstID+"&actInstID="+actInstID+"&workItemID="+workItemID;
            String value = flows.getId()+"_"+flows.getType();

            if (DecryptUtil.isInteger(actInstID)) {
                redisService.setInsideJump(user,value,"01");
            }
            LOGGER.debug("++++++++  sdto  ctrl end+++++++++++++++");
            return "/main";
        }else {
            return "/";
        }
    }

}
