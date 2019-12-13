 package org.clm.javaproject.thread.bingfacontainer;

 import java.util.Random;
 import java.util.concurrent.*;

 /**
  * LinkedTransferQueue
  *
  * 比如：有一个队列，有五个消费者正在往里消费，一个生产者往里生产
  * 当生产者往里生产的时候，这个时候有消费者正在消费，生产者不会把元素放到队列里，而是直接给消费者
  *
  * 用在高并发的情况下
  *
  * 下面的例子：
  * 1.当注释了生产者，放开消费者，程序可以打印出来aaa。
  *     消费者先消费，当往队列transfer的时候，看到有消费者，就直接把值给这个消费者了
  *
  *  2.当注释了消费者，放开生产者，程序没有打印出东西，因为strs.take的时候没有找到消费者，所以程序阻塞了，打印不出来
  */
 public class T08_TransferQueue {
     public static void main(String[] args) {
         LinkedTransferQueue<String> strs = new LinkedTransferQueue<>();

         //消费者线程
         new Thread(()->{
             try {
                 System.out.println(strs.take());
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }).start();

         try {
             strs.transfer("aaa");
         } catch (InterruptedException e) {
             e.printStackTrace();
         }

         //生产者线程
//         new Thread(()->{
//             try {
//                 System.out.println(strs.take());
//             } catch (InterruptedException e) {
//                 e.printStackTrace();
//             }
//         }).start();

     }

 }
