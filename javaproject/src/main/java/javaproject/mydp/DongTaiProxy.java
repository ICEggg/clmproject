package javaproject.mydp;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DongTaiProxy {

    public static void main(String[] args) {
        Tank tank = new Tank();

        System.getProperties().put("jdk.proxy.ProxyGenerator.saveGeneratedFiles","true");
        Moveable move = (Moveable) Proxy.newProxyInstance(Tank.class.getClassLoader(), new Class[]{Moveable.class},
                new TimeProxy());
        move.move2();
    }

    static class Tank implements Moveable{
        public Tank() {
        }

        public void move() {
            System.out.println("tank go go");
        }

        @Override
        public void move2() {
            System.out.println("tank22222222222222222");
        }
    }

    interface Moveable{
        void move();

        void move2();
    }

    static class TimeProxy implements InvocationHandler{
        Tank tank = new Tank();
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("method "+method.getName()+" start");
            Object o = method.invoke(tank, args);
            System.out.println("method "+method.getName()+" end");
            return o;
        }
    }


}
