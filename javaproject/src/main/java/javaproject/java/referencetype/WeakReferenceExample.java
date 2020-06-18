package javaproject.java.referencetype;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 弱引用：只要碰到垃圾回收，就被回收掉
 * -Xmx20m -XX:+PrintGCDetails -verbose:gc
 *
 * 可以配合引用队列，清除掉，垃圾回收之后的null对象
 */
public class WeakReferenceExample{

    private static final int _4MB = 4*1024*1024;

    public static void main(String[] args) {
        List<WeakReference<byte[]>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            WeakReference<byte[]> ref = new WeakReference<>(new byte[_4MB]);
            list.add(ref);
            for (WeakReference<byte[]> w :list){
                System.out.println(w.get()+ " ");
            }
            System.out.println();
        }
        System.out.println("循环结束："+list.size());
    }

}

