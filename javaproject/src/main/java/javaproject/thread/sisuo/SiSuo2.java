package javaproject.thread.sisuo;

/**
 * 死锁例子
 * 比如两个人，一个要先拿钥匙再拿车
 *           一个要先拿车再拿钥匙。
 *
 *  两个人各自有一个锁，锁还没释放，就要拿别人的还没释放的锁。
 */
public class SiSuo2 {
    public static void main(String[] args) {
        Yaoshi yaoshi = new Yaoshi();
        Car car = new Car();
        new Thread(new MyThread(0,yaoshi,car),"aaa").start();
        new Thread(new MyThread(1,yaoshi,car),"bbb").start();
    }

}

class Yaoshi{

}

class Car{

}

class  MyThread implements  Runnable{
    int choice;
    public Yaoshi yaoshi;
    public Car car;

    public MyThread(int choice, Yaoshi yaoshi, Car car) {
        this.choice = choice;
        this.yaoshi = yaoshi;
        this.car = car;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(choice == 0){
            synchronized (yaoshi){
                System.out.println(Thread.currentThread().getName()+":先拿钥匙");
                synchronized (car){
                    System.out.println(Thread.currentThread().getName()+":再拿车");
                }
            }
        }else {
            synchronized (car){
                System.out.println(Thread.currentThread().getName()+":先拿车");
                synchronized (yaoshi){
                    System.out.println(Thread.currentThread().getName()+":再拿钥匙");
                }
            }
        }
    }
}
