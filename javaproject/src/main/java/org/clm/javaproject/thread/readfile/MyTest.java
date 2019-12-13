package org.clm.javaproject.thread.readfile;

import java.io.*;
import java.util.Arrays;

/**
 * 未完成
 */
public class MyTest {

    static class ThreadReadFile implements Runnable{
        private long start;
        private long end;
        private BufferedInputStream in;
        private static StringBuilder sb = new StringBuilder();

        public ThreadReadFile(){

        }
        public ThreadReadFile(long start,long end,BufferedInputStream in){
            this.start = start;
            this.end = end;
            this.in = in;
        }

        @Override
        public synchronized void run() {
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("d:\\2.txt"));
                byte[] bytes = new byte[5];
                int n = -1;

                while ((n = in.read(bytes,0,bytes.length)) != -1) {
                    String str = new String(bytes,0,n,"UTF8");
                    System.out.println(Thread.currentThread().getName()+":"+str);
                    sb.append(str);
                    out.write(n);

                }
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        //线程数
        int thread_num = 3;
        File file = new File("D:\\threadfile.txt");

        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
            //文件的长度
            long filelength = file.length();
            System.out.println(filelength);
            Thread[] threadlist = new Thread[thread_num];

            //文件长度除以线程数：每个线程要处理多少数据
            long basenum = filelength / thread_num;
            //最后一个线程要多处理的数据
            long tmp_num = filelength % thread_num;
            long start = 0;
            long end = tmp_num;
            for (int i = 0; i < thread_num; i++) {
                ThreadReadFile trf = new ThreadReadFile(start, end, in);
                threadlist[i] = new Thread(trf,String.valueOf(i));
                start = end ;
                if(i == thread_num-1){
                    end += basenum+tmp_num;
                }else{
                    end += basenum;
                }
            }

            Arrays.asList(threadlist).forEach(o->o.start());
            Arrays.asList(threadlist).forEach(o->{
                try {
                    o.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            System.out.println(ThreadReadFile.sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
