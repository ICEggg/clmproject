package javaproject.java.referencetype;

import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 当软引用被垃圾回收掉之后，值为null，要把这些null给清理掉，
 * 就用到了引用队列
 * 当软引用对象被回收时，软引用对象就会被加到队列中，然后遍历，把null的对象清理掉
 */
public class SoftReferenceWithQueueExample {
    private static final int _4MB = 4*1024*1024;

    public static void main(String[] args) throws IOException {
        //软引用 定义SoftReference对象指向 byte[]，当堆内存不够，垃圾回收就把SoftReference指向的byte[]回收了

        //定义软引用队列
        ReferenceQueue<byte[]> queue = new ReferenceQueue<>();

        List<SoftReference<byte[]>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            //参数加上 queue，关联了引用队列
            SoftReference<byte[]> ref = new SoftReference<>(new byte[_4MB],queue);
            System.out.println(ref.get());
            list.add(ref);
            System.out.println(list.size());
        }

        //remove为null的软引用对象
        Reference<? extends byte[]> poll = queue.poll();
        while(poll != null){
            list.remove(poll);
            poll = queue.poll();
        }

        System.out.println("循环结束：" + list.size());
        for (SoftReference<byte[]> ref : list) {
            System.out.println(ref.get());

        }

    }


}
