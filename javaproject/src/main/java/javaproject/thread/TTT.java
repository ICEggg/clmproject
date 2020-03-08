package javaproject.thread;


import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;


public class TTT {
    static int[] nums = new int[100];
    static final int MAX_NUM=10;
    static Random r = new Random();

    static {
        for (int i = 0; i < nums.length; i++) {
            nums[i] = r.nextInt(100);
        }
        System.out.println(Arrays.stream(nums).sum());
    }

    static class addTask extends RecursiveTask<Long> {
        int start;
        int end;

        addTask(int start,int end){
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            int tmp = end - start;
            if(tmp<=MAX_NUM){
                long sum = 0l;
                for (int i = start; i < end; i++) {
                    sum += nums[i];
                    System.out.println("from "+start+"to "+end+"="+sum);
                }
                return sum;
            }

            int middle = start+(end-start)/2;
            addTask addTask1 = new addTask(start, middle);
            addTask addTask2 = new addTask(middle, end);
            addTask1.fork();
            addTask2.fork();

            return Long.valueOf(addTask1.join()+addTask2.join());
        }
    }

    public static void main(String[] args) throws IOException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        addTask task = new addTask(0,nums.length);
        forkJoinPool.execute(task);
        long result = task.join();
        System.out.println(result);
        //System.in.read();
    }
}
