package javaproject.thread.pool;

import java.util.concurrent.*;

/**
 * Callable和runnable很相似
 * 区别是：runable没有返回值，callable有返回值
 */
public class T03_Callable {

    static class TestCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            return "1";
        }
    }

    public static void main(String[] args) {
        int threadNum = 5;
        int result = 0;
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        TestCallable test = new TestCallable();
        for (int i = 0; i < threadNum; i++) {
            try {
                result += Integer.valueOf(executorService.submit(test).get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println(result);


        executorService.shutdown();


    }




}
