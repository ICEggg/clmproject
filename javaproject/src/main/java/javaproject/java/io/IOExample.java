package javaproject.java.io;

import java.io.*;


/**
 * BufferedInputStream、BufferedOutputStream（缓存字节流）使用方式和字节流差不多，但是效率更高（推荐使用）
 * InputStream、OutputStream（字节流）
 * InputStreamReader、OutputStreamWriter（字节流，这种方式不建议使用，不能直接字节长度读写）。使用范围用做字符转换
 * BufferedReader、BufferedWriter(缓存流，提供readLine方法读取一行文本)
 * Reader、PrintWriter（PrintWriter这个很好用，在写数据的同事可以格式化）
 *
 * 其他小功能：
 * 获取一个文件有多少行数据
 */
public class IOExample {

    /**
     * BufferedInputStream、BufferedOutputStream（缓存字节流）使用方式和字节流差不多，但是效率更高（推荐使用）
     */
    public void one(){
        try {
            //读取文件(缓存字节流)
            BufferedInputStream in = new BufferedInputStream(new FileInputStream("d:\\1.txt"));
            //写入相应的文件
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("d:\\2.txt"));
            //读取数据
            //一次性取多少字节
            byte[] bytes = new byte[2048];
            //接受读取的内容(n就代表的相关数据，只不过是数字的形式)
            int n = -1;
            //循环取出数据
            while ((n = in.read(bytes,0,bytes.length)) != -1) {
                //转换成字符串
                String str = new String(bytes,0,n,"GBK");
                System.out.println(str);
                //写入相关文件
                out.write(bytes, 0, n);
            }
            //清楚缓存
            out.flush();
            //关闭流
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * InputStream、OutputStream（字节流）
     */
    public void two(){
        try {
            //读取文件(字节流)
            InputStream in = new FileInputStream("d:\\1.txt");
            //写入相应的文件
            OutputStream out = new FileOutputStream("d:\\2.txt");
            //读取数据
            //一次性取多少字节
            byte[] bytes = new byte[2048];
            //接受读取的内容(n就代表的相关数据，只不过是数字的形式)
            int n = -1;
            //循环取出数据
            while ((n = in.read(bytes,0,bytes.length)) != -1) {
                //转换成字符串
                String str = new String(bytes,0,n,"GBK"); //这里可以实现字节到字符串的转换，比较实用
                System.out.println(str);
                //写入相关文件
                out.write(bytes, 0, n);
            }
            //关闭流
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * InputStreamReader、OutputStreamWriter（字节流，这种方式不建议使用，不能直接字节长度读写）。使用范围用做字符转换
     */
    public void three(){
        try {
            //读取文件(字节流)
            InputStreamReader in = new InputStreamReader(new FileInputStream("d:\\1.txt"),"GBK");
            //写入相应的文件
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("d:\\2.txt"));
            //读取数据
            //循环取出数据
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = in.read()) != -1) {
                System.out.println(len);
                //写入相关文件
                out.write(len);
            }
            //清楚缓存
            out.flush();
            //关闭流
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * BufferedReader、BufferedWriter(缓存流，提供readLine方法读取一行文本)
     */
    public void four(){
        try {
            //读取文件(字符流)
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("d:\\1.txt"),"GBK"));//这里主要是涉及中文
            //BufferedReader in = new BufferedReader(new FileReader("d:\\1.txt")));
            //写入相应的文件
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("d:\\2.txt"),"GBK"));
            //BufferedWriter out = new BufferedWriter(new FileWriter("d:\\2.txt"))；
            //读取数据
            //循环取出数据
            String str = null;
            while ((str = in.readLine()) != null) {
                System.out.println(str);
                //写入相关文件
                out.write(str);
                out.newLine();
            }

            //清楚缓存
            out.flush();
            //关闭流
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reader、PrintWriter（PrintWriter这个很好用，在写数据的同事可以格式化）
     */
    public void five(){
        try {
            //读取文件(字节流)
            Reader in = new InputStreamReader(new FileInputStream("d:\\1.txt"),"GBK");
            //写入相应的文件
            PrintWriter out = new PrintWriter(new FileWriter("d:\\2.txt"));
            //读取数据
            //循环取出数据
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = in.read()) != -1) {
                System.out.println(len);
                //写入相关文件
                out.write(len);
            }
            //清楚缓存
            out.flush();
            //关闭流
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取一个文件有多少行数据
     * @param file
     * @return
     */
    public long getLineNumber(File file) {
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                LineNumberReader lineNumberReader = new LineNumberReader(fileReader);

                //该方法传入的参数是Long.MAX_VALUE，      实际返回的是：实际上跳过字符的个数
                lineNumberReader.skip(Long.MAX_VALUE);
                //LineNumberReader.getLineNumber()从0开始计数，所以这里算行数的时候需要+1。
                long lines = lineNumberReader.getLineNumber() + 1;
                System.out.println(lines);
                fileReader.close();
                lineNumberReader.close();
                return lines;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        IOExample ex = new IOExample();
        long num = ex.getLineNumber(new File("C:\\Users\\dev\\Desktop\\tmpfile\\bm.json"));
        //System.out.println(num);
    }

}
