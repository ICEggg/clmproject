package javaproject.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock可以代替synchronized
 * 注意：必须要手动释放锁  lock.unlock
 * 一般是在finally中释放的
 *
 *
 *  ReentrantLock默认是公平锁
 *  比如：有四个线程，有一个拿到了锁，执行完成后，剩下的三个线程都想要拿这把锁才能执行。
 *  ReentrantLock会根据哪个线程等待的时间久，就把锁给那个线程。
 *
 */
public class ReenTrantLockTest {
    Lock lock = new ReentrantLock();

    private int count = 0;
    public void way1(){
        lock.lock();
        for (int i = 0; i < 10; i++) {
            count++;
            System.out.println(Thread.currentThread().getName()+"----"+count);
        }
        lock.unlock();
    }

    public static void main(String[] args) {
        ReenTrantLockTest test = new ReenTrantLockTest();
        new Thread(()->test.way1()).start();
        new Thread(()->test.way1()).start();
    }
}
