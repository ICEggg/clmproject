package clm.ioc.service;

import java.util.Date;

/**
 * set方法注入  测试类
 */
public class AccountServiceImpl2 implements IAccountService{
    //如果是经常变化的数据，不适合依赖注入
    private String name;
    private Integer age;
    private Date birthday;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }


//    public AccountServiceImpl1() {
//        System.out.println("AccountServiceImpl1 无参构造函数 对象创建了");
//    }

    public void saveCount() {
        //clm.dao.saveAcount();
        System.out.println("saveCount方法执行了  "+name+"  "+age+"  "+birthday);
    }

}
