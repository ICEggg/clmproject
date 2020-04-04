package javaproject.thread.pool;

import javaproject.mydp.list.Collection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    private static ExecutorService service = Executors.newFixedThreadPool(8);
    final static CountDownLatch latch = new CountDownLatch(8);

    public static void main(String[] args) throws IOException, InterruptedException {
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 8; i++) {
            service.execute(()->{
                add(list);
                latch.countDown();
            });
        }
        latch.await();
        service.shutdown();
        System.out.println(list.size());

        /*clm.service.shutdown();
        while (true) {//等待所有任务都执行结束
            if (clm.service.isTerminated()) {//所有的子线程都结束了
                System.out.println(list.size());
                break;
            }
        }*/




    }

    public static void add(List<Integer> list){
        int count = 0;
        for (int i = 0; i < 10000; i++) {
            count++;
            list.add(count);
        }
        System.out.println(Thread.currentThread().getName()+"-----"+count);
    }
}
