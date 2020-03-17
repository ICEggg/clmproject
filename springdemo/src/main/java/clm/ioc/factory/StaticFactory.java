package clm.ioc.factory;

import clm.ioc.service.AccountServiceImpl1;
import clm.ioc.service.IAccountService;
import java.util.Date;

/**
 * 静态工厂
 */
public class StaticFactory {
    public static IAccountService getAccountService(){
        return new AccountServiceImpl1("aaa",18,new Date());
    }

}
