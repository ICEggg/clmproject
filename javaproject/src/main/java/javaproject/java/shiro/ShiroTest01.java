package javaproject.java.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * shiro ,用户认证用户，权限分配等功能
 * 理论上 shiro > jwt
 *
 * shiro是一套权限管理框架，包括认证、授权等，在使用时直接写相应的接口就可以了，已经把底层处理都写好了
 * 而jwt只是一种生成token的机制，所有权限处理逻辑还需要自己写
 *
 * 这是用户登录的例子
 */
public class ShiroTest01 {
    /**
     * 测试用户认证：
     *      认证：用户登录
     *
     *      1.根据配置文件创建SecurityManagerFactory
     *      2.通过工厂获取SecurityManager
     *      3.将SecurityManager绑定到当前运行环境
     *      4.从当前运行环境中构造subject
     *      5.构造shiro登录的数据
     *      6.主体登陆
     */

    public static void main(String[] args) {
        String filepath = System.getProperty("user.dir")+"\\javaproject\\src\\main\\resource\\shiro-test-1.ini";
        System.out.println(filepath);
        //1.根据配置文件创建SecurityManagerFactory
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(filepath);
        //2.通过工厂获取SecurityManager
        SecurityManager securityManager = factory.getInstance();
        //3.将SecurityManager绑定到当前运行环境
        SecurityUtils.setSecurityManager(securityManager);
        //4.从当前运行环境中构造subject
        Subject subject = SecurityUtils.getSubject();
        //5.构造shiro登录的数据
        String username = "zhangsan";
        String password = "123456";
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        //6.主体登陆
        subject.login(token);
        //7.验证用户是否登录成功
        System.out.println("用户是否登录成功="+subject.isAuthenticated());
        //8.获取登录成功的数据
        System.out.println(subject.getPrincipal());
    }



}
