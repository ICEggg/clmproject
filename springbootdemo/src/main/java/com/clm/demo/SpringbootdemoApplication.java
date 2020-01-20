package com.clm.demo;

import com.clm.demo.util.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//还不知道下面这几个注解的含义
//@ServletComponentScan
//@Configuration


//配置监听器如果用WebListener注解的话，就开启这个
//@ServletComponentScan(basePackages ="com.clm.demo.listener")
@SpringBootApplication      //springboot 启动类必须有这个注解
@EnableSwagger2        //开启swagger
@EnableScheduling   //开启这个注解，会找到@Scheduled注解的类，开启定时任务
@EnableCaching      //开启spring缓存
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
