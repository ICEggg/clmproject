package javaproject.thread.countdownlatch;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier,和countDownLatch有点相似
 *
 * countDownLatch只能使用一次，而CyclicBarrier方法可以使用reset()方法重置，
 * 所以CyclicBarrier方法可以能处理更为复杂的业务场景。
 *
 * 我曾经在网上看到一个关于countDownLatch和cyclicBarrier的形象比喻，就是在百米赛跑的比赛中,
 * 若使用 countDownLatch的话冲过终点线一个人就给评委发送一个人的成绩，10个人比赛发送10次，如果用CyclicBarrier，
 * 则只在最后一个人冲过终点线的时候发送所有人的数据，仅仅发送一次，这就是区别。
 */
public class CycleBarrierDemo {
    private static CyclicBarrier barrier = new CyclicBarrier(9);

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        for (int i = 0; i < 9; i++) {
            new Thread(()->{
                add();

                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        barrier.await();


    }

    public static void add(){
        int count = 0;
        for (int j = 0; j <10000; j++) {
            count++;
        }
        System.out.println(Thread.currentThread().getName()+"--"+count);
    }

}
