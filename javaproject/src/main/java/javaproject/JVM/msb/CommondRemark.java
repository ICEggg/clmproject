package javaproject.JVM.msb;

/**
 * 指令重排 案例
 *
 *
 * 理论上应该会出现 x = 0,y=0的情况，来证明指令重排是存在的
 * 但是没有执行出来。。。。不知道为什么，可能和机器有关
 *
 * 如果没有指令重拍的话，应该只可能有三种结果 (1,0)(0,1)(1,1),
 * 而绝对不会有(0,0)这种情况
 *
 * 引出 volatile 可以阻止指令重拍
 *
 */
public class CommondRemark {
    static int a,b;
    static int x,y;

    public static void main(String[] args) throws InterruptedException {
        a = 0;b = 0;
        x = 0;y = 0;
        int i = 0;
        for (;;) {
            i++;
            Thread one = new Thread(() -> {
                a = 1;
                x = b;
            });

            Thread two = new Thread(() -> {
                b = 1;
                y = a;
            });
            one.start();
            two.start();

            one.join();
            two.join();


            if(x == 0 && y == 0){
                System.out.println("第" + i + "次：" + "(" + x + "," + y +")");
                break;
            }

        }

    }
}
