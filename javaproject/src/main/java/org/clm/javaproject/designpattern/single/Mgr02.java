package org.clm.javaproject.designpattern.single;

/**
 * 懒汉式，即用到的时候，才实例化，双重判断也保证了线程安全
 * 但是实际使用上，没有必要这样写，用第一种饿汉式就够了
 *
 * 完美的写法之一
 *
 */
public class Mgr02 {

    private static Mgr02 INSTANCE;

    private Mgr02(){ }

    public static Mgr02 getInstance(){
        if(INSTANCE == null){
            synchronized (Mgr02.class){
                if(INSTANCE == null){
                    //这个sleep是模拟执行时间
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    INSTANCE = new Mgr02();
                }
            }
        }
        return INSTANCE;
    }

    public static void main(String[] args) {
        Mgr02 m1 = Mgr02.getInstance();
        Mgr02 m2 = Mgr02.getInstance();
        System.out.println(m1==m2);
    }
}
