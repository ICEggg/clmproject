package org.clm.javaproject.thread;


import java.util.concurrent.atomic.AtomicInteger;

public class TTT {
    private static AtomicInteger atom_count = new AtomicInteger(0);

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 100000; i++) {
                        atom_count.incrementAndGet();
                    }
                }
            }).start();
        }
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(atom_count.get());
    }
}
