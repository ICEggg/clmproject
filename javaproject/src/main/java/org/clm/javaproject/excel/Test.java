package org.clm.javaproject.excel;


import org.clm.javaproject.excel.thread.MyThread;
import java.io.*;



public class Test {

    public static void main(String[] args) {
        //excel文件路径
        String excelPath = "E:\\JOB\\file";    //excel文件夹的路径
        String outPutPath = "E:\\JOB\\result\\";    //输出的路径
        int index = 16;  //线程数

        try {
            File file = new File(excelPath);
            if(file.isDirectory()){
                //如果是文件夹
                File[] files = file.listFiles();
                way(files,8,outPutPath);
            }else{
                //是文件
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param filelist  文件列表数组
     * @param index     线程数
     * @param outPutPath    输出路径
     */
    public static void way(File[] filelist , int index,String outPutPath){
        if(index > filelist.length){
            index = filelist.length;
        }

        //根据线程数，计算开始和结尾
        int end = 0;
        int basenum = filelist.length/index;
        int remaindernum = filelist.length%index;
        for (int i = 0; i < index; i++) {
            int start = end;
            end = start + basenum;
            if(i == index-1){
                end = start + basenum + remaindernum;
            }
            //System.out.println(start +"--------"+end);
            MyThread m = new MyThread("thread"+String.valueOf(i),filelist,start,end);
            new Thread(m).start();
        }
    }





}
