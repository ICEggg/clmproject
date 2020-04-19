package javaproject.mytest;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadTest {

    public static void main(String[] args) {
        MyList<Integer> list = new MyList<>();


        new Thread(()->{
            for (int i = 0; i < 100; i++){
                System.out.println("消费者消费了："+list.get());
            }

        },"consumer").start();

        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                list.add(i);
                System.out.println("生产者生产了："+i);
            }
        },"producer").start();

    }

}

interface Collection_<T>{
    void add(T t);
    int size();
    T get();
}

class MyList<T> implements Collection_<T>{
    //List<T> list = Collections.synchronizedList(new ArrayList<>());
    List<T> list = new LinkedList<>();
    int MAX = 10;
    int count = 0;

    @Override
    public synchronized void add(T t) {
        while(list.size() == 10){
            try {
                //System.out.println("producer wait");
                this.notifyAll();
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(t);
        ++count;
        this.notifyAll();
    }

    @Override
    public synchronized T get(){
        while (list.size()<=0){
            try {
                //System.out.println("consumer wait");
                this.notifyAll();
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        --count;
        T t = list.remove(0);
        return t;
    }

    @Override
    public int size() {
        return count;
    }

}