package org.clm.javaproject.thread.synchronize;

/**
 * synchronized的例子
 * 加了synchronized就是加了锁，线程就会有序对count进行操作
 * 当一个线程访问这个方法的时候，其他线程进不来
 *
 *
 * synchronized默认是不公平锁
 * 比如：有四个线程，有一个拿到了锁，执行完成后，剩下的三个线程都想要拿这把锁才能执行。
 * synchronized不会根据哪个线程等待的时间久，就把锁给那个线程。
 * 哪个线程可以拿到锁是随机的
 */
public class T {

    public synchronized void testway(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("testway");
    }

    static class A implements Runnable{
        int count = 10;

        //加了synchronized就是加了锁，线程就会有序对count进行操作
        //当一个线程访问这个方法的时候，其他线程进不来
        @Override
        public synchronized void run() {
            count--;
            System.out.println(Thread.currentThread().getName()+"count:"+count);

        }
    }

    public static void main(String[] args) {

        A a = new A();
        for (int i = 0; i < 5; i++) {
            new Thread(a).start();
        }


        //T t = new T();
        //lambada表达式，把一个方法用线程调用
        //new Thread(()->t.testway()).start();

        //相当于下面这个
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                t.testway();
//            }
//        }).start();

    }
}
