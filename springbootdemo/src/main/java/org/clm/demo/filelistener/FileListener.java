package org.clm.demo.filelistener;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

public class FileListener extends FileAlterationListenerAdaptor {
    public static final Logger logger = LoggerFactory.getLogger(FileListener.class);
    @Autowired
    //FileConfigLoader fileConfigLoader;
    /**
     * @description 启动监听
     * @param
     * @return
     * @version 2.0, 2019/1/24 15:08
     * @author <a href="Tastill@**.cn">Tastill</a>
     */
    @Override
    public void onStart(FileAlterationObserver observer) {
        //logger.info("启动监听器：");
    }
    @Override
    public void onDirectoryCreate(File directory) {
        logger.info("有新文件夹生成："+directory.getName());
    }
    @Override
    public void onDirectoryChange(File directory) {
        logger.info("有文件夹内容发生变化："+directory.getName());
    }
    @Override
    public void onDirectoryDelete(File directory) {
        logger.info("有文件夹被删除："+directory.getName());
    }
    /**
     * @description 文件创建
     * @param
     * @return
     * @version 2.0, 2019/1/24 14:59
     * @author <a href="Tastill@**.cn">Tastill</a>
     */
    @Override
    public void onFileCreate(File file){
        logger.info("有新文件生成："+file.getName());
    }
    /**
     * @description 文件内容发生变化
     * @param
     * @return
     * @version 2.0, 2019/1/24 15:05
     * @author <a href="Tastill@**.cn">Tastill</a>
     */
    @Override
    public void onFileChange(File file){
        logger.info("有文件被修改："+file.getName());


    }
    /**
     * @description 文件被删除
     * @param
     * @return
     * @version 2.0, 2019/1/24 16:13
     * @author <a href="Tastill@**.cn">Tastill</a>
     */
    @Override
    public void onFileDelete(File file){
        logger.info("有文件被删除："+file.getName());
    }
    /**
     * @description 监听停止
     * @param
     * @return
     * @version 2.0, 2019/1/24 15:07
     * @author <a href="Tastill@**.cn">Tastill</a>
     */
    @Override
    public void onStop(FileAlterationObserver observer){
        // System.out.println("监听停止");
    }

}
