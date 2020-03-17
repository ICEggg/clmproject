package clm.ioc.factory;

import clm.ioc.service.AccountServiceImpl1;
import clm.ioc.service.IAccountService;

import java.util.Date;

/**
 * 普通实例工厂  （该类可能在jar包里，没有办法添加构造函数）
 */
public class InstanceFactory {

    public IAccountService getAccountService(){
        return new AccountServiceImpl1("aaa",18,new Date());
    }

}
