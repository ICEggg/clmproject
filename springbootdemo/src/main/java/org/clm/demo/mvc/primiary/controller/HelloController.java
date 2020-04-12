package org.clm.demo.mvc.primiary.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.clm.demo.mvc.primiary.entity.User;
import org.clm.demo.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    private Logger logger = LoggerFactory.getLogger(HelloController.class);
    //Value注解用来读取配置文件中的值
    @Value("${pageSize}")
    private Integer pageSize;
    @Value("${devName}")
    private String devName;

    @RequestMapping(value="/hello",method = RequestMethod.GET)
    public String helloSpringBoot() {
        logger.info("进入了helloSpringBoot方法");
        try {
            Thread.sleep(1000);
            throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("!!!");
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
    public User userentity(){
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


    /**
     * 下面这三个方法是测试HttpUtil的post，表单提交和json提交方法有没有问题，测试下来没有问题
     */
    @RequestMapping(value = "/way",method = RequestMethod.GET)
    public void way(){
        HttpUtil util = new HttpUtil();
        String form_url = "http://127.0.0.1:8083/dev/helloCon/testPostForm";
        Map<String,String> formmap = new HashMap<>();
        formmap.put("username","aaa");
        util.postWithForm(form_url,formmap);

        String json_url = "http://127.0.0.1:8083/dev/helloCon/testPostJson";
        JSONObject obj = new JSONObject();
        obj.put("username","aaa");
        util.postWithJson(json_url,obj.toString());
    }

    @RequestMapping(value = "/testPostForm",method = RequestMethod.POST)
    public void testPostForm(@RequestParam String username){
        System.out.println("这是post，提交表单参数："+username);
    }
    @RequestMapping(value = "/testPostJson",method = RequestMethod.POST)
    public void testPostJson(@RequestBody Map<String, String> tmpmap){
        System.out.println("这是post，提交json参数："+tmpmap.get("username"));
    }
}
