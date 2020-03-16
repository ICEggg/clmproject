package clm;

import clm.dao.IAccountDao;
import clm.service.IAccountService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * ApplicationContext的三个常用实现类   （注解方式应该是最常用的）
 *      ClassPathXmlApplicationContext 它加载类路径下的配置文件，要求配置文件必须在类路径下（就是在项目里），不在的话加载不了
 *      FileSystemXmlApplicationContext 它可以加载磁盘任意路径下的配置文件（必须有访问权限）
 *      AnnotationConfigApplicationContext  它用于读取注解创建容器的
 *
 * 核心容器的两个接口引发出的问题：
 *  ApplicationContext:     （几乎都用这个，单例对象适用）
 *      它在构建容器时，创建对象的策略是立即加载的方式，就是：只要一读到配置文件，就生成对象
 *  BeanFactory：           （多例对象适用）
 *      在使用到对象的时候，才创建
 *
 */
public class Main {
    public static void main(String[] args) {
        //获取核心容器对象
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        //ApplicationContext ac = new FileSystemXmlApplicationContext("D:\\project\\MyProject\\springdemo\\src\\main\\resources\\bean.xml");

        //根据id获取bean
        IAccountService as = ac.getBean("accountService", IAccountService.class);
        IAccountDao adao = ac.getBean("accountDao", IAccountDao.class);

        System.out.println(as);
        System.out.println(adao);
        as.saveCount();

        //BeanFactory       延迟加载对象的方式
        /*ClassPathResource resource = new ClassPathResource("bean.cml");
        BeanFactory factory = new XmlBeanFactory(resource);
        IAccountService as2 = factory.getBean("accountService", IAccountService.class);
        System.out.println(as2);*/
    }

}
