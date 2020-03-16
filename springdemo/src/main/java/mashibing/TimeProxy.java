package mashibing;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class TimeProxy {

    @Before("execution (void mashibing.Tank.move()) ")
    public void before(){
        System.out.println("before method");
    }
    @After("execution (void mashibing.Tank.move())")
    public void after(){
        System.out.println("after start");
    }
}
