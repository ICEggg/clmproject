package org.clm.demo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 这个类的目的是想做登录的token验证的
 * springboot的拦截器和aspect都能做
 *
 * 这个留在这边参考
 *
 */
//@Aspect
//@Component
//@Slf4j
public class ContorllerAspect {

    /*@Value("${tokenAspectType}")
    private int tokenAspectType;

    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired(required = false)
    private HttpSession session;

    @Pointcut("execution(* com.jn.primiary.beyondsoft.controller.*.*(..))")
    public void beyondsoftController(){}

    @Pointcut("execution(* com.jn.primiary.office.controller.*.*(..))")
    public void FileUploadController(){}



    //设置白名单，有这个注解的就不拦截
    @Pointcut("@annotation(com.jn.primiary.beyondsoft.aspect.LoginWhitePathAnnotation)")
    public void cutAnnotation(){}

    @Around("((beyondsoftController() || FileUploadController()) && !cutAnnotation())")
    public Object around(ProceedingJoinPoint point) throws Throwable {


        Object[] paramValues = point.getArgs();
        for (Object param : paramValues){
            logger.info("请求的参数是："+param);
        }

        Object result = null;
        AbstracyAspectStrategy aas = null;
        if(tokenAspectType == 1){
            aas = new SaiSiAspect(tokenAspectType,request,point,session);
        }else if(tokenAspectType == 2){
            //ukey
            aas = new UkeyAspect(tokenAspectType,request,point,session);
        }else{
            //本地测试
            aas = new DevAspect(tokenAspectType,request,point,session);
        }
        result = aas.aspectFunction();
        return result;

    }*/



}
