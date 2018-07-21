package org.quetzaco.archives.web.restful;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class AppErrorController implements ErrorController {
	
	private static final String ERROR_PATH = "/error";  
    @RequestMapping(value=ERROR_PATH)  
    public String handleError(){  
        return "/login.html";  
    }  
    @Override  
    public String getErrorPath() {  
        return ERROR_PATH;  
    }  
}
