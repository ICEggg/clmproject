package clm.aop.aspect_annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 注解方式，普通的前置，后置，异常，最终通知会出问题。
 * 最终通知总是在后置通知和异常通知之前执行
 * 这个问题spring一直没解决
 *
 * 用环绕通知就不会有这个问题了
 */
@Component("logger")
@Aspect
public class Logger {

    @Pointcut("execution(* clm.aop.aspect_annotation.AccountService.*(..))")
    private void pt1(){}

    /**
     * 前置通知
     */
    //@Before("pt1()")
    public void beforePringLog(){
        System.out.println("前置通知");
    }

    /**
     * 后置通知
     */
    //@AfterReturning("pt1()")
    public void AfterPringLog(){
        System.out.println("后置通知");
    }
    /**
     * 异常通知
     */
    //@AfterThrowing("pt1()")
    public void ThrowPringLog(){
        System.out.println("异常通知");
    }
    /**
     * 最终通知
     */
    //@After("pt1()")
    public void FinalPringLog(){
        System.out.println("最终通知");
    }

    /**
     * 环绕通知
     *  环绕通知有明确的切入点方法的调用，调用方法之前的代码是前置通知，之后的就是后置通知
     *  调用切入点方法：ProceedingJoinPoint中的proceed()
     *
     *  环绕通知，也是手动控制增强方法何时执行的方式
     */
    @Around("pt1()")
    public Object aroundPrintLog(ProceedingJoinPoint pjp){
        Object result = null;
        try {
            System.out.println("前置通知");
            Object[] args = pjp.getArgs();  //方法执行所需的参数
            result = pjp.proceed();//调用被增强的方法
            System.out.println("后置通知");
            return result;
        } catch (Throwable t) {
            System.out.println("异常通知");
            t.printStackTrace();
            throw new RuntimeException(t);
        }finally {
            System.out.println("最终通知");
        }
    }
}
