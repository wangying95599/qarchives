package org.quetzaco.archives.application.message;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.quetzaco.archives.application.biz.AccessLogService;
import org.quetzaco.archives.model.AccessLog;
import org.quetzaco.archives.model.PrimaryKey;
import org.quetzaco.archives.model.User;
import org.quetzaco.archives.model.api.APIEntity;
import org.quetzaco.archives.web.config.session.WebSecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description Created by dong on 2017/8/14.
 */
@Component
@Aspect
public class AccessLogHandle {

  final static Logger logger = LoggerFactory.getLogger(AccessLogHandle.class);
  @Autowired
  AccessLogService accessLogService;

  private static OptionLogger giveController(JoinPoint joinPoint) throws Exception {
    Signature signature = joinPoint.getSignature();
    MethodSignature methodSignature = (MethodSignature) signature;
    Method method = methodSignature.getMethod();

    if (method != null) {
      return method.getAnnotation(OptionLogger.class);
    }
    return null;
  }

  @AfterReturning(pointcut = "execution(* org.quetzaco.archives.web.restful.DocumentController.*(..) ) && @annotation(org.quetzaco.archives.application.message.OptionLogger)", returning = "result")
  public void proccessDocumentController(JoinPoint jp, Object result) {
    handleLog(jp, result);
  }

  @AfterReturning(pointcut = "execution(* org.quetzaco.archives.web.restful.ZipUtilsController.*(..))&&@annotation(org.quetzaco.archives.application.message.OptionLogger)", returning = "result")
  public void zipDownloadController(JoinPoint jp, Object result) {
    handleLog(jp, result);
  }

  @AfterReturning(pointcut = "execution(* org.quetzaco.archives.web.restful.ArchiveController.*(..))&&@annotation(org.quetzaco.archives.application.message.OptionLogger)", returning = "result")
  public void proccessArchiveController(JoinPoint jp, Object result) {
    handleLog(jp, result);
  }

  @AfterReturning(pointcut = "execution(* org.quetzaco.archives.web.restful.BoxController.*(..))@annotation(org.quetzaco.archives.application.message.OptionLogger)", returning = "result")
  public void proccessBoxController(JoinPoint jp, Object result) {
    handleLog(jp, result);

  }

  @AfterReturning(pointcut = "execution(* org.quetzaco.archives.web.restful.RecordController.*(..))@annotation(org.quetzaco.archives.application.message.OptionLogger)", returning = "result")
  public void proccessRecordController(JoinPoint jp, Object result) {
    handleLog(jp, result);
  }

/* 其他一些部分的切面方法 .. 暂时不用
 @After("execution(* org.quetzaco.archives.web.restful.DeptController.*(..))")
  public void proccessDeptController(JoinPoint jp) {
    jp.getArgs();
  }

  @After("execution(* org.quetzaco.archives.web.restful.UserController.*(..))")
  public void proccessUserController(JoinPoint jp) {
    jp.getArgs();
  }

  @After("execution(* org.quetzaco.archives.web.restful.RoleController.*(..))")
  public void proccessRoleController(JoinPoint jp) {
    jp.getArgs();
  }

  @After("execution(* org.quetzaco.archives.web.restful.FlowLendingController.*(..))")
  public void proccessFlowLendingController(JoinPoint jp) {
    jp.getArgs();
  }*/

  private void handleLog(JoinPoint joinPoint, Object result) {
    try {

      Signature signature = joinPoint.getSignature();
      MethodSignature methodSignature = (MethodSignature) signature;
      Method method = methodSignature.getMethod();

      OptionLogger logger = null;
      //获得注解
      if (method != null) {
        logger = method.getAnnotation(OptionLogger.class);
      }
      if (logger == null) {
        return;
      }

      Object[] arguments = joinPoint.getArgs();

      String objectType = logger.objectType();
      OptionLogger.OpType type = logger.type();
      String description = "";
      Long objId = null;
      String strId = null;
      List<Long> list = null;

      if ("ZIP下载".equals(type.getName())) {
        String objectType1 = (String) arguments[0];
        objectType = "document".equals(objectType1) ? "文档" : "record".equals(objectType1) ? "案卷" : "archive".equals(objectType1) ? "档案" : "全宗";
        if (arguments[1] instanceof String) {
          String fileId = (String) arguments[1];
          arguments[1] = "document".equals(arguments[0]) ? fileId : Long.parseLong(fileId);
        }
      }

      //获取objId
      if (!"新增".equals(type.getName())) {
        Parameter[] parameters = method.getParameters();
        if (parameters != null && parameters.length > 0) {
          for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(AccessKey.class)) {
              if (arguments[i] instanceof Long) {
                objId = (Long) arguments[i];
              } else {
                strId = (String) arguments[i];
              }
              break;
            }
            if(parameters[i].isAnnotationPresent(MultiAccessKey.class)){
                list = (List<Long>) arguments[i];
                break;
            }
          }
        }
      } else {
        //在返回值中获取objId
        System.out.println("result                   " + result);
        HttpEntity httpEntity = (HttpEntity) result;
        APIEntity apiEntity = (APIEntity) httpEntity.getBody();
        if (apiEntity.getContent() instanceof PrimaryKey) {
          PrimaryKey primaryKey = (PrimaryKey) apiEntity.getContent();
          objId = primaryKey.getId();
        }

        System.out.println("result                   " + method.getReturnType());
        System.out.println("result                   " + method.getGenericReturnType());
        System.out.println("result                   " + apiEntity.getContent());
      }
      if (!"".equals(logger.description().trim())) {
        description = logger.description();
      }

      HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
          .getRequestAttributes()).getRequest();
      HttpSession session = request.getSession();
      //读取session中的用户
        User user = (User) session.getAttribute(WebSecurityConfig.SESSION_KEY);
      // Long usrId = user.getId();
        // Long usrId = 1l;
        // User user = new User();
        // user.setId(usrId);
      if(list==null){
        extractInsertAccessLog(objectType, type, description, strId, user, objId);
      }else{
        for(Long id:list){
          extractInsertAccessLog(objectType, type, description, strId, user, id);
        }
      }



    } catch (Exception exp) {
      logger.debug("error in accesslog handle ", exp);
      logger.error("异常信息:{}", exp.getMessage());
    }
  }

  private void extractInsertAccessLog(String objectType, OptionLogger.OpType type, String description, String strId, User user, Long id) {
    AccessLog accessLog = new AccessLog();
    accessLog.setUser(user);
    accessLog.setAccessType(type.getName());
    accessLog.setObjType(objectType);
    accessLog.setObjId(id);
    accessLog.setTime(new Date());
    accessLog.setStrId(strId);
    accessLog.setDescription(description);
    accessLogService.insertLog(accessLog);
  }
}
