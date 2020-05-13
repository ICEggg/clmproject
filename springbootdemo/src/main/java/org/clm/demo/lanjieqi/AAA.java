package org.clm.demo.lanjieqi;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HandlerInterceptorAdapter拦截器 ，在HandlerInterceptor后执行
 * 要在WebMvcConfig中注册
 * 记录
 */
//@Component
public class AAA extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("-----------------------------");

        return true;
    }

}
