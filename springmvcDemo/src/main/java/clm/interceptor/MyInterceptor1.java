package clm.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 */
public class MyInterceptor1 implements HandlerInterceptor {
    /**
     * 预处理，controller方法执行之前
     * return true 放行，执行下一个拦截器，如果没有，就执行controller中的方法
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器1实现了preHandle");
        /*预处理直接跳到error界面（比如要做一些判断，判断用户是否登录了），就不会走controller方法了*/
        //request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request,response);
        return false;
    }

    /**
     * 后处理方法，controller方法执行完,然后执行这个方法，最后跳到success页面
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("拦截器1实现了postHandle");
    }

    /**
     * 跳到success页面之后，执行（关闭资源）
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("拦截器1实现了afterCompletion");
    }
}
