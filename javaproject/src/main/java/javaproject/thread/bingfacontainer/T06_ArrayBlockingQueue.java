package javaproject.thread.bingfacontainer;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 *  阻塞式容器
 *  ArrayBlockingQueue  有界队列
 * ArrayBlockingQueue 指定了容器的大小为10个，如果再往里添加，会报错Queue full
 *
 */
public class T06_ArrayBlockingQueue {

     static BlockingQueue strs = new ArrayBlockingQueue<String>(10);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            try {
                strs.put("a"+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            strs.put("a");  //满了就会等待，程序阻塞
            //strs.add("a");    //会报异常  Queue full
            //strs.offer("a");  //offer不会报异常，但是也不会把数据加进去
            strs.offer("a",1,TimeUnit.SECONDS); //等待一段时间往里加，加不进去就不加了
            System.out.println(strs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
