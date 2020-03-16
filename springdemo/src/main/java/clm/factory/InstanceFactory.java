package clm.factory;

import clm.service.AccountServiceImpl;
import clm.service.IAccountService;

public class InstanceFactory {

    public IAccountService getAccountService(){
        return new AccountServiceImpl();
    }

}
