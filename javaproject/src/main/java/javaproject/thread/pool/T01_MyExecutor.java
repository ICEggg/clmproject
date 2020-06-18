package javaproject.thread.pool;

import java.util.concurrent.Executor;

/**
 * Executor 是所有线程池的最基本的接口
 */
public class T01_MyExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        new Thread(command).run();
        command.run();
    }

    public static void main(String[] args) {
        new T01_MyExecutor().execute(()->{
            System.out.println("aaa");
        });
    }
}
