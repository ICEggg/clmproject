package javaproject.thread.bingfacontainer;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

/**
 * 这个包下记录的都是并发容器
 *
 * 计算那个map的效率是最高的
 * map和set同理
 *
 * 理论上，并发时：ConcurrentHashMap 比 HashTable 更快
 *
 * ConcurrentSkipListMap 跳表排序，插入速度比较慢因为做排序了，查询比较快因为做排序了
 *
 * 不加锁：hashmap,treemap,linkedhashmap
 * 加锁的：hashtable（现在用的比较少了）（并发量不高的时候可以用）
 *并发量更高的时候用ConcurrentHashMap
 * 并发量更高，并且需要排序的时候，用ConcurrentSkipListMap
 * Collections.synchronizedmap(map)/synchronizedlist(list) 可以把不带锁的map/list变成带锁的（并发量不高的时候）
 *
 */
public class T01_ConcurrentMap {
    public static void main(String[] args) {
        //两种map
        //Map<String,String> map = new ConcurrentHashMap<>();
        //Map<String,String> map = new ConcurrentSkipListMap<>();      //高并发并且排序

        Map<String,String> map = new Hashtable<>(); //hashtable所有方法都加了锁了
        int index = 0;
        Random r = new Random();
        Thread[] ths = new Thread[100];
        CountDownLatch latch = new CountDownLatch(ths.length);

        long start = System.currentTimeMillis();
        for (int i = 0; i < ths.length; i++) {
            ths[i] = new Thread(()->{
                for (int j = 0; j <10000; j++) {
                    map.put("a"+r.nextInt(100000),"a"+r.nextInt(100000));
                }
                latch.countDown();
            });
        }

        Arrays.asList(ths).forEach(o->o.start());

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println(map.size());
        System.out.println(end-start);
    }



}
