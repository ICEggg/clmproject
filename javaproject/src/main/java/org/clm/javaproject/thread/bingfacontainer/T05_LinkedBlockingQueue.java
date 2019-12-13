package org.clm.javaproject.thread.bingfacontainer;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 *  阻塞式容器
 *  LinkedBlockingQueue     无界队列（可以往里扔无数的东西，直到内存不够）
 *
 *  实现生产者消费者
 */
public class T05_LinkedBlockingQueue {
    static BlockingQueue<String> strs = new LinkedBlockingQueue<>();

    static Random r = new Random();

    public static void main(String[] args) {
        //生产者线程
        new Thread(()->{
            try {
                for (int i = 0; i < 100; i++) {

                    //put方法，如果满了，就会等待
                    strs.put("a"+i);
                    TimeUnit.MILLISECONDS.sleep(r.nextInt(1000));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"p1").start();


        //消费者线程
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
               for (;;){
                   try {
                       //take方法，如果空了，就会等待
                       System.out.println(Thread.currentThread().getName()+":take - "+strs.take());
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
            }).start();
        }
        
    }

}
