package javaproject.thread.countdownlatch;


import java.util.concurrent.CountDownLatch;

/**
 * 和Wait，Notify的功能相同
 *
 * 用CountDownLatch这个类，实现起来并发更好
 *
 * countDownLatch这个类使一个线程等待其他线程各自执行完毕后再执行。
 * 是通过一个计数器来实现的，计数器的初始值是线程的数量。每当一个线程执行完毕后，计数器的值就-1，
 * 当计数器的值为0时，表示所有线程都执行完毕，然后在闭锁上等待的线程就可以恢复工作了。
 */
public class CountDownLatchDemo {

    static class Test{
        private CountDownLatch latch = new CountDownLatch(1);
        public void way(){
            int count = 0;
            System.out.println(Thread.currentThread().getName()+"--开始");
            for (int i = 0; i < 10; i++) {
                count++;
                System.out.println(Thread.currentThread().getName()+"---"+count);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(count == 3){
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        public void way2(){
            int count = 0;
            System.out.println(Thread.currentThread().getName()+"--开始");
            for (int i = 0; i < 10; i++) {
                count++;
                System.out.println(Thread.currentThread().getName()+"---"+count);
                try {
                    Thread.sleep(500);

                    if(count == 6){
                        latch.countDown();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void main(String[] args) {
        Test test = new Test();
        new Thread(()->test.way(),"a").start();
        new Thread(()->test.way2(),"b").start();
    }
}
