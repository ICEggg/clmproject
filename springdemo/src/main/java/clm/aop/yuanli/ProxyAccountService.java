package clm.aop.yuanli;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyAccountService {
    private AccountService service ;
    private TransactionManager transactionManager;

    /**
     * spring  set注入
     * @param service
     */
    public void setService(AccountService service) {
        this.service = service;
    }

    /**
     * 应该返回的是：service的代理对象，并对代理对象的所有方法
     * 加上事务处理
     * @return
     */
    public AccountService getService() {
        AccountService as = (AccountService)Proxy.newProxyInstance(service.getClass().getClassLoader(), service.getClass().getInterfaces(), new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object result = null;
                transactionManager.beginTransaction();
                try {
                    result = method.invoke(service,args);
                    transactionManager.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    transactionManager.rollback();
                }finally {
                    transactionManager.release();
                }
                return result;
            }
        });
       return as;
    }


}
