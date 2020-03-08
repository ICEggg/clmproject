package javaproject.thread.InterviewQuestion;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程，一个线程输出1234.。。。 一个线程输出abcd。。。
 * 最终要这样的输出结果：1a2b3c交替输出
 * 其实个题要考的是wait，notify，synchronize
 * 下面写了多个方法，供学习。LockSupport这个方法挺好的
 *
 * wait：等待的时候，放弃线程，到等待队列里，不占cpu资源，重量级锁
 *
 * 自旋锁：一直拿着线程，在那边空转也不放开，一直占着资源，轻量级锁，
 * 好处是：不经过操作系统（不明白原因）
 * 应用场景：那些迅速就会继续执行的逻辑，不会长时间在那边空转
 *
 */
public class Question4 {

    public static void main(String[] args) throws IOException {
        char[] aI = "1234567".toCharArray();
        char[] aC = "abcdefg".toCharArray();

        //LockSupport实现
        //way1(aI,aC);
        //自旋锁实现
        //way2(aI,aC);
        //BlockingQueue实现
        //way3(aI,aC);
        //pipedstream 管道实现 效率很低
        //way4(aI,aC);
        //wait,notify实现
        //way5(aI,aC);
        //wait,notify实现     指定线程执行顺序
        //way6(aI,aC);
        //countdownlatch 实现     指定线程执行顺序
        //way7(aI,aC);
        //ReenTrantLock
        way8(aI,aC);
    }

    //LockSupport实现
    static Thread t1 = null;
    static Thread t2 = null;
    public static void way1(char[] aI,char[] aC){
        t1 = new Thread(() -> {
            for (char c : aI) {
                System.out.println(c);
                //让t2这个线程继续运行
                LockSupport.unpark(t2);
                //停止当前线程
                LockSupport.park();
            }
        },"t1");

        t2 = new Thread(() -> {
            for (char c : aC) {
                LockSupport.park();
                System.out.println(c);
                LockSupport.unpark(t1);
            }
        },"t2");

        t1.start();
        t2.start();
    }

    /**
     * 枚举实现
     * 枚举也可以用AtomicInteger原子类：0,1当做标识符来判断，
     * 枚举的好处是：这里只有T1,T2,只有这两个。用数字的话，就可以3,4,5，随便写了
     */
    enum ReadyToRun {T1,T2}
    static volatile  ReadyToRun r = ReadyToRun.T1; //为什么要用volatile
    public static void way2(char[] aI,char[] aC){
        new Thread(() -> {
            for (char c : aI) {
                while (r != ReadyToRun.T1) {
                }
                System.out.println(c);
                r = ReadyToRun.T2;
            }
        }, "t1").start();

        new Thread(() -> {
            for (char c : aC) {
                while (r != ReadyToRun.T2) {
                }
                System.out.println(c);
                r = ReadyToRun.T1;
            }
        }, "t2").start();

    }

    /**
     * BlockingQueue实现，这个方法不推荐，就是为了开拓思维一下
     */
    static BlockingQueue<String> q1 = new ArrayBlockingQueue<String>(1);
    static BlockingQueue<String> q2 = new ArrayBlockingQueue<String>(1);
    public static void way3(char[] aI,char[] aC){
        new Thread(()->{
            for (char c : aI){
                System.out.println(c);
                try {
                    q1.put("ok");
                    //从q2拿一个值，没取到就阻塞着
                    q2.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1").start();

        new Thread(()->{
            for (char c : aC){
                try {
                    q1.take();
                    System.out.println(c);
                    q2.put("ok");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        },"t2").start();
    }

    /**
     * 管道流，用于线程之间通信的，两个管道，一个只能发，一个只能收
     * 一根不能同时又收，又发
     * *******生产上几乎不用，效率太低，单纯扩展知识面******
     * 这里的read和write是阻塞操作
     */
    public static void way4(char[] aI,char[] aC) throws IOException {
        PipedInputStream input1 = new PipedInputStream();
        PipedInputStream input2 = new PipedInputStream();
        PipedOutputStream output1 = new PipedOutputStream();
        PipedOutputStream output2 = new PipedOutputStream();

        input1.connect(output2);
        input2.connect(output1);
        String msg = "your turn";

        new Thread(()->{
            byte[] buffer = new byte[9];
            try {
                for (char c : aI){
                    //read是阻塞操作
                    input1.read(buffer);
                    if(new String(buffer).equals(msg)){
                        System.out.println(c);
                    }
                    //write是阻塞操作
                    output1.write(msg.getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        },"t1").start();

        new Thread(()->{
            byte[] buffer = new byte[9];
            try {
                for (char c : aC){
                    System.out.println(c);
                    output2.write(msg.getBytes());
                    input2.read(buffer);
                    if(new String(buffer).equals(msg)){
                        continue;
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        },"t2").start();

    }


    /**
     * 用wait，notify实现，有个缺点：输出可能是1a2b，或者a1b2这种顺序
     * 谁先运行时不确定了，way6用自旋解决
     *
     * 这个对象的作用就是当开关
     */
    final static Object o = new Object();
    public static void way5(char[] aI,char[] aC){
        new Thread(()->{
            synchronized (o){
                for (char c :aI){
                    System.out.println(c);
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //因为不管谁先执行完了，肯定有一个是wait状态
                o.notify();
            }
        },"t1").start();


        new Thread(()->{
            synchronized (o){
                for (char c :aC){
                    System.out.println(c);
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify();
            }
        },"t2").start();


    }

    /**
     * t5的改进，让t2先运行（指定线程的执行顺序）,自旋的方式
     * 或者
     * countdownlatch
     */
    private static volatile boolean t2Start = false;
    public static void way6(char[] aI,char[] aC){
        new Thread(()->{
            synchronized (o){
                //如果t2没启动，t1就在这边等着
                while(!t2Start){
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                for (char c :aI){
                    System.out.println(c);
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //因为不管谁先执行完了，肯定有一个是wait状态
                o.notify();
            }
        },"t1").start();


        new Thread(()->{
            synchronized (o){
                t2Start = true;
                for (char c :aC){
                    System.out.println(c);
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify();
            }
        },"t2").start();


    }

    /**
     * t5的改进，让t2先运行（指定线程的执行顺序）,countdownlatch的方式
     */
    private static CountDownLatch latch = new CountDownLatch(1);
    public static void way7(char[] aI,char[] aC){
        new Thread(()->{
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (o){
                for (char c :aI){
                    System.out.println(c);
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //因为不管谁先执行完了，肯定有一个是wait状态
                o.notify();
            }
        },"t1").start();


        new Thread(()->{
            synchronized (o){
                for (char c :aC){
                    System.out.println(c);
                    latch.countDown();
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify();
            }
        },"t2").start();


    }

    /**
     * ReenTrantLock实现
     *
     */
    public static void way8(char[] aI,char[] aC){
        Lock lock = new ReentrantLock();
        //这两相当于把一把锁换成了两把锁，两把锁分别对应两个线程，自己的锁管自己的线程
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();

        new Thread(()->{
            try {
                lock.lock();
                for (char c :aI){
                    System.out.println(c);
                    //t2 拿着condition2的这个线程先启动
                    condition2.signal();
                    //t1 先等待
                    condition1.await();
                }
                condition2.signal();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        },"t1").start();


        new Thread(()->{
            try {
                lock.lock();
                for (char c :aC){
                    System.out.println(c);
                    condition1.signal();
                    condition2.await();
                }
                condition1.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        },"t2").start();


    }
}
