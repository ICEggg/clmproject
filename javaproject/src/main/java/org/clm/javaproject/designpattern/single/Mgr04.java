package org.clm.javaproject.designpattern.single;

/**
 * 枚举单例
 * 这是java创始人之一写的单例方法，在effectivejava一书中有提到
 * 理论上，这是最好的方法
 *
 * 解决线程同步，防止反序列化（这句话没懂）
 * 防止反序列化：其他前面的几种单例，都可以通过Class.forname(""),来把类加载到内存中，
 * 然后实例化成一个对象。但是这种枚举单例的方式，是不能通过Class.forname来反序列化的。
 *
 * 枚举类不能被反序列化的原因：枚举类没有构造方法
 * 所以就算拿到了枚举类的class文件，也没有办法实例化一个对象，他返回的只是INSTANCE这样一个值
 * 根据反序列化拿到INSTANCE这个值，是和单例创建的同一个对象
 *
 * 等明白了反射，在回来看看这段话
 */
public enum  Mgr04 {
    INSTANCE;

    public void m(){ }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                System.out.println(Mgr04.INSTANCE.hashCode());
            }).start();
        }
    }
}

