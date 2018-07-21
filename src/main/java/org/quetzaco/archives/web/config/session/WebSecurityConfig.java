package org.quetzaco.archives.web.config.session;

import org.apache.http.HttpStatus;
import org.quetzaco.archives.model.User;
import org.quetzaco.archives.util.runnable.ThreadLocalTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Description Created by dong on 2017/7/16.
 */

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 30 * 60)
public class WebSecurityConfig extends WebMvcConfigurerAdapter {

  final static Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
  /**
   * 登录session key
   */
  public final static String SESSION_KEY = "user";

  @Bean
  public SecurityInterceptor getSecurityInterceptor() {
    return new SecurityInterceptor();
  }
  /*@Bean
  public EmbeddedServletContainerCustomizer containerCustomizer() {
    return new EmbeddedServletContainerCustomizer() {
      @Override
      public void customize(ConfigurableEmbeddedServletContainer container) {
        container.addErrorPages(new ErrorPage(org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED, "/"));
        container.addErrorPages(new ErrorPage(org.springframework.http.HttpStatus.NOT_FOUND,"/"));
      }
    };
  }*/
  public void addInterceptors(InterceptorRegistry registry) {
    InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

    // 排除配置
    addInterceptor.excludePathPatterns("/error");
    addInterceptor.excludePathPatterns("/css/**");
    addInterceptor.excludePathPatterns("/fonts/**");
    addInterceptor.excludePathPatterns("/img/**");
    addInterceptor.excludePathPatterns("/js/**");
    //addInterceptor.excludePathPatterns("/login");
    addInterceptor.excludePathPatterns("/users/login");
    addInterceptor.excludePathPatterns("/login/**");
    addInterceptor.excludePathPatterns("/");
    addInterceptor.excludePathPatterns("/error/**");
    addInterceptor.excludePathPatterns("/sso");
    addInterceptor.excludePathPatterns("/sdto");
    // 拦截配置
    addInterceptor.addPathPatterns("/**");
  }

  private class SecurityInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler)
        throws Exception {
      HttpSession session = request.getSession();
      if (session.getAttribute(SESSION_KEY) != null) {
        User user = (User) session.getAttribute(SESSION_KEY);
        ThreadLocalTest.setUsrTl(user.getId());
        logger.debug("usrtl : "+user.getId());
        return true;
      }
    logger.debug("go here  ",request.getRequestURL());

      // 跳转登录
      /*response.setStatus(405);
      response.sendError(HttpStatus.SC_METHOD_NOT_ALLOWED,"Have no permission to do this action, you need login  ");
      return false;*/

      response.setHeader("Location","/");
      response.setStatus(405);
      response.sendError(HttpStatus.SC_METHOD_NOT_ALLOWED,"Have no permission to do this action, you need login . ");
      return false;
    }
  }
}