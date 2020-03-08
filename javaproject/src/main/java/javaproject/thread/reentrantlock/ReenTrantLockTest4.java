package javaproject.thread.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  ReentrantLock默认是公平锁
 *  比如：有四个线程，有一个拿到了锁，执行完成后，剩下的三个线程都想要拿这把锁才能执行。
 *  ReentrantLock会根据哪个线程等待的时间久，就把锁给那个线程。
 *
 */
public class ReenTrantLockTest4 extends Thread{
    private static Lock lock = new ReentrantLock(false);//参数为true，是公平锁
    private int count = 0;

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            lock.lock();
            System.out.println(Thread.currentThread().getName()+"----获得锁");
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReenTrantLockTest4 test1 = new ReenTrantLockTest4();
        ReenTrantLockTest4 test2 = new ReenTrantLockTest4();
        test1.start();
        test2.start();
    }
}
