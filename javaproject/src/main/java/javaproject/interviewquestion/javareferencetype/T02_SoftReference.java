package javaproject.interviewquestion.javareferencetype;

import java.lang.ref.SoftReference;

/**
 * 软引用
 * 比如m是指向一个内存的，这个byte[]是在这个对象里的，这个数组就是软引用
 *
 * 垃圾回收器内存不够的时候回收。当内存快满的时候，
 * 这时候来了一个大对象放不进去了，就把软引用回收了
 *
 * 执行这个程序，要-Xmx20M，指定堆空间，最大是20M
 * 这样，第二个数组肯定在堆里装不下
 *
 * 应用：软引用适合当缓存，比如一个图片，先放缓存，装不下的时候就删了。
 */
public class T02_SoftReference {
    public static void main(String[] args){
        SoftReference<byte[]> m = new SoftReference<>(new byte[1024*1024*10]);
        //m.get指向软引用，指向这个byte[]
        System.out.println(m.get());
        System.gc();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(m.get());

        //再分配一个数组，heap装不下，这时候垃圾会先回收一次，
        // 如果回收不掉，就会把软引用回收了
        byte[] b = new byte[1024*1024*15];
        System.out.println(m.get());
    }
}
