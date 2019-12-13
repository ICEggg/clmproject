package com.clm.demo;

import com.clm.demo.lanjieqi.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 这是拦截器，放开@Component就是启用了
 */

@Component
public class WebMvcConfig extends WebMvcConfigurationSupport {

    //自定义的拦截器
    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)  //添加认证拦截器
                .excludePathPatterns("/dev/sysuser/login")  //excludePathPatterns(),设置不拦截的路径
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**","/dev/swagger-ui.html/**")
                .addPathPatterns("/**");
        super.addInterceptors(registry);

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }

}
