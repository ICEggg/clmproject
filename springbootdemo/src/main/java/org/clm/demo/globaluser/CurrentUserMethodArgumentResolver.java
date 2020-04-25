package org.clm.demo.globaluser;

import org.clm.demo.mvc.primiary.entity.User;
import org.clm.demo.util.UserContextUtil;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * 自定义参数解析器
 *
 * 理解Springmvc自带的一些实现，我们可以根据业务进行接口重写
 * ServletRequestMethodArgumentResolver和ServletResponseMethodArgumentResolver处理了自动绑定HttpServletRequest和HttpServletResponse
 * RequestParamMapMethodArgumentResolver处理了@RequestParam
 * RequestHeaderMapMethodArgumentResolver处理了@RequestHeader
 * PathVariableMapMethodArgumentResolver处理了@PathVariable
 * ModelAttributeMethodProcessor处理了@ModelAttribute
 * RequestResponseBodyMethodProcessor处理了@RequestBody
 */
@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 用于判定是否需要处理该参数分解，返回true为需要，并会去调用下面的方法resolveArgument
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //比如：@CurrentUser User user；调用的方法，其中一个参数是用@CurrentUser了，并且参数是User的，就返回true
        if (parameter.getParameterType().isAssignableFrom(User.class)
                && parameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }
        return false;

    }

    /**
     * 真正用于处理参数分解的方法，返回的Object就是controller方法上的形参对象
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        User user = UserContextUtil.getUser();
        return user;

    }


}
