package javaproject.thread.bingfacontainer;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * 写时复制容器，copy on write
 * 多线程环境下，写时效率低，读时效率高
 * 适合写少读多的环境
 *
 * 应用：比如一个监听器list，用户要一直去读监听器的内容，但是添加一个新的监听器就不经常
 *
 * 当往里添加一个元素的时候，会把容器复制一个新的，然后往新的里面添加一个，然后指向这个新的
 */
public class T02_CopyOnWriteList {
    public static void main(String[] args) {
        List<String> lists =
                //new ArrayList<>();    //这个会出并发问题
                //new Vector<>();
                new CopyOnWriteArrayList<>();
        Random r = new Random();
        Thread[] ths = new Thread[100];

        for (int i = 0; i < ths.length; i++) {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j <1000; j++) {
                        lists.add("a"+r.nextInt(10000));
                    }
                }
            };
            ths[i] = new Thread(task);
        }


        long start = System.currentTimeMillis();
        Arrays.asList(ths).forEach(o->o.start());
        Arrays.asList(ths).forEach(o->{
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long end = System.currentTimeMillis();
        System.out.println(end-start);

        System.out.println(lists.size());
    }

}
