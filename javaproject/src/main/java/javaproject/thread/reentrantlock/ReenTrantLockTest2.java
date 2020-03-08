package javaproject.thread.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock
 * 使用trylock进行尝试锁定，不管锁定与否，方法都将继续进行
 * 也可以根据trylock的返回值判断是否锁定
 * 也可以指定trylock的时间，由于trylock（time）抛出异常，所以要注意trylock的处理，必须放到finally中
 */
public class ReenTrantLockTest2 {
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

    public void way2(){
        try {
            boolean flag = lock.tryLock();//尝试拿到锁，拿到是true，没拿到是false
            //boolean flag2 = lock.tryLock(5, TimeUnit.SECONDS); //等待5秒，尝试拿锁
            if(flag){
                for (int i = 0; i < 10; i++) {
                    count++;
                    System.out.println(Thread.currentThread().getName()+"----"+count);
                }
            }else{
                System.out.println("没拿到锁");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        ReenTrantLockTest2 test = new ReenTrantLockTest2();
        new Thread(()->test.way1()).start();
        new Thread(()->test.way2()).start();
    }
}
