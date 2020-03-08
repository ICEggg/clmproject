package javaproject.designpattern.proxy;

import java.util.Random;

/**
 * 静态代理的例子
 * 比如：当坦克有个move方法，要记录这个方法运行的时间，或者给这个方法的前后加上日志，
 *
 * 作为坦克的代理，也要实现moveable接口，然后内部整合被代理对象tank。
 * 代理类都实现moveable接口的作用是：代理类可以互相嵌套，比如先日志代理再时间，或者反过来，先时间再日志代理
 * 所以，代理对象内部应该整合的是：Moveable接口
 *
 * 但是，这种静态代理有确定，就是只代理了实现了moveable的类，这样不灵活。比如我想代理各种接口的类
 * 所以引出 动态代理
 */
public class StaticTank implements Moveable {
    @Override
    public void move() {
        System.out.println("tank moving claclacla.......");
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new TankTimeProxy(
                new TankLogProxy(
                        new StaticTank()
                )
        ).move();
    }
}

class TankTimeProxy implements Moveable {
    Moveable m;

    public TankTimeProxy(Moveable m) {
        this.m = m;
    }

    @Override
    public void move() {
        long start = System.currentTimeMillis();
        System.out.println("开始时间："+start);
        m.move();
        long end = System.currentTimeMillis();
        System.out.println("结束时间："+end);
        System.out.println("执行时间："+(end-start));
    }
}

class TankLogProxy implements Moveable {
    Moveable m;

    public TankLogProxy(Moveable m) {
        this.m = m;
    }

    @Override
    public void move() {
        System.out.println("记录日志开始。。");
        m.move();
        System.out.println("记录日志结束。。");
    }
}


interface Moveable {
    public void move();
}
