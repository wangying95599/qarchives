package org.quetzaco.archives.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.quetzaco.archives.application.biz.EipJumpService;
import org.quetzaco.archives.application.biz.RedisService;
import org.quetzaco.archives.application.biz.RoleService;
import org.quetzaco.archives.application.dao.FlowFormLendingMapper;
import org.quetzaco.archives.application.dao.FlowsMapper;
import org.quetzaco.archives.model.*;
import org.quetzaco.archives.util.boot.DES;
import org.quetzaco.archives.web.config.session.WebSecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import sun.misc.Request;
import sun.security.krb5.internal.crypto.Des;

@Controller
public class PageControler {

  final static Logger logger = LoggerFactory.getLogger(PageControler.class);
  @Autowired
  RoleService roleService;
  @Autowired
  FlowFormLendingMapper flowFormLendingMapper;
  @Autowired
  FlowsMapper flowsMapper;
  @Autowired
  RedisService redisService;

  @RequestMapping("/")
  public String home() {
    return "login.html";
  }
  @RequestMapping("/index")
  public String index() {
    return "login.html";
  }

  //loginAdmin.html
  @RequestMapping("/main")
  public String main() {
    logger.debug(" goto main ");
    return "usermain.html";
  }

  @RequestMapping("/admin")
  public String admin() {
    return "adminmain.html";
  }
  
  @RequestMapping("/searchgoogle")
  public String searchuse(@SessionAttribute(WebSecurityConfig.SESSION_KEY) User user, @RequestParam(value = "noAssist" ,required =false)boolean noAssist) {
    if(noAssist)
      redisService.delEsMark(user);
    System.out.println("searchgoogle123 "+noAssist);
    return "search_google.html";
//	  return "searchsite.html";
  }
  
  @RequestMapping("/search")
  public String search(HttpServletRequest request) {
	  System.out.println("searchcontent     "+request.getParameter("searchText"));
	  System.out.println("type      "+request.getParameter("type"));
    return "search_content.html?searchText="+request.getParameter("searchText")+"&type=1";
//	  return "searchsite.html";
  }

//  @RequestMapping("/search")
//  public String search() {
//    return "searchsite.html";
//  }

  @RequestMapping("/history")
  public String history() {
    return "historydata.html";
  }

  @RequestMapping("/view")
  public String view() {
    return "quetviewer.html";
  }

  @RequestMapping("/flowLending/endSwitch")
  public String end(@SessionAttribute(WebSecurityConfig.SESSION_KEY)User user,@RequestParam("flowId")Long flowId,@RequestParam("action")String action){
    if(!"ACCEPT".equals(action)){
      return "forward:/flowLending/end/"+action;
    }
    FlowFormLending flowFormLending = flowFormLendingMapper.selectByPrimaryKey(flowId);
    if(user.getId().equals(flowFormLending.getManagerId())){
      return "forward:/flowLending/end/"+action;
    }else{
      return "forward:/flowLending/process";
    }
  }

  @RequestMapping("/flowDestroy/endSwitch")
  public String endDes(@RequestParam("action") String action,@RequestParam("flowId")Long flowId, @RequestParam("usrId") Long usrId) {
    //判断是否为管理员
    /*Role role = roleService.getRoleByDepAndUser(deptId, usrId);
    if (role != null) {
      if (role.getId() == 1L || role.getId() == 3L)
        return "forward:/flowDestroy/end/" + action;
    }
    return "forward:/flowDestroy/process";*/
    if(!"ACCEPT".equals(action))
      return "forward:/flowDestroy/end/" + action;
    Flows flows = flowsMapper.selectByPrimaryKey(flowId);
    if(usrId.equals(flows.getCreatedBy()))
      return "forward:/flowDestroy/end/" + action;
    return "forward:/flowDestroy/process";
  }

  @RequestMapping("/flowFormDeliver/endSwitch")
  public String endTurnOver(@SessionAttribute(WebSecurityConfig.SESSION_KEY)User user, @RequestParam("action") String action,@RequestParam("deptId") Long deptId) {
    //如果action是否决的话直接结束流程
    if(!"ACCEPT".equals(action))
      return "forward:/flowFormDeliver/end/" + action;
    //判断是否为管理员
    Role role = roleService.getRoleByDepAndUser(deptId, user.getId());
    if (role != null) {
      if (role.getId() == 1L || role.getId() == 3L)
        return "forward:/flowFormDeliver/end/" + action;
    }
    return "forward:/flowFormDelivers/process";
  }

  @RequestMapping("/flowFormAssist/endSwitch")
  public String endAssist(@SessionAttribute(WebSecurityConfig.SESSION_KEY)User user, @RequestParam("action") String action,@RequestParam("deptId") Long deptId) {
    //如果action是否决的话直接结束流程
    if(!"ACCEPT".equals(action))
      return "forward:/flowFormAssist/end/" + action;
    //判断是否为管理员
    Role role = roleService.getRoleByDepAndUser(deptId, user.getId());
    if (role != null) {
      if (role.getId() == 1L || role.getId() == 3L)
        return "forward:/flowFormAssist/end/" + action;
    }
    return "forward:/flowFormAssist/process";
  }

  //	固定key
  @Value("${EIP_JUMP.RKEY}")
  static String rkey;

  @Autowired
  EipJumpService eipJumpService;


  //sso  eipJump 单点登录
  @RequestMapping("/sso")
  public String fromEipJump(HttpServletRequest request, HttpSession httpSession){
    if(httpSession.getAttribute(WebSecurityConfig.SESSION_KEY)!=null){
      return "/main";
    }

    String e_userId = request.getParameter("userid");
    String flag = request.getParameter("flag");
    String e_checkTime = request.getParameter("check_time");
    String e_userData = request.getParameter("userData");

    if(StringUtils.isEmpty(e_userId)||StringUtils.isEmpty(flag)||StringUtils.isEmpty(e_checkTime)||StringUtils.isEmpty(e_userData)){
      return "/";
    }

    String userKey = DES.desDecryptString(e_userData, rkey);
    logger.debug("解密userKey:"+userKey);
    String userId = DES.desDecryptString(e_userId, userKey);
    logger.debug("解密userId:"+userId);
    String checkTime = DES.desDecryptString(e_checkTime, userKey);
    logger.debug("解密checkTime:"+checkTime);

    if((System.currentTimeMillis()-Long.valueOf(checkTime))/1000 >10){
      return "/";
    }

    User contextUser = eipJumpService.checkSSO(userId,httpSession);
    if(contextUser!=null){
      return "/main";
    }else {
      return "/";
    }

  }
}
