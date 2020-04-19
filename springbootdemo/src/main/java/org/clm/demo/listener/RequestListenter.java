package org.clm.demo.listener;


import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

/**
 * 监听器：ServletRequestListener
 * 获取用户的访问信息
 */

//也可以用这个
//@WebListener
@Component
public class RequestListenter implements ServletRequestListener {
    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        //请求销毁

        System.out.println("request end");
        HttpServletRequest request = (HttpServletRequest) servletRequestEvent.getServletRequest();
        //System.out.println("request域中保存的name值为："+request.getAttribute("name"));
        System.out.println("这是监听器---------------------------->请求销毁");

    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        //请求创建
        System.out.println("这是监听器---------------------------->请求创建");
        HttpServletRequest request = (HttpServletRequest) servletRequestEvent.getServletRequest();
        //System.out.println("session id为："+request.getRequestedSessionId());
        System.out.println("request url为："+request.getRequestURL());
        //request.setAttribute("name", "mashibing");

    }


}
