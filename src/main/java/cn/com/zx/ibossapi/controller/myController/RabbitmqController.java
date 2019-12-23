package cn.com.zx.ibossapi.controller.myController;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitmqController {

    @GetMapping("/testMq")
    public String testMq(){
//        rabbitAdmin.declareExchange();
        return "mq success";
    }

}
