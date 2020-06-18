package javaproject.thread.current;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 和countdownlatch类似，countdownlatch是一次性的，CyclicBarrier可以重复
 *
 * 一辆车20人，人满了就开车
 *
 * 应用：比如一个方法有复杂操作，先数据库，在网络请求，再文件，这时候可以用CyclicBarrier
 *      等这些都执行完了，在往下走
 */
public class TestCyclicBarrier {

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(20,()->{
            System.out.println("满人 发车");
        });

        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }


    }

}
