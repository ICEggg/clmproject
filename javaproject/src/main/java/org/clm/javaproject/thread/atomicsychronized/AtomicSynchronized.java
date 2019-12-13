package org.clm.javaproject.thread.atomicsychronized;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Auto原子类，比sychronized的效率要高
 *
 *测试出来是：
 * 当低并发时（小于150000左右），Atomic效率优于synchronized
 * 当高并发时（大于150000左右），synchronized效率优于Atomic
 */
public class AtomicSynchronized {

    static class TestAtomicSynchronized{
        private int count;
        private AtomicInteger atom_count = new AtomicInteger(0);
        private int size = 1000000;

        public synchronized void SychronizedTest(){
            long start = System.currentTimeMillis();
            for (int i = 0; i < size; i++) {
                count++;
            }
            long end = System.currentTimeMillis();
            System.out.println("count:"+count+"synchronized的执行时间是："+(end-start));
        }

        public void AtomicTest(){
            long start = System.currentTimeMillis();
            for (int i = 0; i < size; i++) {
                atom_count.incrementAndGet();
            }
            long end = System.currentTimeMillis();
            System.out.println("atom_count:"+atom_count+"AtomicInteger的执行时间是："+(end-start));
        }
    }

    public static void main(String[] args) {
        TestAtomicSynchronized tas1 = new TestAtomicSynchronized();
        new Thread(()->tas1.SychronizedTest()).start();
        new Thread(()->tas1.AtomicTest()).start();
    }



}
