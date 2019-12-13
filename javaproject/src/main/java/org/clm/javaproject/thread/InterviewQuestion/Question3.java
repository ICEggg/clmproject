package org.clm.javaproject.thread.InterviewQuestion;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 有N张火车票，每张票都有一个编号
 * 同时有十个窗口对外售票
 * 写一个模拟程序
 *
 * 分析下面代码可能会发生什么问题？
 * 重复销售？超量销售？
 *
 * TicketSeller1,TicketSeller2 都会出问题
 * TicketSeller3 效率慢
 * TicketSeller4 用了java1.6后的同步容器，完美解决
 */
public class Question3 {

    /**
     * 多个线程没有加锁，可能同时拿到同一张票，然后就会同一张票卖了好几次
     */
    static class TicketSeller1{
        static List<String> tickets = new ArrayList<>();
        static {
            for (int i = 0; i < 10000; i++) {
                tickets.add("票编号："+i);
            }
        }

        public static void main(String[] args) {
            for (int i = 0; i < 10; i++) {
                new Thread(()->{
                    while(tickets.size()>0){
                        System.out.println("销售了--"+tickets.remove(0));
                    }
                }).start();
            }
        }
    }


    /**
     * 替换成同步容器vector后，还是有问题
     * 第二个问题: 换成vector后（vector是线程安全的同步容器），也不行。因为判断和操作分离了。
     *              vector的remove方法和list方法是线程安全的，但是while判断list的大小，到执行remove方法中间这段逻辑，会出问题
     *              比如中间这段代码需要执行10秒，在10秒内，又有其他线程remove了一个数据，那这个list的大小就有问题了
     */
    static class TicketSeller2{
        static Vector<String> tickets = new Vector<>();

        static {
            for (int i = 0; i < 10000; i++) {
                tickets.add("票编号："+i);
            }
        }

        public static void main(String[] args) {
            for (int i = 0; i < 10; i++) {
                new Thread(()->{
                    while(tickets.size()>0){

                       try {
                           Thread.sleep(10);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }

                        System.out.println("销售了--"+tickets.remove(0));
                    }
                }).start();
            }
        }

    }


    /**
     * 这个没有问题，因为list加了锁
     * 缺点是效率不高，因为每卖一张票都要加个锁
     */
    static class TicketSeller3{
        static List<String> tickets = new ArrayList<>();

        static {
            for (int i = 0; i < 10000; i++) {
                tickets.add("票编号："+i);
            }
        }

        public static void main(String[] args) {
            for (int i = 0; i < 10; i++) {
                new Thread(()->{
                    while(true){
                        synchronized (tickets){
                            if(tickets.size()<0) break;

                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println("销售了--"+tickets.remove(0));

                        }
                    }
                }).start();
            }
        }
    }


    /**
     * 最优解
     * java 1.5之后增加的并发容器ConcurrentLinkedQueue
     *
     * 1和2的判断是：先判断list是否为空，再取值（判断之后，对队列进行了操作）
     * 4是：先poll，先取值，在判断拿到的这个值是不是为空的，（判断之后，没有再对队进行操作了）
     *
     * 所以4是不会出问题的
     */
    static class TicketSeller4{
        static Queue<String> tickets = new ConcurrentLinkedQueue<>();
        static {
            for (int i = 0; i < 10000; i++) {
                tickets.add("票编号："+i);
            }
        }

        public static void main(String[] args) {
            for (int i = 0; i < 10; i++) {
                new Thread(()->{
                    while(true){
                        String s = tickets.poll();
                        if(s == null) break;
                        else System.out.println("销售了--"+s);
                    }
                }).start();
            }
        }
    }

}
