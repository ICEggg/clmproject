package org.clm.demo.lanjieqi;

import io.jsonwebtoken.Claims;
import org.clm.demo.entity.ResultCode;
import org.clm.demo.exception.CommonException;
import org.clm.demo.mvc.primiary.entity.User;
import org.clm.demo.mvc.primiary.service.UserService;
import org.clm.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 拦截器
 * 引用场景：登录模块，拦截请求头中是否带token
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    UserService userService;

    //拦截请求，进入api之前要做的事
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        //System.out.println("拦截器preHandle："+"org.clm.demo.lanjieqi.AuthenticationInterceptor");
        //return true;


        //验证token的逻辑
        System.out.println("进入拦截器："+"org.clm.demo.lanjieqi.AuthenticationInterceptor");
        System.out.println(httpServletRequest.getRequestURI());
        String url = httpServletRequest.getRequestURI();
        if(url.contains("/dev/userCon/login") || url.contains("/dev/swagger-ui.html")){
            //这两个链接放过去，不拦截
            return true;
        }

        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        if(!"".equals(token) && token!=null){
            Claims claims = jwtUtil.parseJwt(token);
           /* if(claims!=null){
                String password = (String) claims.getId();
                String username = (String) claims.getSubject();
            }*/
            return true;
        }else{
            throw new CommonException("请传入token");
        }

    }

    //api执行之后要做的事
    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("拦截器postHandle：一个api执行完了");
    }

    //全部api执行之后要做的事
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
        System.out.println("拦截器afterCompletion：所有api执行完了");
    }





}
