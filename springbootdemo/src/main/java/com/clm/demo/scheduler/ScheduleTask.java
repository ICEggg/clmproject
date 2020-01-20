package com.clm.demo.scheduler;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Cron表达式参数分别表示：
 * 秒（0~59） 例如0/5表示每5秒
 * 分（0~59）
 * 时（0~23）
 * 日（0~31）的某天，需计算
 * 月（0~11）
 * 周几（ 可填1-7 或 SUN/MON/TUE/WED/THU/FRI/SAT）
 *
 * 注解方式的缺点是:当我们调整了执行周期的时候，需要重启应用才能生效，这多少有些不方便。为了达到实时生效的效果，可以使用接口来完成定时任务。
 */
@Component
@EnableAsync    // 2.开启多线程
public class ScheduleTask {

    //3.添加定时任务
    @Async  //开启多线程后，要配合使用这个注解
    @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    public void configureTasks() {
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
    }

//    @Scheduled(fixedRate=1000)
//    private void configureTasks2() {
//        System.err.println("执行静态定时任务时间2: " + LocalDateTime.now());
//    }

}
