package javaproject.interviewquestion.javareferencetype;

import java.io.IOException;

/**
 * 强引用
 * m new出来的时候指向一个内存地址，
 * 当m=null时，代表这个对象没有被任何地方引用了
 * 就可以回收了，就会调用finalize方法
 */
public class T01_NormalReference {
    public static void main(String[] args) throws IOException {
        M m = new M();
        m = null;
        System.gc();

        /**
         * 有这一行的目的是：system.gc和main不是同一个线程
         * main执行完就结束了，gc是后台执行，main结束了就看不到gc的结果了
         */

        System.in.read();
    }


}
