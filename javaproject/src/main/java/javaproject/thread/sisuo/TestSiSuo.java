package javaproject.thread.sisuo;

/**
 * 死锁的例子，这个例子是错的。
 *
 * 因为way1和way2的方法里，T1和T2每次都是new出来的，原先的锁一直没打开，不断的创建新的锁。
 *
 * 所以程序会一直打印开始
 */
public class TestSiSuo {

    static class T1 implements Runnable{

        public synchronized void way1(){
            System.out.println(Thread.currentThread().getName()+":way1开始");

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
            System.out.println(Thread.currentThread().getName()+":way2开始");
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
        new Thread(new T1(),"aaa").start();
        new Thread(new T2(),"bbb").start();
    }













}
