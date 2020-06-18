package javaproject.java.referencetype;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 软引用
 * -Xmx20m -XX:+PrintGCDetails -verbose:gc
 */
public class SoftReferenceExample {

    private static final int _4MB = 4*1024*1024;

    public static void main(String[] args) throws IOException {
        //正常引用，应该属于强引用，堆内存不足会直接报错oom
//        List<byte[]> list = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            list.add(new byte[_4MB]);
//        }
//        System.in.read();

        //软引用 定义SoftReference对象指向 byte[]，当堆内存不够，垃圾回收就把SoftReference指向的byte[]回收了
        soft();
    }

    public static void soft(){
        List<SoftReference<byte[]>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            SoftReference<byte[]> ref = new SoftReference<>(new byte[_4MB]);
            System.out.println(ref.get());
            list.add(ref);
            System.out.println(list.size());
        }

        System.out.println("循环结束：" + list.size());
        for (SoftReference<byte[]> ref : list) {
            System.out.println(ref.get());

        }
    }

}
