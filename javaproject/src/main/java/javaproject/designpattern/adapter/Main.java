package javaproject.designpattern.adapter;

import java.io.*;

/**
 * 适配器模式
 * Adapter 适配器模式       接口转换器
 *
 * 比如手机充电器，中国的和欧洲的插座是不一样的，中国电压220V，欧洲电压110V，所以中间需要一个转接头
 * 中间的转接头就是适配器，能让我的手机，在其他的插座上也能充电
 *
 * 下面这个io的例子，BufferedReader是一行一行读文件的，FileInputStream是按字节流读的，
 * 所以就需要一个转换器把字节流的，转换成一行一行的，
 * InputStreamReader就相当于是转换器
 */
public class Main {
    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("D://test.txt");
        //InputStreamReader就相当于是转换器
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(reader);
        String line = br.readLine();
        System.out.println(line);
        br.close();
    }
}
