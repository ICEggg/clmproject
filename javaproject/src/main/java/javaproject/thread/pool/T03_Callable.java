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
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        TestCallable test = new TestCallable();
        Future<String> future = executorService.submit(test);
        try {
            String result = future.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }




}
