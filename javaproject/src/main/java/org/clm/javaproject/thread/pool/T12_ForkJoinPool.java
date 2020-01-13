package org.clm.javaproject.thread.pool;

import org.clm.javaproject.thread.TTT;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class T12_ForkJoinPool {
    static int[] nums = new int[1000000];
    static final int MAX_NUM=50000;
    static Random r = new Random();

    static {
        for (int i = 0; i < nums.length; i++) {
            nums[i] = r.nextInt(100);
        }
        System.out.println(Arrays.stream(nums).sum());
    }

    //继承RecursiveAction，是没有返回值的
    static class AddTask extends RecursiveAction{
        int start;
        int end;

        AddTask(int start,int end){
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if(end-start <= MAX_NUM){
                long sum = 0L;
                for (int i = start; i < end; i++) sum += nums[i];
                System.out.println("from "+start+"to "+end+"="+sum);
            }else{
                int middle = start + (end-start)/2;
                AddTask subTask1 = new AddTask(start,middle);
                AddTask subTask2 = new AddTask(middle,end);
                subTask1.fork();
                subTask2.fork();

            }
        }
    }


    //继承RecursiveTask才有返回值
//    static class AddTask extends RecursiveTask<Long> {
//        int start;
//        int end;
//
//        AddTask(int start,int end){
//            this.start = start;
//            this.end = end;
//        }
//
//        @Override
//        protected Long compute() {
//            int tmp = end - start;
//            if(tmp<=MAX_NUM){
//                long sum = 0l;
//                for (int i = start; i < end; i++) {
//                    sum += nums[i];
//                    System.out.println("from "+start+"to "+end+"="+sum);
//                }
//                return sum;
//            }
//
//            int middle = start+(end-start)/2;
//            AddTask AddTask1 = new AddTask(start, middle);
//            AddTask AddTask2 = new AddTask(middle, end);
//            AddTask1.fork();
//            AddTask2.fork();
//
//            return Long.valueOf(AddTask1.join()+AddTask2.join());
//        }
//    }

    public static void main(String[] args) throws IOException {
        /*//RecursiveAction任务
        ForkJoinPool fjp = new ForkJoinPool();
        AddTask task = new AddTask(0,nums.length);
        fjp.execute(task);
        //因为是精灵线程，所以要加System.in.read，阻塞一下，才能看到打印的结果
        System.in.read();*/

        /*//RecursiveTask任务
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        AddTask task2 = new AddTask(0,nums.length);
        forkJoinPool.execute(task2);
        long result = task2.join();
        System.out.println(result);*/
    }

}
