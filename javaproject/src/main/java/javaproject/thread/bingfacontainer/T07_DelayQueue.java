 package javaproject.thread.bingfacontainer;

import java.util.Random;
import java.util.concurrent.*;

/**
 * T07_DelayQueue 无界队列  默认是有序的
 *
 * 当消费者往队列里拿元素的时候，只有等一段时间之后，才能把它取出来
 * 可以用来做：   定时执行任务
 */
public class T07_DelayQueue {
    static BlockingQueue<Mytask> tasks = new DelayQueue<>();

    static Random r = new Random();
    static class Mytask implements Delayed {
        long runtime;
        Mytask(long rt ){
            this.runtime = rt;
        }

        @Override
        public int compareTo(Delayed o) {
            if(this.getDelay(TimeUnit.MILLISECONDS)< o.getDelay(TimeUnit.MICROSECONDS)){
                return -1;
            }else if(this.getDelay(TimeUnit.MILLISECONDS)> o.getDelay(TimeUnit.MICROSECONDS)){
                return 1 ;
            }else {
                return 0;
            }
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(runtime - System.currentTimeMillis(),TimeUnit.MILLISECONDS);
        }

        @Override
        public String toString() {
            return ""+runtime;
        }
    }

    public static void main(String[] args) {
        long now = System.currentTimeMillis();

        //这个任务表示，现在开始，1秒后开始执行，下面以此类推
        Mytask t1 = new Mytask(now + 1000);
        Mytask t2 = new Mytask(now + 2000);
        Mytask t3 = new Mytask(now + 1500);
        Mytask t4 = new Mytask(now + 2500);
        Mytask t5 = new Mytask(now + 500);

        try {
            tasks.put(t1);
            tasks.put(t2);
            tasks.put(t3);
            tasks.put(t4);
            tasks.put(t5);

            System.out.println(tasks);

            for (int i = 0; i < 5; i++) {
                System.out.println(tasks.take());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
