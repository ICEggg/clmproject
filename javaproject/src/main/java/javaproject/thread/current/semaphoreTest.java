package javaproject.thread.current;

import java.util.concurrent.Semaphore;

/**
 * Semaphore 信号
 *
 * 应用： 限流，有多少个线程可以执行
 *
 */
public class semaphoreTest {
    public static void main(String[] args) {
        //这里参数可以写多个，只要线程拿的时候 参数没有减到0，线程就可以执行
        Semaphore s = new Semaphore(1);

        new Thread(()->{
            try {
                //把s的1拿到
                s.acquire();

                System.out.println("t1 running");
                Thread.sleep(200);
                System.out.println("t1 running");


            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                //把1还回去
                s.release();
            }


        }).start();

        new Thread(()->{
            try {
                s.acquire();

                System.out.println("t2 running");
                Thread.sleep(200);
                System.out.println("t2 running");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                s.release();
            }

        }).start();


    }
}
