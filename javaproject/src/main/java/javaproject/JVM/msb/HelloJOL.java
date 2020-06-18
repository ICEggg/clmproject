package javaproject.JVM.msb;

import org.openjdk.jol.info.ClassLayout;

/**
 * jol一个工具 jol-core 0.9
 * java object layout ：java对象布局
 *
 *java.lang.Object object internals:
 *      OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
 * 1      0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
 * 2      4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
 * 3      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
 * 4     12     4        (loss due to the next object alignment)
 *
 *  1,2是对象头，3是类型指针压缩后4字节，4是对齐填充
 *
 *
 * 如果加上参数，-XX:-UseCompressedClassPointers，减号 意思是去掉压缩类型指针
 * java.lang.Object object internals:
 *      OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
 * 1      0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
 * 2      4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
 * 3      8     4        (object header)                           00 1c ab 19 (00000000 00011100 10101011 00011001) (430644224)
 * 4     12     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
 *
 *  1,2,3,4都是对象头，没有对齐填充了
 */
public class HelloJOL{
    public static void main(String[] args) {
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        /**
         * 对象头包含三个主要信息：hashcode，对象gc分代信息，锁的信息
         *
         * 下面代码的目的是：为了验证对象头中有锁的信息，执行后会看到markword上锁和不上锁的差别
         */
        synchronized (o){
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }
}
