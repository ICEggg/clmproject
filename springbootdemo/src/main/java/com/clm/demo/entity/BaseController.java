package com.clm.demo.entity;

import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自己写的
 * 所有的controller都要继承这个类，这样每个controller进来之前，都会执行@ModelAttribute注解的方法
 *  拦截器里会给claims赋值
 */
public class BaseController {
    protected HttpServletRequest request ;
    protected HttpServletResponse response;
    protected Claims claims;

    //被@ModelAttribute注释的方法会在此controller每个方法执行前被执行
    @ModelAttribute
    public void SetResAndReq(HttpServletRequest request,HttpServletResponse response){
        this.request = request;
        this.response = response;

        Object obj = request.getAttribute("claims");
        if(obj != null){
            this.claims = (Claims)obj;
        }

    }

}
