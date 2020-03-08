package javaproject.thread.threadlocal;

/**
 * 线程的局部变量  ThreadLocal
 * ThreadLocal是空间换时间，sychronized是时间换空间，前者效率更高
 * ThreadLocal操作的是自己线程的变量，sychronized是线程获取到锁的时候才能操作变量
 *
 * ThreadLocal会导致内存泄漏？？
 */
public class ThreadLocalTest2 {
    //static Person person = new Person();
    private static ThreadLocal<Person> tl = new ThreadLocal<>();
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
            System.out.println(tl.get());
        }).start();

        new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tl.set(new Person());
        }).start();
    }
}
