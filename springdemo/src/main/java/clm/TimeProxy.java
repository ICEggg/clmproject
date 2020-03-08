package clm;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class TimeProxy {

    @Before("execution (void clm.Tank.move()) ")
    public void before(){
        System.out.println("before method");
    }
    @After("execution (void clm.Tank.move())")
    public void after(){
        System.out.println("after start");
    }
}
