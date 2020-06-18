package javaproject.thread.pool;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 其他线程池，每个线程维护一个自己的任务队列。
 *
 * newWorkStealingPool，这个当自己队列里的任务执行完后，会把别的队列的任务拿来执行
 *
 *
 * 这个线程池，默认起的线程数，就是cpu的核数。底层是用的ForkJoinPool
 *
 */
public class T11_WorkStealingPool {
    public static void main(String[] args) throws IOException {
        ExecutorService service = Executors.newWorkStealingPool();

        //打印本机cpu有多少核
        System.out.println(Runtime.getRuntime().availableProcessors());

        service.execute(new R(1000));
        service.execute(new R(2000));
        service.execute(new R(2000));
        service.execute(new R(2000));
        service.execute(new R(2000));

        //由于产生是精灵线程（守护线程，后台线程），主线程不阻塞的话，看不到输出
        System.in.read();

    }

    static class R implements Runnable{
        int time;

        public R(int time) {
            this.time = time;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(time+"--"+Thread.currentThread().getName());
        }
    }


}
