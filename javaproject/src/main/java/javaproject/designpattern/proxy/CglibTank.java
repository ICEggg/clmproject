package javaproject.designpattern.proxy;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Random;

/**
 * cglib实现动态代理，比jdk的实现更好，因为被代理对象不需要实现一个接口，就可以实现动态代理
 * cglib没有实现接口，因为它动态生成的代理类，是被代理对象的子类
 * 缺点是：如果被代理对象是final修饰的，比如final Tank，那cglib这个方式就不行了
 * cglib的底层也是asm
 */
public class CglibTank {
    public static void main(String[] args) {
        //增强
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Tank.class);
        enhancer.setCallback(new TimeMethodIntereptor());
        Tank tank = (Tank)enhancer.create();
        tank.move();
    }
}

class TimeMethodIntereptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println(o.getClass().getSuperclass().getName());
        System.out.println("before");
        Object result = null;
        result = methodProxy.invokeSuper(o,objects);
        System.out.println("after");
        return result;
    }
}

class Tank{
    public void move() {
        System.out.println("tank moving claclacla.......");
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}