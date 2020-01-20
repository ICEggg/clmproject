package org.clm.javaproject.thread.InterviewQuestion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 写一个固定容量的同步容器，拥有put和get方法，以及getcount方法
 * 能够支持2个生产者线程和10个消费者线程的阻塞调用
 *
 * 第一种方法
 * 使用synchronized，wait，notify，notifyall完成
 *
 *
 * *******大部分情况下，wait都是和while一块用的*********
 * *******大部分情况下，都是用的notifyall********
 */
public class Question1 {

    static class MyContainer<T>{
        final private LinkedList<T> containerlist = new LinkedList<>();
        final private Integer max = 10;
        private int count = 0;

        public synchronized void put(T t){
            //这里为什么用while不用if？
            //因为当两个线程都走到wait方法的时候，两个线程都等待在第this.wait()这一行，
            // 如果当此时容器数量不是满的了，生产者的线程又要重新往里加
            //这个时候代码是从this.wait()后往下执行的
            //如果用if，线程A和线程B都往下继续执行的时候，线程A已经往容器里增加了一个，这时候容器满了
            //线程B也往下走，并且没有判断容器是否是满的，就可能出现容器里又添加了一个东西
            //用while的话，线程B往下走的时候，会在走到while(containerlist.size() == max)这里去判断容器是否是满的
            while(containerlist.size() == max){
                try {
                    System.out.println(Thread.currentThread().getName()+":容器的容量满了："+containerlist.size());
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            containerlist.add(t);
            System.out.println(t);
            ++count;

            //为什么使用notifyall而不是notify？
            //因为使用notify只能唤醒一个等待的线程，如果唤醒的是一个生产者线程，那程序就会变成死锁了。
            this.notifyAll();   //通知消费者进行消费
        }

        public synchronized T get(){
            T t = null;
            while(containerlist.size() == 0){
                try {
                    System.out.println(Thread.currentThread().getName()+":容器的空了："+containerlist.size());
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            t = containerlist.removeFirst();
            System.out.println(Thread.currentThread().getName()+":消费者消费了："+t);
            count--;
            this.notifyAll(); //通知生产者进行生产
            return t;
        }

        public synchronized Integer getcount(){
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
