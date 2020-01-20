package org.clm.javaproject.thread.sisuo;

/**
 * 死锁的例子（不知道是不是对的）
 */
public class TestSiSuo {

    static class T1 implements Runnable{

        public synchronized void way1(){
            System.out.println("way1开始");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            T2 t2 = new T2();
            t2.way2();
            System.out.println("way1结束");
        }

        @Override
        public void run() {
            way1();
        }
    }

    static class T2 implements  Runnable{
        public synchronized void way2(){
            System.out.println("way2开始");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            T1 t1 = new T1();
            t1.way1();
            System.out.println("way2结束");
        }

        @Override
        public void run() {
            way2();
        }
    }

    public static void main(String[] args) {
        new Thread(new T1()).start();
        new Thread(new T2()).start();
    }













}
