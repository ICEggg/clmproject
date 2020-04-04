package clm.aop.aspect_annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AspectMain {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("aop_annotation.xml");
        AccountService service = ac.getBean("accountService", AccountService.class);
        service.add();
        //clm.service.delete();
    }
}
