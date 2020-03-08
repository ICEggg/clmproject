package javaproject.designpattern.templatemethod;

/**
 * 模板方法
 * 父类有一个模板方法是调用自己内部的几个方法，
 * 子类重写那几个方法后，就改变了父类的m方法，
 */
public class Main {
    public static void main(String[] args) {
        F f = new C();
        f.m();
    }

}

abstract class F{
    public void m(){
        op1();
        op2();
    }

    abstract void op1();
    abstract void op2();
}

class C extends F{

    @Override
    void op1() {
        System.out.println("1");
    }

    @Override
    void op2() {
        System.out.println("2");
    }
}
