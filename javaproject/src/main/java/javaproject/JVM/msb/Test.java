package javaproject.JVM.msb;

import java.util.concurrent.atomic.AtomicInteger;

public class Test {
    public static void main(String[] args) {
        int a = 0;
        for (int i = 0; i < 100; i++) {
            a++;
        }
    }




}


class ThreadLocalId {
    // Atomic integer containing the next thread ID to be assigned
    private static final AtomicInteger nextId = new AtomicInteger(0);

    // Thread local variable containing each thread's ID
    private static final ThreadLocal <Integer> threadId =
            new ThreadLocal<Integer>()
            {
                @Override
                protected Integer initialValue() {
                    return nextId.getAndIncrement();
                }
            };

    // Returns the current thread's unique ID, assigning it if necessary
    public static int get() {
        return threadId.get();
    }
    public static void remove() {
        threadId.remove();
    }
}

class ThreadLocalMain {
    private static void incrementSameThreadId(){
        try{
            for(int i=0;i<5;i++){
                System.out.println(Thread.currentThread()
                        +"_"+i+",threadId:"+
                        ThreadLocalId.get());
            }
        }finally {
            ThreadLocalId.remove();
        }
    }

    public static void main(String[] args) {
        incrementSameThreadId();
        new Thread(new Runnable() {
            @Override
            public void run() {
                incrementSameThreadId();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                incrementSameThreadId();
            }
        }).start();
    }
}

