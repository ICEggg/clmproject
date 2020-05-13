package org.clm.demo.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *  spring security
 *
 *
 *
 */
@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()                    //  定义当需要用户登录时候，转到的登录页面。
                .and()
                .authorizeRequests()        // 定义哪些URL需要被保护、哪些不需要被保护
                .anyRequest()               // 任何请求,登录后可以访问
                .authenticated();



    }

    //定制认证规则
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);

        auth.inMemoryAuthentication()
                .withUser("zhangsan").password("123456").roles("VIP1","VIP2")
                .and()
                .withUser("lisi").password("123456").roles("VIP2","VIP3")
                .and()
                .withUser("wangwu").password("123456").roles("VIP1","VIP3");
    }
}
