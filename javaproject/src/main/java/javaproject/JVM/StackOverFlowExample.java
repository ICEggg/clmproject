package javaproject.JVM;

import org.apache.hadoop.fs.shell.Count;

/**
 * 递归演示 栈内存溢出   java.lang.StackOverFlowError
 */
public class StackOverFlowExample {
    private static int count = 0;
    public static void main(String[] args) {

        try {
            m1();
            //这里用exception不会打印下面那句话，用throwable才行。不知道为什么
        } catch (Throwable e) {
            System.out.println(count);
            e.printStackTrace();
        }
    }


    static void m1(){
        count++;
        m1();
    }
}
