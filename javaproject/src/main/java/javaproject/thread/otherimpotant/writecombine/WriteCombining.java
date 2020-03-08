package javaproject.thread.otherimpotant.writecombine;

/**
 * cpu的合并写 技术
 * 理论上：
 * cpu 内存有一个非常深的一个buffer，除了寄存器，就这个buffer离cpu最近了
 * 比如：更新了一个字节的数据，数据要发到cpu缓存，在发到主存，一次一个字节 太慢了
 *  而这个buffer的作用是：这个buffer只有四个字节，当四个字节满了，再发送
 *  这样，比一个字节一个字节要快得多
 *
 *  下面的代码逻辑是这样，但是跑出来的结果好像不是这样的。。。。
 *  有时间，查一下
 */
public class WriteCombining {
    private static final int ITERATION = Integer.MAX_VALUE;
    private static final int ITEMS = 1<<24;
    private static final int MASK = ITEMS -1;

    private static final byte[]  arrayA = new byte[ITEMS];
    private static final byte[]  arrayB = new byte[ITEMS];
    private static final byte[]  arrayC = new byte[ITEMS];
    private static final byte[]  arrayD = new byte[ITEMS];
    private static final byte[]  arrayE = new byte[ITEMS];
    private static final byte[]  arrayF = new byte[ITEMS];

    public static void main(String[] args) {
        for (int i = 0; i <= 3; i++) {
            System.out.println(i+" SingleLoop duration (ns) "+runCaseOne());
            System.out.println(i+" SplitLoop duration (ns)  "+runCaseTwo());
        }
    }

    public static long runCaseOne(){
        long start = System.nanoTime();
        int i = ITERATION;
        while (--i != 0){
            int slot = i & MASK;
            byte b = (byte) i;
            arrayA[slot] = b;
            arrayB[slot] = b;
            arrayC[slot] = b;
            arrayD[slot] = b;
            arrayE[slot] = b;
            arrayF[slot] = b;
        }
        return  System.nanoTime() - start;
    }

    private static long runCaseTwo() {
        long start = System.nanoTime();
        int i = ITERATION;
        while (--i != 0){
            int slot = i & MASK;
            byte b = (byte) i;
            arrayA[slot] = b;
            arrayB[slot] = b;
            arrayC[slot] = b;
        }
        i = ITERATION;
        while (--i != 0){
            int slot = i & MASK;
            byte b = (byte) i;
            arrayD[slot] = b;
            arrayE[slot] = b;
            arrayF[slot] = b;
        }
        return  System.nanoTime() - start;
    }


}
