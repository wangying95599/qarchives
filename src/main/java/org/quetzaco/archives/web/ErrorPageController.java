package org.quetzaco.archives.web;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

//@Controller
public class ErrorPageController implements ErrorController {
    private static final String PATH = "/error";
    /*@RequestMapping("/404")
    public String errorHtml404() {
        System.out.println("this is 404 html");
        return "/login.html";
    }

    @RequestMapping("/405")
    public String errorHtml405() {
        System.out.println("this is 405 html");
        return "/login.html";
    }*/
    @RequestMapping(value=PATH)
    public String handleError(){
        return "/login.html";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
