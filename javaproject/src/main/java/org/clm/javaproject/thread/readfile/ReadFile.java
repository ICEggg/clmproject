package org.clm.javaproject.thread.readfile;

import java.io.*;

/**
 * 多线程读取一个大文件
 * 这个评论说太慢，还没看完
 */
public class ReadFile extends Thread{
        //定义字节数组（取水的竹筒）的长度
        private final int BUFF_LEN = 32;
        //定义读取的起始点
        private long start;
        //定义读取的结束点
        private long end;
        //读取文件对应的输入流
        private InputStream is;
        //将读取到的字节输出到raf中
        private RandomAccessFile raf;
        //构造器，传入输入流，输出流和读取起始点、结束点
        public ReadFile(long start, long end, InputStream is, RandomAccessFile raf) {
            //输出该线程负责读取的字节位置
            System.out.println(start + "---->" + end);
            this.start = start;
            this.end = end;
            this.is = is;
            this.raf = raf;
        }
        public void run() {
            try {
                is.skip(start);
                raf.seek(start);
                //定义读取输入流内容的的缓存数组（竹筒）
                byte[] buff = new byte[BUFF_LEN];
                //本线程负责读取文件的大小
                long contentLen = end - start;
                //定义最多需要读取几次就可以完成本线程的读取
                long times = contentLen / BUFF_LEN + 4;
                //实际读取的字节数
                int hasRead = 0;
                for (int i = 0; i < times; i++) {
                    hasRead = is.read(buff);
                    //如果读取的字节数小于0，则退出循环！
                    if (hasRead < 0) {
                        break;
                    }
                    raf.write(buff, 0, hasRead);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            //使用finally块来关闭当前线程的输入流、输出流
            finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                    if (raf != null) {
                        raf.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    class MutilDown {
        public static void main(String[] args) {
            final int DOWN_THREAD_NUM = 4;
            final String OUT_FILE_NAME = "d:/copy勇敢的心.rmvb";
            InputStream[] isArr = new InputStream[DOWN_THREAD_NUM];
            RandomAccessFile[] outArr = new RandomAccessFile[DOWN_THREAD_NUM];
            try {
                isArr[0] = new FileInputStream("d:/勇敢的心.rmvb");
                //获取文件的长度
                long fileLen = new File("d:/勇敢的心.rmvb").length();
                System.out.println("文件的大小" + fileLen);
                //以输出文件名创建第一个RandomAccessFile输出流
                outArr[0] = new RandomAccessFile(OUT_FILE_NAME, "rw");
                //创建一个与文件相同大小的空文件
                for (int i = 0; i < fileLen; i++) {
                    outArr[0].write(0);
                }
                //每线程应该读取的字节数
                long numPerThred = fileLen / DOWN_THREAD_NUM;
                //整个文件整除后剩下的余数
                long left = fileLen % DOWN_THREAD_NUM;
                for (int i = 0; i < DOWN_THREAD_NUM; i++) {
                    //为每个线程打开一个输入流、一个RandomAccessFile对象，
                    //让每个线程分别负责读取文件的不同部分。
                    if (i != 0) {
                        isArr[i] = new FileInputStream("d:/勇敢的心.rmvb");
                        //以指定输出文件创建多个RandomAccessFile对象
                        outArr[i] = new RandomAccessFile(OUT_FILE_NAME, "rw");
                    }
                    if (i == DOWN_THREAD_NUM - 1) {
                        //最后一个线程读取指定numPerThred+left个字节
                        new ReadFile(i * numPerThred, (i + 1) * numPerThred
                                + left, isArr[i], outArr[i]).start();
                    } else {
                        //每个线程负责读取一定的numPerThred个字节
                        new ReadFile(i * numPerThred, (i + 1) * numPerThred,
                                isArr[i], outArr[i]).start();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

}
