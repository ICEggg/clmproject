package javaproject.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * newCachedThreadPool     可以起无数个线程，直到系统能支撑的最多
 *
 * 新起一个任务，池里有空闲的线程就用空闲的。没有就在池里新建一个
 *
 * 池里的一个线程如果超过60s没有被使用，就会自动被销毁
 */
public class T08_CachePool {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        System.out.println(service);

        for (int i = 0; i < 2; i++) {
            service.submit(()->{
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }

        System.out.println(service);

        TimeUnit.SECONDS.sleep(1);

        System.out.println(service);



    }


}
