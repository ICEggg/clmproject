import impl.MessageService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("application.xml");
        MessageService ms = ac.getBean("messageService", MessageService.class);
        ms.getMessage();




    }
}
