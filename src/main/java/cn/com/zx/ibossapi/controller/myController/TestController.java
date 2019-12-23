package cn.com.zx.ibossapi.controller.myController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 测试控制层
 */
@Controller
public class TestController {

    @GetMapping("/testSession")
    @ResponseBody
    public String testSession(HttpServletRequest request, @RequestParam String param1,@RequestParam Integer param2) throws Exception{
        System.out.println(param1);
        System.out.println(param2);
        return "request success";
    }

}
