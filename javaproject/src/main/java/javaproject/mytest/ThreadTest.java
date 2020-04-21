package javaproject.mytest;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
public class ThreadTest {

    public static void main(String[] args) {

        MyList<String> list = new MyList<>();

        for (int j = 0; j < 1; j++) {
            new Thread(()->{
                /*for (int i = 0; i < 100; i++){
                    System.out.println(Thread.currentThread().getName()+"消费了："+list.get());
                }*/
                while(list.size() > 0){
                    System.out.println(Thread.currentThread().getName()+"消费了："+list.get());
                }

            },"consumer"+j).start();
        }

        for (int j = 0; j < 1; j++) {
            new Thread(()->{
                for (int i = 0; i < 1000; i++) {
                    list.add(Thread.currentThread().getName()+","+i);
                    System.out.println(Thread.currentThread().getName()+"生产了："+Thread.currentThread().getName()+","+i);
                }
            },"producer"+j).start();
        }



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
    AtomicInteger count = new AtomicInteger(0);

    @Override
    public synchronized void add(T t) {
        while(list.size() == 10){
            try {
                //this.notifyAll();
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(t);
        count.incrementAndGet();
        this.notifyAll();
    }

    @Override
    public synchronized T get(){
        while (list.size()<=0){
            try {
                //this.notifyAll();
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count.decrementAndGet();
        T t = list.remove(0);
        this.notifyAll();
        return t;
    }

    @Override
    public synchronized int size() {
        return count.get();
    }

}