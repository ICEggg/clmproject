package javaproject.JVM.test;

import java.util.Map;

/**
 * 在代码中可以写，打印程序运行线程的信息
 */
public class JStackCommand {

    public static void main(String[] args) {
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        for (Map.Entry entry : allStackTraces.entrySet()){
            Thread thread = (Thread) entry.getKey();
            StackTraceElement[] stack = (StackTraceElement[])entry.getValue();
            if(thread.equals(Thread.currentThread())){
                continue;
            }
            System.out.println("线程： " +thread.getName() +"\n");
            for (int i = 0; i < stack.length; i++) {
                System.out.println(stack[i]);
            }
        }
    }

}
