package org.clm.javaproject.designpattern.single;

/**
 * 饿汉式
 * 类加载到内存后，就实例化一个单例，JVM保证线程安全
 * （这是推荐使用的方法！！）
 * 唯一缺点：不管用到与否，类装载时就完成实例化
 *
 * Class.forname(""):这是只把class落到内存里，但是不实例化
 */
public class Mgr01 {

    private static Mgr01 INSTANCE = new Mgr01();

    private Mgr01(){ }

    public static Mgr01 getInstance(){
        return INSTANCE;
    }

    public static void main(String[] args) {
        Mgr01 m1 = Mgr01.getInstance();
        Mgr01 m2 = Mgr01.getInstance();
        System.out.println(m1==m2);
    }
}
