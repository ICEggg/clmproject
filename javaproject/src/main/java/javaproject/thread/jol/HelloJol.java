package javaproject.thread.jol;

import org.openjdk.jol.info.ClassLayout;


/**
 * JOL是openjdk的一个工具，maven下载
 *
 * 是查看对象在内存中怎么布局的
 */
public class HelloJol {
    public static void main(String[] args) {
        Object o = new Object();
        //解析一个对象，并打印出来
        String s = ClassLayout.parseInstance(o).toPrintable();
        System.out.println(s);

        System.out.println("=========================");
        synchronized (o){
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }

    }
}
