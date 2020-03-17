package clm.ioc_annotation;

import clm.ioc_annotation.service.service.AccountServiceImpl1;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        //ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("bean_annotation.xml");
        //用SpringConfiguration代替bean.xml的话，就用这个
        ApplicationContext ac = new AnnotationConfigApplicationContext();

        AccountServiceImpl1 as = ac.getBean("accountService1", AccountServiceImpl1.class);


        //AccountServiceImpl1 as2 = ac.getBean("accountService1", AccountServiceImpl1.class);
        as.saveCount();
        as.destory();

    }
}
