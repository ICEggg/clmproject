 package org.clm.javaproject.thread.bingfacontainer;

 import java.util.Random;
 import java.util.concurrent.*;

 /**
  * SynchronousQueue 叫做没有容量的队列
  *     如果有个生产者往里生产，必须有个消费者消费掉
  *     用add方法，就会报错Queue full
  *     用put会打印aaa，它会阻塞等待消费者
  */
 public class T09_SynchronizedQueue {

     public static void main(String[] args) {
         BlockingQueue<String> strs = new SynchronousQueue<>();
         new Thread(()->{
             try {
                 System.out.println(strs.take());
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }).start();

         //strs.put("aaa");     //阻塞等待消费者
         strs.add("aaa");
         System.out.println(strs.size());
     }

 }
