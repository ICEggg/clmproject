package clm.service;

import clm.dao.IAccountDao;

public class AccountServiceImpl implements IAccountService{
    private IAccountDao dao;
    public AccountServiceImpl() {
        System.out.println("AccountServiceImpl 对象创建了");
    }

    public void saveCount() {
        dao.saveAcount();
    }

    public void init(){
        System.out.println("AccountServiceImpld的初始化方法");
    }

    public void destory(){
        System.out.println("AccountServiceImpld的销毁方法");
    }
}
