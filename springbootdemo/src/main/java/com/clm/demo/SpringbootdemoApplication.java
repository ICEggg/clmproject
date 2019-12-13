package com.clm.demo;

import com.clm.demo.util.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
//@ServletComponentScan
//@Configuration
//@EnableScheduling
//@EnableCaching
public class SpringbootdemoApplication /*extends SpringBootServletInitializer*/ {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootdemoApplication.class, args);
    }

/*    //为了打包springboot项目(注释了好像也能打包)
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }*/

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
