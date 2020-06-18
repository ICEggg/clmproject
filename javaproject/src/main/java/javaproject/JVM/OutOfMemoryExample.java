package javaproject.JVM;

import java.util.ArrayList;
import java.util.List;

/**
 * java 内存溢出例子 java.lang.OutOfMemoryError: Java Heap Space
 * -Xms8m
 */
public class OutOfMemoryExample {
    public static void main(String[] args) {
        int i = 0;
        String a = "hello";
        List<String> list = new ArrayList<>();
        try {
            while (true){
                list.add(a);
                a = a+a;
                i++;
            }
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println(i);
        }

    }
}
