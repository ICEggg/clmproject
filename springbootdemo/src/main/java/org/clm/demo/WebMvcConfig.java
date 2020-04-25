package org.clm.demo;

import org.clm.demo.globaluser.CurrentUserMethodArgumentResolver;
import org.clm.demo.lanjieqi.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * 注册    自定义的拦截器
 * 这是拦截器，放开@Component就是启用了
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${spring.profiles.active}")
    private String active;

    //自定义的拦截器
    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    //参数解析器
    @Autowired
    private CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;

    //添加参数解析器
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserMethodArgumentResolver);
    }

    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if("pro".equals(active)){
            //如果是application-pro.yml激活，就使用拦截器拦截token
            registry.addInterceptor(authenticationInterceptor)  //添加认证拦截器
                    .excludePathPatterns("/userCon/login")  //excludePathPatterns(),设置不拦截的路径
                    .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**","/dev/swagger-ui.html/**")
                    .addPathPatterns("/**");
        }else{
            //dev
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /*@Override
    protected void addInterceptors(InterceptorRegistry registry) {
        if("pro".equals(active)){
            //如果是application-pro.yml激活，就使用拦截器拦截token
            registry.addInterceptor(authenticationInterceptor)  //添加认证拦截器
                    .excludePathPatterns("/userCon/login")  //excludePathPatterns(),设置不拦截的路径
                    .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**","/dev/swagger-ui.html/**")
                    .addPathPatterns("/**");
        }else{
            //dev
        }

        super.addInterceptors(registry);

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


    *//**
     * 增加跨域设置          未测试
     * @param registry
     *//*
    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        //
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3088")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowCredentials(true)
                .allowedHeaders("*")
                .maxAge(3600);
        super.addCorsMappings(registry);
    }*/

}
