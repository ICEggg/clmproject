package javaproject.thread;

public class A {

    public static void main(String[] args) {
        Toy1 toy1 = new Toy1();
        Toy2 toy2 = new Toy2();

        Thread t1 = new Thread(() -> {
            toy1.run();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            toy2.run();

        },"t11111");
        Thread t2 = new Thread(() -> {
            toy2.run();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            toy1.run();

        },"t2222");

        t1.start();
        t2.start();


    }

}

class Toy1{

    public synchronized void run() {
        while (true){
            System.out.println(Thread.currentThread().getName()+":toy1");
        }
    }
}
class Toy2{

    public synchronized void run() {
        while (true){
            System.out.println(Thread.currentThread().getName()+":toy2");
        }
    }
}
