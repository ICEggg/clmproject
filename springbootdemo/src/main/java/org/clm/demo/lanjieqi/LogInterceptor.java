package org.clm.demo.lanjieqi;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Aspect定义这个类是个切面
 *
 * 这个功能是，调用每一个接口，日志打印这个接口执行的时间，地址，参数等
 */

//@Aspect
//@Component
public class LogInterceptor {
    static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    static final String pCutStr = "execution(* com.mashibing.demo.mvc.*..*(..))";

    //定义切点
    @Pointcut(value = pCutStr)
    public void logPointcut() {
    }

    //@before代表在目标方法执行前切入, 并指定在哪个方法前切入
    /*@Before("pointCut()")
    public void logStart(){
        System.out.println("方法执行前");
    }
    @After("pointCut()")
    public void logEnd(){
        System.out.println("方法执行后");
    }
    @AfterReturning("pointCut()")
    public void logReturn(){
        System.out.println("目标方法正常返回值后运行");
    }
    @AfterThrowing("pointCut()")
    public void logException(){
        System.out.println("目标方法出现异常后运行");
    }*/


    @Around("logPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("=====================================Method  start====================================");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        long start = System.currentTimeMillis();
        try {
            //joinPoint.proceed()，这个意思就是开始执行方法，
            // 上部分代码就是：方法执行前做的事，相当于@Before，下部分代码，就相当于@After
            Object result = joinPoint.proceed();
            long end = System.currentTimeMillis();
            logger.info("请求地址:" + request.getRequestURI());
            logger.info("用户IP:" + request.getRemoteAddr());
            logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            logger.info("参数: " + Arrays.toString(joinPoint.getArgs()));
            logger.info("执行时间: " + (end - start) + " ms!");
            logger.info("=====================================Method  End====================================");
            return result;
        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            logger.info("URL:" + request.getRequestURI());
            logger.info("IP:" + request.getRemoteAddr());
            logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
            logger.info("执行时间: " + (end - start) + " ms!");
            logger.info("=====================================Method  End====================================");
            throw e;
        }
    }
}
