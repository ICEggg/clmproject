package clm.ioc_annotation.service.service;

import clm.ioc_annotation.dao.AccountDaoImpl1;
import clm.ioc_annotation.dao.IAccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 *  账户业务层实现类
 *
 *  用于创建对象的
 *      xml配置：<bean></bean>
 *      注解：@Component：把当前类对象，注入到spring容器中
 *              属性：value：用于指定bean的id，当不指定的时候，id就是当前类名，首字母小写
 *
 *            @Controller  表现层
 *            @Service 业务层
 *            @Repository  持久层
 *            以上三个和Component的作用是一样的，是spring提供的明确的三层使用，使层次更加清晰
 *
 *  用于注入数据
 *      xml配置：<bean><property></property></bean>
 *      注解：@Autowried:
 *              作用：自动按照类型注入，只要容器中有唯一的一个bean对象类型和要注入的变量类型匹配，就可以注入成功。
 *              出现位置：变量上，方法上
 *              如果ioc容器中没有任何bean的类型和要注入的变量类型匹配，则报错
 *            @Qualifier：(要搭配Autowried，不能独立使用)
 *              作用：在按照类注入的基础上，再按名称注入。在给类成员注入时不能单独使用，但是给方法参数注入时可以。
 *              属性：value：指定bean的id
 *            @Resource：
 *              作用：直接按照bean的id注入，可以独立使用
 *              属性：name：指定bean的id
 *
 *   上面三个注解，只能注入bean类型的数据，基本类型和string不能。
 *   集合类型只能通过xml来实现
 *
 *            @Value：
 *              作用：用于注入基本类型和string类型
 *              属性：value：指定值，可以使用spring的SpEl（spring的el表达式）
 *              SpEl写法：${}
 *
 *  用于改变作用范围
 *      xml配置：bean标签中的scope属性
 *      注解：@Scope
 *              作用：指定bean 的作用范围
 *              属性：value：指定范围的值，常用：singleton，prototype
 *  用于生命周期
 *      xml配置：bean标签中的init-method和destory-method
 *      注解：
 *      @PreDestory
 *          作用：用于指定销毁方法
 *      @PostConstruct
 *          作用：用于指定初始化方法
 *
 */
//@Component("accountService1")
@Service("accountService1")
@Scope(value = "singleton") //多例对象
public class AccountServiceImpl1 implements IAccountService {

    //@Autowired
    //@Qualifier("accountDao1")
    @Resource(name = "accountDao1")
    private IAccountDao accountDao;

    public void saveCount() {
        accountDao.saveAcount();
    }

    @PostConstruct
    public void init(){
        System.out.println("AccountServiceImpl 的初始化方法");
    }
    @PreDestroy
    public void destory(){
        System.out.println("AccountServiceImpl 的销毁方法");
    }
}
