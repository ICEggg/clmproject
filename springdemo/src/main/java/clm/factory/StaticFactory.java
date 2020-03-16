package clm.factory;

import clm.service.AccountServiceImpl;
import clm.service.IAccountService;

public class StaticFactory {
    public static IAccountService getAccountService(){
        return new AccountServiceImpl();
    }

}
