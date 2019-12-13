package com.clm.demo.mvc.primiary.controller;

import com.clm.demo.mvc.primiary.entity.User;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RestController 相当于   @ResponseBody+@Controller
 *
 * @responseBody注解的作用是将controller的方法返回的对象通过适当的转换器转换为指定的格式之后，
 *  写入到response对象的body区，通常用来返回JSON数据或者是XML
 *
 *  比如一个接口的返回类型是对象，前台接收到的就是json格式的
 */
@RestController
@RequestMapping(value="/helloCon")
@Api(value = "HelloController操作api")
public class HelloController {
    //Value注解用来读取配置文件中的值
    @Value("${pageSize}")
    private Integer pageSize;
    @Value("${devName}")
    private String devName;

    @RequestMapping(value="/hello",method = RequestMethod.GET)
    public String helloSpringBoot(){
        return "hello springboot";
    }

    @RequestMapping(value="/showInfo",method = RequestMethod.GET)
    public String showInfo(){
        return "pageSize:"+pageSize+",devName="+devName;
    }

    @RequestMapping(value = {"/t","/temp","/template"},method = RequestMethod.GET)
    public String  showIndexHtml(){
        return "index";
    }

    @RequestMapping(value = "userentity",method = RequestMethod.GET)
    public User  userentity(){
        User user = new User("1","aaa","111");
        return user;

        /*
        {
            "username": "aaa",
            "password": "111",
            "id": "1"
        }
        */
    }
}
