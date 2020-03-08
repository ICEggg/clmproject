package javaproject.thread.otherimpotant.cacheline;

/**
 * 内部的原理：new出来的两个T对象（a,b）在内存中各自一块空间。new了一个数组，分别指向这两块内存。
 * 当两个线程（T1,T2）执行的时候，T1调用了a.x,T2调用了b.x，x是long类型，8字节。
 * 当T1执行时，由于a.x是8字节，不够一个cacheline（64字节），所以会把b.x也放到这个缓存行里。同理T2也是这样。
 * 当两个线程都执行时，两个线程分别都有一个cacheline（c1，c2），里面是a.x和b.x
 * 因为volatile的可见性，当T1里的a.x改变的时候，T2的cacheline里的a.x也要改变，
 * 所以会造成T1改一下，T2也得跟着改一下，浪费了很多时间。
 */
public class T01_CacheLinePadding {
    private static class T{
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
