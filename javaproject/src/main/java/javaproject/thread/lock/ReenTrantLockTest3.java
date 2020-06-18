package javaproject.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock
 * way1拿着锁，死循环，way2永远拿不到锁，所以永远执行不了。
 * way2里的lock.lockInterruptibly();  意思是：在way2等不到可还是一直等待的时候,主线程可以打断他，让他不要再等了
 */
public class ReenTrantLockTest3 {
    Lock lock = new ReentrantLock();

    private int count = 0;
    public void way1(){
        lock.lock();
        for (int i = 0; i < 10; i++) {
            count++;
            System.out.println(Thread.currentThread().getName()+"----"+count);
            try {
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lock.unlock();
    }

    public void way2(){
        try {
            lock.lockInterruptibly();
            for (int i = 0; i < 10; i++) {
                count++;
                System.out.println(Thread.currentThread().getName()+"----"+count);
            }
        } catch (InterruptedException e) {
            System.out.println("error");
            e.printStackTrace();
        }finally {
            if(lock.tryLock()){
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReenTrantLockTest3 test = new ReenTrantLockTest3();
        Thread thread1 = new Thread(() -> test.way1());
        thread1.start();
        Thread thread2 = new Thread(() -> test.way2());
        thread2.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread2.interrupt();
    }
}
