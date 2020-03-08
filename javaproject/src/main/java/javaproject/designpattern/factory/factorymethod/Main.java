package javaproject.designpattern.factory.factorymethod;

/**
 * car实现moveable接口，CarFactory创建car
 *
 * CarFactory可以换成plane，train等
 */
public class Main {
    public static void main(String[] args) {
        Moveable move = new CarFactory().create();
    }
}
