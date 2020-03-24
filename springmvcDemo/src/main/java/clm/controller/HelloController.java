package clm.controller;

import clm.domain.Account;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @RequestMapping注解：
 *  path和value属性是一样的
 *  method：设置请求方式，post，get。method={RequestMethod.POST}
 *  params:请求参数，params={"username"}
 *  headers:请求消息头
 *
 */

@Controller
@RequestMapping(value = "/user")
public class HelloController {

    @RequestMapping(path = "/hello")
    public String hello(){
        System.out.println("hello");
        //这个返回值，就对应一个：success.jsp的文件
        return "success";
    }

    /**
     * 接收get请求参数
     * @param username
     * @return
     */
    @RequestMapping(path = "/param")
    public String param(String username){
        System.out.println(username);
        //这个返回值，就对应一个：success.jsp的文件
        return "success";
    }

    /**
     * post传参
     * @return
     */
    @RequestMapping(path = "/saveAccount")
    public String saveAccount(Account account){
        System.out.println(account);
        return "success";
    }

}
