package javaproject.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 4种线程池例子
 */
public class Threadpools {
	
	public static class ThreadForpools implements Runnable{
		 private Integer index;
		    public  ThreadForpools(Integer index)
		    {
		     this.index=index;
		    }
		    public void run() {
		        /***
		         * 业务......省略
		          */
		        try {
		            System.out.println("开始处理线程！！！");
		            Thread.sleep(1000);
		            System.out.println("我的线程标识是："+index);
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
		    }
	}
	
	
	 /**
     * 我们获取四次次线程，观察4个线程地址
     * @param args
     */
	
	//创建一个可缓存线程池，应用中存在的线程数可以无限大
	public static void newCachedThreadPooltest(){
		 ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
	        System.out.println("****************************newCachedThreadPool*******************************");
	        for(int i=1;i<=5;i++)
	        {
	            final int index=i;
	          newCachedThreadPool.execute(new ThreadForpools(index));
	        }
	}
	
	//创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。示例代码如下：
	public static void newFixedThreadPooltest(){
		 //线程池允许同时存在两个线程
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(2);
        System.out.println("****************************newFixedThreadPool*******************************");
        for(int i=1;i<=5;i++)
        {
            final int index=i;
            newFixedThreadPool.execute(new ThreadForpools(index));
        }
	}
	
	//创建一个定长线程池，支持定时及周期性任务执行。延迟执行示例代码如下：
	public static void newScheduledThreadPooltest(){
		 ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(2);
	        System.out.println("****************************newFixedThreadPool*******************************");
	        for(int i=1;i<=4;i++)
	        {
	            final int index=i;
	            //延迟三秒执行
	            newScheduledThreadPool.schedule(new ThreadForpools(index),3, TimeUnit.SECONDS);
	        }
	}
	
	//创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。示例代码如下：
	public static void newSingleThreadExecutortest(){
		ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
	        System.out.println("****************************newFixedThreadPool*******************************");
	        for(int i=1;i<=5;i++)
	        {
	            final int index=i;
	            newSingleThreadExecutor.execute(new ThreadForpools(index));
	        }
	}
	
	
    public static  void main(String[]args)
    {
    	//创建一个可缓存线程池，应用中存在的线程数可以无限大
    	//newCachedThreadPooltest();
    	
    	//创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。示例代码如下
    	//newFixedThreadPooltest();
    	
    	//创建一个定长线程池，支持定时及周期性任务执行。延迟执行示例代码如下：
    	//newScheduledThreadPooltest();
    	
    	//创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。示例代码如下：
    	newSingleThreadExecutortest();
    }
}
