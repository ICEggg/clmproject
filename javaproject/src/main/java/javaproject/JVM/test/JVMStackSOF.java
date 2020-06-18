package javaproject.JVM.test;

import java.util.Map;

/**
 * 栈溢出
 * -Xss128k
 */
public class JVMStackSOF {
    private int stackLength = 1;

    public void stackLeak(){
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {

        String ab = new StringBuilder("a").append("b").toString();
        System.out.println(ab.intern() == ab);
        String cd = new StringBuilder("ja").append("va").toString();
        System.out.println(cd.intern() == cd);

        JVMStackSOF oom = new JVMStackSOF();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length :" + oom.stackLength );
            throw e;
        }
    }


}
