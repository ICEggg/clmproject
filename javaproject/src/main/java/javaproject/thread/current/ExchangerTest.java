package javaproject.thread.current;

import java.util.concurrent.Exchanger;

/**
 * exanger 交换
 *
 * 发生在两个线程间
 * 可以理解为：exchanger 有两个格子，两个格子放了两个数据，
 * 两个线程运行的时候，数据交换，然后继续往后执行
 *
 */
public class ExchangerTest {

    static Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {

        new Thread(()->{
            String s = "t1";
            try {
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"  "+s);
        },"t1").start();

        new Thread(()->{
            String s = "t2";
            try {
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"  "+s);
        },"t2").start();


    }
}
