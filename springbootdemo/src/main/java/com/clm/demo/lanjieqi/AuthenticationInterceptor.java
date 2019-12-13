package com.clm.demo.lanjieqi;

import com.clm.demo.entity.ResultCode;
import com.clm.demo.exception.CommonException;
import com.clm.demo.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    //进入api之前要做的事
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        System.out.println(httpServletRequest.getRequestURI());
        String url = httpServletRequest.getRequestURI();
        if(url.contains("/dev/userCon/login") || url.contains("/dev/swagger-ui.html")){
            //这两个链接放过去，不拦截
            return true;
        }
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        if(!"".equals(token) && token!=null){
            Claims claims = jwtUtil.parseJwt(token);
            if(claims!=null){
                httpServletRequest.setAttribute("claims",claims);
            }
            return true;
        }
        throw new CommonException(ResultCode.RESULT_ERROR);
    }

    //api执行之后要做的事
    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("一个api执行完了");
    }

    //全部api执行之后要做的事
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
        System.out.println("所有api执行完了");
    }
}
