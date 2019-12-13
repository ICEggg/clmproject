package org.clm.javaproject.thread.threadsingle;

import java.util.Arrays;

/**
 * 线程安全的单例模式
 *
 * 网上可以找到三种
 * 1.直接new一个对象（这种不太好因为没有实现懒加载，直接上来就new了一个对象）
 * 2.在getinstance方法上加synchronized（这种的缺点是锁加在方法上粒度太大）
 * 3.双重锁，在new的时候，加synchronized。这样子加锁的粒度小
 *
 *
 * 4.用内部类实现单例，既不用加锁，也能实现懒加载
 * 下面代码所实现的就是这种方式
 */
public class Singleton {

    private static class Inner{
        private static Singleton s =  new Singleton();
    }

    private Singleton(){
        System.out.println("Single");
    }
    private static Singleton getSingle(){
        return Inner.s;
    }

    public static void main(String[] args) {
        Thread[] ths = new Thread[200];
        for (int i = 0; i < ths.length; i++) {
            ths[i] = new Thread(()->{
               Singleton.getSingle();
            });
        }

        Arrays.asList(ths).forEach(o->o.start());
    }
}
