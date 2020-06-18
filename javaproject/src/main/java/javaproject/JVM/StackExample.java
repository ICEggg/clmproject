package javaproject.JVM;

/**
 * 栈，栈针的演示
 *
 * debug运行时，idea下面左边，有一个Frames窗口，就是栈
 */
public class StackExample {

    public static void main(String[] args) {
        m1();
    }

    public static void m1(){
        m2(1,2);
    }

    public static int m2(int a , int b){
        int c = a + b;
        return c;
    }
}
