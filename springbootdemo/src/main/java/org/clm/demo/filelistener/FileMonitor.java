package org.clm.demo.filelistener;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * 监听#文件夹#里文件变化，如果该文件夹下的文件有变化，就会打印出信息
 */
//@Component
public class FileMonitor  {
    //监听#文件夹#里文件变化
    /*@PostConstruct
    public void initFileMonitor() {
        // 监控目录
        String rootDir = "D:\\t.txt";
        // 轮询间隔 5 秒
        Integer time = 5;
        long interval = TimeUnit.SECONDS.toMillis(time);
        // 创建一个文件观察器用于处理文件的格式,
        //                        FileFilterUtils.suffixFileFilter(".txt")
        FileAlterationObserver _observer = new FileAlterationObserver(
                rootDir,
                FileFilterUtils.and(
                        FileFilterUtils.fileFileFilter()),  //过滤文件格式
                null);
        FileAlterationObserver observer = new FileAlterationObserver(rootDir);

        observer.addListener(new FileListener()); //设置文件变化监听器
        //创建文件变化监听器
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
        // 开始监控
        try {
            monitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    //通过过滤文件夹下的，文件的格式，来监听一个文件的变化
    @PostConstruct
    public void initFileMonitor() {
        // 监控目录
        String rootDir = "D:\\aaa";
        // 轮询间隔 5 秒
        Integer time = 5;
        long interval = TimeUnit.SECONDS.toMillis(time);
        // 创建一个文件观察器用于处理文件的格式,
                                FileFilterUtils.suffixFileFilter(".txt");
        //过滤文件格式(这段代码意思是，监听以.txt结尾的文件，的变化)
        FileAlterationObserver observer = new FileAlterationObserver(rootDir, FileFilterUtils.and(
                FileFilterUtils.fileFileFilter(),FileFilterUtils.suffixFileFilter(".txt")));

        observer.addListener(new FileListener()); //设置文件变化监听器
        //创建文件变化监听器
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
        // 开始监控
        try {
            monitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
