package javaproject.thread.pool;

import java.util.concurrent.Executor;

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
