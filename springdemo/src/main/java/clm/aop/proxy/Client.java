package clm.aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 客户
 */
public class Client {
    public static void main(String[] args) {
        //匿名内部类访问外部变量，需要加final
        final Producer producer = new Producer();
        /**
         * 动态代理：
         *  特点：字节码随用随创，随用随加载
         *  作用：不修改源代码的基础上，进行增强
         *  分类：
         *      基于接口的动态代理
         *      基于子类继承的动态代理
         *   基于接口：JDK官方的Proxy类
         *   如何创建：Proxy.newProxyInstance
         *   要求：被代理类最少实现一个接口，没有则不能使用
         */
        IProducer proxyProducer = (IProducer)Proxy.newProxyInstance(producer.getClass().getClassLoader(), new Class[]{IProducer.class}, new InvocationHandler() {
            /**
             * 执行被代理对象的   任何接口方法   都会经过该方法
             * @param proxy 代理对象的引用
             * @param method    当前执行的方法
             * @param args  当前执行方法所需的参数
             * @return  和被代理对象有相同的返回值
             * @throws Throwable
             */
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object returnVlaue = null;
                Float money = (Float)args[0];
                if("saleProduct".equals(method.getName())){
                    returnVlaue = method.invoke(producer,money*0.8f);
                }
                return returnVlaue;
            }
        });

        //代理卖这个10000块的电脑
        proxyProducer.saleProduct(10000f);










    }
}
