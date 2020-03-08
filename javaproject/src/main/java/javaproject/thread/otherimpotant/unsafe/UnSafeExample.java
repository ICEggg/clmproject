package javaproject.thread.otherimpotant.unsafe;

import sun.misc.Unsafe;
import java.lang.reflect.Field;

/**
 * CAS compare and swap(compare and exchange) 自旋/自旋锁/无锁
 *
 * unsafe
 * unsafe里一个方法 compareAndSwapInt（object var1,long var2,int var4,int var5）
 * 这个方法就是自旋锁。
 *
 * AtomicInteger的incrementAndGet方法用到了unsafe
 *
 */
public class UnSafeExample {
    int i = 0;
    private static UnSafeExample t = new UnSafeExample();
    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);

        //获取UnSafeExample声名的变量i
        Field f = UnSafeExample.class.getDeclaredField("i");
        //获取i在内存中的offset
        long offset = unsafe.objectFieldOffset(f);
        System.out.println(offset);

        /**
         * 参数含义：
         * 1：从这个对象  2：某个变量在内存中的下标（现在这个变量是i）
         * 3：期望i，现在的值是，0    4：希望它变成1
         * 如果i当前的值不是零，这就行代码就一直循环，只有i是0了，i才会变成1
         *
         * 这个操作是原子性的
         */
        boolean success = unsafe.compareAndSwapInt(t,offset,0,1);
        System.out.println(success);
        System.out.println(t.i);


    }
}
