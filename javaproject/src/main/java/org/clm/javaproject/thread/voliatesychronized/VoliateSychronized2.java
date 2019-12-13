package org.clm.javaproject.thread.voliatesychronized;

import java.util.ArrayList;
import java.util.List;

/**
 * volatile 和 synchronized的区别
 * synchronized保持原子性，所以最后答应的count是50000
 * voliate只有可见性，没有原子性，所以打印的结果<50000
 */
public class VoliateSychronized2 {

    static class Test2{
        /*volatile*/ private int count;

        public synchronized void way2(){
            for (int i = 0; i < 10000; i++) {
                count++;
                //System.out.println(count);
            }
        }

    }

    public static void main(String[] args) {
        Test2 t2 = new Test2();
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new Thread(()->t2.way2()));
        }

        for (int i = 0; i < list.size(); i++) {
            list.get(i).start();

//            try {
//                list.get(i).join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

        list.forEach((o)->{
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(t2.count);
    }
}
