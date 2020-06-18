package javaproject.java.referencetype;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.List;

/**
 * 虚引用
 *
 * 虚引用是get不到的
 */
public class PhantomReferenceExample {
    private static final List<Object> list = new LinkedList<>();
    private static final ReferenceQueue<M> QUEUE = new ReferenceQueue<M>();

    public static void main(String[] args) {
        PhantomReference<M> phantomReference = new PhantomReference<M>(new M(),QUEUE);
        new Thread(()->{
            while(true){
                list.add(new byte[1024*1024]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(phantomReference.get());
            }
        }).start();

        new Thread(()->{
            while(true){
                Reference<? extends M> poll = QUEUE.poll();
                if (poll != null){
                    System.out.println("虚引用对象被jvm回收了:"+poll);
                }
            }
        }).start();



    }

    static class M{

    }
}
