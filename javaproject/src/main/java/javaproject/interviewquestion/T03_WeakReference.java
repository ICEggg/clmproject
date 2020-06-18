package javaproject.interviewquestion;

import java.lang.ref.WeakReference;

/**
 * 弱引用,遭到gc，就会被回收了
 * 应用：为了解决某些地方的内存泄漏问题，（解决在map里可能存在的内存泄漏问题）
 */
public class T03_WeakReference {
    public static void main(String[] args) {
        WeakReference<M> m = new WeakReference<>(new M());

        System.out.println(m.get());
        System.gc();
        System.out.println(m.get());

        ThreadLocal<M> t1 = new ThreadLocal<>();
        t1.set(new M());
        t1.remove();
    }
}
