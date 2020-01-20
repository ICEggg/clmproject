package org.clm.javaproject.thread.voliatesychronized;

import java.util.concurrent.TimeUnit;

/**
 * volatile和sychronized的区别
 * volatile:可见性
 * sychronized：可见性和原子性
 * sychronized的速度比voliate要慢
 *
 * 下面这个例子不加volatile,程序不会结束
 *              加了后，程序flag才会变成false，程序才会结束
 */
public class VoliateSychronized {
    static class TestVoliate{
        /*volatile*/ boolean flag = true;
        public void test(){
            System.out.println("开始");
            while(flag){

            }
            System.out.println("结束");
        }
    }

    public static void main(String[] args) {
        TestVoliate vs = new TestVoliate();
        new Thread(()->vs.test()).start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            vs.flag = false;
        }).start();
    }

}
