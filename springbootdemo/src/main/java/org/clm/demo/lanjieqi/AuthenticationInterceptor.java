package org.clm.demo.lanjieqi;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.clm.demo.exception.CommonException;
import org.clm.demo.mvc.primiary.entity.User;
import org.clm.demo.mvc.primiary.service.UserService;
import org.clm.demo.util.JwtUtil;
import org.clm.demo.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 * 引用场景：登录模块，拦截请求头中是否带token
 */
@Slf4j
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    UserService userService;

    //拦截请求，进入api之前要做的事
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object object) throws Exception {
        //验证token的逻辑
        log.info("进入拦截器："+"org.clm.demo.lanjieqi.AuthenticationInterceptor");
        String url = request.getRequestURI();
        if(url.contains("/userCon/login") || url.contains("/swagger-ui.html")){
            //这两个链接放过去，不拦截
            return true;
        }

        User user = getUser(request, response);
        if(user == null){
            throw new CommonException("无效的token");
        }else{
            UserContextUtil.setUser(user);
            return true;
        }

        /*String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        if(!"".equals(token) && token!=null){
            Claims claims = jwtUtil.parseJwt(token);
            String password = claims.getId();
            String username = claims.getSubject();
            User user = userService.getUserByNameAndPwd(username, password);
            if(user != null){
                return true;
            }
        }else{
            throw new CommonException("请传入token");
        }*/
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


    private User getUser(HttpServletRequest request, HttpServletResponse response) throws CommonException {
        User user = null;
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        if(!"".equals(token) && token!=null){
            Claims claims = jwtUtil.parseJwt(token);
            String password = claims.getId();
            String username = claims.getSubject();
            user = userService.getUserByNameAndPwd(username, password);
        }else{
            throw new CommonException("请传入token");
        }
        return user;
    }




}
