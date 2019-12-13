package org.clm.javaproject.thread.threadlocal;

/**
 * 线程的局部变量
 * 当加了volatile的时候，输出的结果肯定是lisi
 * 当不加volatile的时候，数据的结果可能是lisi，可能是张三
 */
public class ThreadLocalTest {
    /*volatile*/ static Person person = new Person();

    static class Person{
        String name ="zhangsan";
    }

    public static void main(String[] args) {

        new Thread(()->{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(person.name);
        }).start();

        new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            person.name = "lisi";
        }).start();
    }
}
