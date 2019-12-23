package cn.com.zx.ibossapi.controller.myController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SessionController {

    @GetMapping("/saveSession")
    @ResponseBody
    public String saveSession(HttpServletRequest request){
        request.getSession().setAttribute("userId","joy");
        return "save success";
    }

}
