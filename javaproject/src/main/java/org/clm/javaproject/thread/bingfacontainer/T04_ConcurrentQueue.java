package org.clm.javaproject.thread.bingfacontainer;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

/**
 * Queue的实现
 * 并发情况下用这两种队列：
 * ConcurrentLinkedQueue，BlockingQueue
 *
 *
 */
public class T04_ConcurrentQueue {
    public static void main(String[] args) {
        Queue<String> strs = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < 10; i++) {
            strs.offer("a"+i);
        }
        System.out.println(strs);
        System.out.println(strs.size());

        //poll，拿出一个元素，并删除队列中这个元素
        System.out.println(strs.poll());
        System.out.println(strs.size());

        //peek拿出一个元素，但是不删除
        System.out.println(strs.peek());
        System.out.println(strs.size());

        //双向队列  可以从两头加，也可以从两头取
        //ConcurrentLinkedDeque
    }

}
