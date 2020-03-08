package javaproject.thread.otherimpotant.cacheline;

/**
 * T02_CacheLinePadding
 * T类继承了Padding，所以new T()的时候，会把Padding的p1-p7都加载了。
 * 线程T1执行的时候，a.x和p1-p7正好64个字节，正好是一个cacheline 的长度。
 * 同理T2也是，b.x和p1-p7也正好是一个cacheline的长度。
 * 所以，a.x只在T1自己的cacheline里，当a.x变化的时候，就不用再通知其他线程了。
 * 同理T2也是。
 * 所以速度相比T01_CacheLinePadding就快了
 */
public class T02_CacheLinePadding {
    private static class Padding{
        public volatile long p1,p2,p3,p4,p5,p6,p7;
    }

    private static class T extends Padding{
        public volatile long x = 0L;
    }

    public static T[] arr = new T[2];

    static {
        arr[0] = new T();
        arr[1] = new T();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            for (int i = 0; i < 10000000L; i++) {
                arr[0].x = i;
            }
        });

        Thread t2 = new Thread(()->{
            for (int i = 0; i < 10000000L; i++) {
                arr[1].x = i;
            }
        });

        final long start = System.nanoTime();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println((System.nanoTime() - start) / 100_0000);


    }




}
