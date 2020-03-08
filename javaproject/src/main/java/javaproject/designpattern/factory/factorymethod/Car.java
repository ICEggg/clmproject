package javaproject.designpattern.factory.factorymethod;

/**
 * 可以任意定制交通工具，实现moveable接口即可
 */
public class Car implements Moveable{
    @Override
    public void go() {
        System.out.println("小车开");
    }
}
