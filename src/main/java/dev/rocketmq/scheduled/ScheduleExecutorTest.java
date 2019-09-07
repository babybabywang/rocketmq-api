package dev.rocketmq.scheduled;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author echo huang
 * @version 1.0
 * @date 2019-09-08 00:12
 * @description 任务线程池demo
 */
public class ScheduleExecutorTest {
    public static void main(String[] args) {

        ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(1);
//        scheduled.schedule(() -> {
//            System.out.println("test");
//        }, 10, TimeUnit.MICROSECONDS);
        scheduled.scheduleAtFixedRate(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("test" + LocalDateTime.now().getSecond());
        }, 1000, 1000, TimeUnit.MILLISECONDS);
    }
}
