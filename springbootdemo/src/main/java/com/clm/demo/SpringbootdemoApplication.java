package com.clm.demo;

import com.clm.demo.util.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//还不知道下面这几个注解的含义
//@ServletComponentScan
//@Configuration
//@EnableScheduling
//@EnableCaching

//配置监听器如果用WebListener注解的话，就开启这个
//@ServletComponentScan(basePackages ="com.clm.demo.listener")
@SpringBootApplication
@EnableSwagger2
public class SpringbootdemoApplication /*extends SpringBootServletInitializer*/ {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootdemoApplication.class, args);
    }

    //为了解决nosession延迟加载的问题（还不知道为什么）
    @Bean
    public OpenEntityManagerInViewFilter openEntityManagerInViewFilter(){
        return new OpenEntityManagerInViewFilter();
    }

    //为了在别的地方可以用@
    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }
}
