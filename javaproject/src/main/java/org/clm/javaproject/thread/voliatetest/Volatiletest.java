package org.clm.javaproject.thread.voliatetest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


public class Volatiletest implements Runnable{
	public boolean status = false;
	//public static volatile int num = 0;
	
	static CountDownLatch countDownLatch = new CountDownLatch(30);
	//使用原子操作类
    public static AtomicInteger num = new AtomicInteger(0);
    
	/**
     * 状态切换为true
     */
    public void changeStatus(){
        status = true;
    }
    
	public void run() {
		for(int i =0;i<100;i++){
			num.incrementAndGet();
		}
		countDownLatch.countDown();
	}
	
	
	public static void main(String[] args) {
		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        for(int i=1;i<=5;i++)
        {
          newCachedThreadPool.execute(new Volatiletest());
        }
        try {
			countDownLatch.await();
			System.out.println(num);
		}
		catch(InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		 /*for(int i=1;i<=2;i++){
			 if(i==1){
				 new Thread(){
					 public void run(){
						 new Threadforvolatile().changeStatus(); 
					 }
				 }.start();
			 }
			 if(i==2){
				 new Thread(){
					 public void run(){
						 new Threadforvolatile().run(); 
					 }
				 }.start();
			 }
			
		 }*/
	}

}
