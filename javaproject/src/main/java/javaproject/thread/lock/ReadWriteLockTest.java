package javaproject.thread.lock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写分离
 * 共享锁，排他锁
 *
 * 高并发读的时候不加锁，写的时候加锁
 */
public class ReadWriteLockTest {
    static Lock lock = new ReentrantLock();
    private static int value;

    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static Lock readLock = readWriteLock.readLock();
    static Lock writeLock = readWriteLock.writeLock();

    public static void main(String[] args) {
        /**
         * 如果用的是ReentrantLock，那读线程就会一个一个的执行
         *
         * 如果用ReentrantReadWriteLock，读线程就会同时执行
         * 写线程会一个一个执行
         */

        for (int i = 0; i < 18; i++) {
            new Thread(()->{
                //read(lock);
                read(readLock);
            }).start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(()->{
                //write(lock,new Random().nextInt());
                write(writeLock,new Random().nextInt());
            }).start();
        }

    }

    public static void read(Lock lock){

        try {
            lock.lock();
            Thread.sleep(1000);
            System.out.println("read over");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    public static void write(Lock lock , int v){

        try {
            lock.lock();
            Thread.sleep(1000);
            value = v;
            System.out.println("write over");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}
