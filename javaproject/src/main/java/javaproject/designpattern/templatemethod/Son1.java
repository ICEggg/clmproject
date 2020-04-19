package javaproject.designpattern.templatemethod;

public class Son1 extends Father{
    @Override
    public void before() {
        System.out.println("son1 before");
    }

    @Override
    public void after() {
        System.out.println("son1 after");
    }

    public static void main(String[] args) {
        Father f = new Son1();
        f.way();
    }
}
