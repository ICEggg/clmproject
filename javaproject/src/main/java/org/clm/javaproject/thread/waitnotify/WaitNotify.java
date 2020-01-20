package org.clm.javaproject.thread.waitnotify;

/**
 * wait和notify的例子
 * 当方法way1的count = 3时候，方法way2开始执行
 * 当方法way2的count = 6 时候，方法way1从4开始打印到10
 * 最后方法way2从7打印到10
 */
public class WaitNotify {
    static class Test{
        //private int count;
        private Object o = new Object();
        public void way(){
            int count = 0;
            synchronized (o){
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
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        o.notify();
                    }
                }
            }
        }

        public void way2(){
            int count = 0;
            synchronized (o){
                System.out.println(Thread.currentThread().getName()+"--开始");
                for (int i = 0; i < 10; i++) {
                    count++;
                    System.out.println(Thread.currentThread().getName()+"---"+count);
                    try {
                        Thread.sleep(500);

                        if(count == 6){
                            o.notify();
                            o.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Test test = new Test();
        new Thread(()->test.way()).start();
        new Thread(()->test.way2()).start();
    }
}
