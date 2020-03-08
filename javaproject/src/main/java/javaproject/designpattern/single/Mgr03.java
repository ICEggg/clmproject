package javaproject.designpattern.single;

/**
 *  静态内部类的方式
 *  完美的写法之一
 */
public class Mgr03 {

    private Mgr03(){ }

    //静态内部类
    private static class Mgr03Holder{
        private final static Mgr03 INSTANCE = new Mgr03();
    }

    public static Mgr03 getInstance(){
        return Mgr03Holder.INSTANCE;
    }

}
