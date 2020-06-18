package javaproject.interviewquestion;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.List;

/**
 * 虚引用
 * 一个虚引用指向M,当M被回收的时候，这个回收的信心会被放到一个队列里。
 * 作用就是当某个对象被回收的时候，能知道哪个对象被回收了
 *
 * 应用：管理堆外内存
 * 虚引用指向的对象，所在的位置，是操作系统直接管理的内存，
 * jvm管理的内存是操作系统内存的一部分
 * 虚引用指向的对象是放在堆外的，就是jvm，不能够进行垃圾回收，不能操作的地方。
 *
 * 可是这些对象也是jvm创建的，销毁等操作的时候，这些也是要干掉的
 * 所以把虚引用的对象放到队列里，jvm操作队列，
 * 就可以知道这里面的对象，放在堆外的那个地方，就可以清除了
 */
public class T04_PhantomReference {
    private static final List<Object> LIST = new LinkedList<>();
    private static final ReferenceQueue<M> QUEUE = new ReferenceQueue<>();

    public static void main(String[] args) {
        PhantomReference<M> phantomReference = new PhantomReference<>(new M(),QUEUE);

        new Thread(()->{
            while (true){
                //这步是想把堆塞满， 然后m被回收，就拿不到m的值了，
                //但是m的值其实永远拿不到
                LIST.add(new byte[1024*1024]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                //是拿不到m的值得
                System.out.println(phantomReference.get());
            }
        }).start();

        new Thread(()->{
            while (true){
                Reference<? extends M> poll = QUEUE.poll();
                if(poll !=null){
                    System.out.println("-----虚引用对象被jvm回收了-----" +poll);
                }
            }
        }).start();
    }
}
