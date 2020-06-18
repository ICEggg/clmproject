package javaproject.JVM;

/**
 * 用jmp pid 命令 查看堆内存的情况
 * 查看过程：
 *  1、打开命令行窗口
 *  2、jps 查看JMapExample进程号
 *  3、分别在打印1,2,3后，执行jmap -heap pid
 *  4、可以看到，打印1的时候，eden区内存占用是7m，打印2后，变成了17m，打印3后，因为垃圾回收变成1m
 */
public class JMapExample {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("1.....");
        Thread.sleep(60000);
        byte[] array = new byte[1024*1024*10];//10m
        System.out.println("2.....");
        Thread.sleep(30000);
        array = null;
        System.gc();
        System.out.println("3......");
        Thread.sleep(1000000l);


    }
}
