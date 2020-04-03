package javaproject.designpattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Random;

/**
 * 动态代理 （缺点在于，被代理类必须实现一个接口）
 * 静态代理是：只能代理moveable接口的实现类，如果我想代理别的类，我除了代理茅台，我还想要代理咖啡，奶茶等各种
 * 分离代理行为和代理对象
 * 使用jdk的动态代理 Proxy.newProxyInstance()：这个的缺点是：被代理类，必须实现某个接口，才能用jdk的动态代理
 *
 * 一些个挺厉害的动态代理的工具：instrument（java 自带的）
 */
public class DynamicTank implements DynamicMoveable{
    @Override
    public void move() {
        System.out.println("tank moving claclacla.......");
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void move2() {
        System.out.println("这是move222222222222222222");
    }

    public static void main(String[] args) {
        DynamicTank tank = new DynamicTank();

        //proxy默认会在项目的一个目录中动态生成代理类的代码，可是这句代码没有反应啊。。。。。。。
        System.getProperties().put("jdk.proxy.ProxyGenerator.saveGeneratedFiles","true");

        //reflection 反射 通过二进制字节码分析类的属性和方法
        /**
         * Proxy.newProxyInstance
         * 第一个参数：要用哪个代理对象把将来返回的代理对象落到内存，和被代理对象用同一个就可以了
         * 第二个参数：new出来的这个代理对象，应该实现哪个接口，由于一个类可以实现多个接口，所以参数是个class数组
         * 第三个参数：调用处理器，指的是被代理对象的方法被调用的时候，我们怎么做处理，比如加上时间，加上日志
         */
        DynamicMoveable m = (DynamicMoveable)Proxy.newProxyInstance(DynamicTank.class.getClassLoader(),
                new Class[]{DynamicMoveable.class},
                //这是内部类的方式，也可以改成外部类的方式，就是写一个类来实现InvocationHandler这个接口
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //一个类里有多少个方法：getClass.getMethod []
                        /**
                         * 第一个参数：是系统动态生成的代理对象
                         * 第二个参数：调用的是被代理类的哪个方法
                         * 第三个参数：调用的方法所需要的参数
                         */
                        System.out.println("method " + method.getName() + " start....");
                        //这行代码，相当于调用了tank.move
                        Object o = method.invoke(tank, args);
                        System.out.println("method " + method.getName() + " end!");

                        //tank的move方法返回值是void，所以这里返回的是个空的object
                        return o;
                    }
                });
        m.move();
        //一个接口可以有好多方法，可以对任意方法进行操作
        //m.move2();
    }
}

interface DynamicMoveable{
    public void move();
    public void move2();
}
