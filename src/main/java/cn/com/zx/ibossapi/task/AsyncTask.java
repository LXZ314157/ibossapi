package cn.com.zx.ibossapi.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * 异步任务
 * 注意：要在主启动程序加上注解 @EnableAsync
 */
@Component
public class AsyncTask {

    @Async
    public Future<Boolean> task1() throws InterruptedException {
        Long start = System.currentTimeMillis();
        Thread.sleep(1000);
        Long end = System.currentTimeMillis();
        System.out.println("task1 耗时："+(end-start));
        return new AsyncResult<>(true);
    }

    @Async
    public Future<Boolean> task2() throws InterruptedException {
        Long start = System.currentTimeMillis();
        Thread.sleep(3000);
        Long end = System.currentTimeMillis();
        System.out.println("task2 耗时："+(end-start));
        return new AsyncResult<>(true);
    }

}
