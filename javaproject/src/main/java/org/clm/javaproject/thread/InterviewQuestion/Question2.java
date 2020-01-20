package org.clm.javaproject.thread.InterviewQuestion;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 写一个固定容量的同步容器，拥有put和get方法，以及getcount方法
 * 能够支持2个生产者线程和10个消费者线程的阻塞调用
 *
 * 第二种方法
 * 使用lock和condition完成
 * lock,await,signalall一起使用
 * 可以精确指定哪些线程被唤醒
 *
 */
public class Question2 {

    static class MyContainer<T>{
        final private LinkedList<T> containerlist = new LinkedList<>();
        final private Integer max = 10;
        private int count = 0;

        private Lock lock = new ReentrantLock();
        private Condition producer = lock.newCondition();
        private Condition consumer = lock.newCondition();

        public void put(T t){
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName()+"获得了锁");
                while(containerlist.size()==max){
                    System.out.println(Thread.currentThread().getName()+":容器的容量满了："+containerlist.size());
                    producer.await();
                }

                System.out.println(t);
                containerlist.add(t);
                count++;
                System.out.println("叫醒所有消费者");
                consumer.signalAll();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }

        public T get(){
            T t = null;
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName()+"获得了锁");
                while(containerlist.size() == 0){
                    System.out.println(Thread.currentThread().getName()+":容器的空了："+containerlist.size());
                    consumer.await();
                 }

                t = containerlist.removeFirst();
                System.out.println(Thread.currentThread().getName()+":消费者消费了："+t);
                count--;
                System.out.println("叫醒所有生产者");
                producer.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
            return t;
        }

        public Integer getcount(){
            return containerlist.size();
        }
    }

    public static void main(String[] args) {
        MyContainer<String> c = new MyContainer<>();
        //启动消费者线程
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                for (int j = 0; j < 5; j++) {
                    System.out.println(c.get());
                }
            },"c"+i).start();
        }

        //启动生产者线程
        for (int i = 0; i < 2; i++) {
            new Thread(()->{
                for (int j = 0; j < 25; j++) {
                    c.put(Thread.currentThread().getName()+"   "+j);
                }
            },"p"+i).start();
        }


    }

}
