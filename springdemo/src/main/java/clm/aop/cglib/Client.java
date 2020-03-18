package clm.aop.cglib;

import clm.aop.proxy.IProducer;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

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
         *   基于子类的动态代理：cglib的Enhancer
         *   如何创建：Enhancer.create
         *   要求：被代理类不能是最终类
         *   create方法的参数：
         *      class：字节码：用于指定被代理对象的字节码
         *      callback:提供增强的代码
         *      一般写的是该接口的子接口实现类：MethodInterceptor
         */
        Producer cglibProducer = (Producer)Enhancer.create(producer.getClass(), new MethodInterceptor() {
            /**
             * 前三个参数和jdk的proxy是一样的
             * @param o
             * @param method
             * @param objects
             * @param methodProxy   当前执行方法的代理对象
             * @return
             * @throws Throwable
             */
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                Object returnVlaue = null;
                Float money = (Float)objects[0];
                if("saleProduct".equals(method.getName())){
                    returnVlaue = method.invoke(producer,money*0.8f);
                }
                return returnVlaue;
            }
        });

        cglibProducer.saleProduct(10000);












        
    }
}
