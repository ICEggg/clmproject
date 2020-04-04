package clm.ioc.service;

import clm.ioc.dao.IAccountDao;

import java.util.Date;

/**
 * bean创建
 * 构造函数注入
 * 测试类
 */
public class AccountServiceImpl1 implements IAccountService{
    //如果是经常变化的数据，不适合依赖注入
    private String name;
    private Integer age;
    private Date birthday;

    private IAccountDao dao;
    public AccountServiceImpl1(String name, Integer age, Date birthday) {
        System.out.println("AccountServiceImpl1 有参构造函数 对象创建了");
        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }


//    public AccountServiceImpl1() {
//        System.out.println("AccountServiceImpl1 无参构造函数 对象创建了");
//    }

    public void saveCount() {
        //clm.dao.saveAcount();
        System.out.println("saveCount方法执行了  "+name+"  "+age+"  "+birthday);
    }

    public void init(){
        System.out.println("AccountServiceImpld的初始化方法");
    }

    public void destory(){
        System.out.println("AccountServiceImpld的销毁方法");
    }
}
