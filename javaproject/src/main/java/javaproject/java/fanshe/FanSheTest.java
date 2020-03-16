package javaproject.java.fanshe;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * java 反射
 *
 * 比如idea自动提示的功能，用到的就是反射
 * 某个类，自动提示这个类有哪些方法，就是把这个类用反射加载到内存中，然后把这个类的所有方法列出来
 */
public class FanSheTest {
    public static void main(String[] args) throws Exception {
        //先要获取配置文件，通过类加载机器加载配置文件，获取输入流
        ClassLoader classLoader = FanSheTest.class.getClassLoader();
        String user_dir = System.getProperty("user.dir"); //D:\project\MyProject
        String prop_dir = user_dir+ File.separator+"javaproject\\src\\main\\resource\\fanshe.properties";
        //下面配置文件正常是放在src下，但是这个是Myproject下的javaproject，所以拿不到
        //InputStream in = classLoader.getResourceAsStream("fanshe.properties");
        InputStream in = new FileInputStream(prop_dir);

        //Properties加载输入流，就可以获取配置文件的参数了
        Properties prop = new Properties();
        prop.load(in);

        //获取类名，方法名
        String className = prop.getProperty("className");
        String methodName = prop.getProperty("methonName");

        //加载该类进内存
        Class cls = Class.forName(className);
        //获取类的实例
        Object obj = cls.newInstance();
        //获取方法
        Method method = cls.getMethod(methodName);
        //调用方法
        method.invoke(obj);
    }
}
