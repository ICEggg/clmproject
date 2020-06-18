package javaproject.JVM.classloader;

import java.util.Arrays;
import java.util.List;

/**
 * 三种类加载器
 * 启动类加载器 BootstrapClassloader
 * 扩展类加载器 ExtentionClassloader
 * 应用程序类加载器 ApplicationClassloader
 */
public class DiffClassLoaderLoadingPath {
    public static void main(String[] args) {
        //bootClassLoadLoadingPath();

        extClassLoadLoadingPath();
        //appClassLoadLoadingPath();
    }

    //启动类加载器 BootstrapClassloader
    public static  void bootClassLoadLoadingPath(){
        String bootStrapLoadingPath = System.getProperty("sun.boot.class.path");

        List<String> list = Arrays.asList(bootStrapLoadingPath.split(";"));
        for (String path : list) {
            System.out.println("启动类加载器----加载的目录：" + path);
        }
    }

    //扩展类加载器 ExtentionClassloader
    public static  void extClassLoadLoadingPath(){
        String bootStrapLoadingPath = System.getProperty("java.ext.dirs");

        List<String> list = Arrays.asList(bootStrapLoadingPath.split(";"));
        for (String path : list) {
            System.out.println("启动类加载器----加载的目录：" + path);
        }
    }

    //应用程序类加载器 ApplicationClassloader
    public static  void appClassLoadLoadingPath(){
        String bootStrapLoadingPath = System.getProperty("java.class.path");

        List<String> list = Arrays.asList(bootStrapLoadingPath.split(";"));
        for (String path : list) {
            System.out.println("启动类加载器----加载的目录：" + path);
        }
    }


}
