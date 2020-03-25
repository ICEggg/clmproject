package clm.controller;

import clm.domain.Account;
import clm.domain.User;
import clm.exception.SysException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @RequestMapping注解：
 *  path和value属性是一样的
 *  method：设置请求方式，post，get。method={RequestMethod.POST}
 *  params:请求参数，params={"username"}
 *  headers:请求消息头
 *
 * 常用注解：
 * 接收参数：
 * @RequestParam:@RequestParam(name="name" String username)，require参数表示前端传参数的时候，key一定是name
 * @RequestBody：get方法不适用，前端表单传参。@RequestBody String body,打印的就是前端表单数据
 * @PathVariable
 * @RequestHeader 获取请求头信息，@RequestHeader(value="Accept") String header
 * @ModelAttribute 在调用一个方法之前，执行的方法
 *      场景：进入方法之前，拿到参数去库里查，拿到返回值。然后再执行方法
 *  @SessionAttributes：参数存入session，用于方法之间参数共享
 * @ResonseBody 返回的时候，把对象转成json
 */

@Controller
@RequestMapping(value = "/user")
@SessionAttributes(value={"美美"})//把msg=美美，存入到session域中
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

    /**
     * 测试@SessionAttribute注解
     *
     * Model对象，可以设置参数到HttpServletRequest请求域中
     * @param model
     * @return
     */
    public String testSessionAttribute(Model model){
        //底层会存储到request域对象中
        model.addAttribute("msg","美美");
        return "success";
    }
    //从session中获取值
    public  String getSessionAttributes(ModelMap modelMap){
        String msg = (String) modelMap.get("msg");
        System.out.println(msg);
        return "success";
    }
    //从session中删除值
    public String delSessionAttributes(SessionStatus status){
        status.setComplete();
        return "success";
    }

    @ResponseBody
    @RequestMapping(path = "/testAjax")
    public User testAjax(@RequestBody User user){
        System.out.println(user);
        return user;
    }

    /**
     * 文档上传     springmvc方式
     * MultipartFile upload,这个upload一定要和前端input的name属性相同，都要叫upload
     */
    @RequestMapping(path = "/fileuploadSpringMvc")
    public String fileuploadSpringMvc(HttpServletRequest request, MultipartFile upload) throws Exception {
        System.out.println("文件上传");

        //使用fileupload组件完成文件上传
        //上传的位置
        String path = request.getSession().getServletContext().getRealPath("/uploads/");
        //判断该路径是否存在
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }

        //获取上传文件项
        //获取上传文件的名称
        String filename = upload.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replace("-","");
        filename = uuid+ "_" +filename ;

        //完成文件上传
        upload.transferTo(new File(path,filename));

        return "success";
    }

    //文档上传      跨服务的方式。比如有一个专门的图片服务器 (未测试)
    @RequestMapping(path = "/fileuploadAcrossService")
    public String fileuploadAcrossService(MultipartFile upload) throws IOException {
        System.out.println("文件上传");

        //使用fileupload组件完成文件上传
        //上传的位置
        String path = "http://localhost:9090/uoloads/";

        //获取上传文件项
        //获取上传文件的名称
        String filename = upload.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replace("-","");
        filename = uuid+ "_" +filename ;

        //创建客户端的对象
        Client client = Client.create();
        //和图片服务器进行连接
        WebResource webResource = client.resource(path + filename);

        //上传文件
        webResource.put(upload.getBytes());

        return "success";

    }

    //文档上传     这是传统的方式。。。。
    @RequestMapping(path = "/fileuploadServlet")
    public String fileuploadServlet(HttpServletRequest request) throws Exception {
        System.out.println("文件上传");

        //使用fileupload组件完成文件上传
        //上传的位置
        String path = request.getSession().getServletContext().getRealPath("/uploads/");
        //判断该路径是否存在
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }

        //解析request对象，获取上传文件项
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        //解析request
        List<FileItem> items = upload.parseRequest(request);

        for (FileItem item : items){
            //判断当前item对象是否是上传文件项
            if(item.isFormField()){
                //普通表单项
            }else {
                //说明上传文件项
                //获取上传文件的名称
                String filename = item.getName();
                //防止文件名重复，就给个唯一值
                String uuid = UUID.randomUUID().toString().replace("-","");
                filename = uuid+ "_" +filename ;
                //完成文件上传
                item.write(new File(path,filename));
                //删除临时文件
                item.delete();
            }
        }

        return "success";
    }

    @RequestMapping(path = "/testException")
    public void testException() throws SysException {
        try {
            int a = 10/0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SysException("这是自定义异常报错信息");
        }

    }




}
