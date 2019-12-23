package cn.com.zx.ibossapi.controller.myController;

import cn.com.zx.ibossapi.task.AsyncTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

@RestController
public class AsyncController {

    @Autowired
    private AsyncTask asyncTask;

    @RequestMapping("/doTask")
    public String doAsyncTask() throws InterruptedException {
        Long start = System.currentTimeMillis();
        Future<Boolean> future1 = asyncTask.task1();
        Future<Boolean> future2 = asyncTask.task2();
        while(!future1.isDone() || !future2.isDone()){
            if(future1.isDone() && future2.isDone()){
                break;
            }
        }
        Long end = System.currentTimeMillis();
        System.out.println("总耗时："+(end-start));
        return "程序执行完成";
    }

}
