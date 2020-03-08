package javaproject.designpattern.single;

/**
 * 懒汉式，即用到的时候，才实例化，双重判断也保证了线程安全
 * 但是实际使用上，没有必要这样写，用第一种饿汉式就够了
 *
 * 完美的写法之一
 *
 */
public class Mgr02 {
    //加volatile是为了指令重排序
    private static volatile Mgr02 INSTANCE;

    private Mgr02(){ }

    public static Mgr02 getInstance(){
        if(INSTANCE == null){
            synchronized (Mgr02.class){
                if(INSTANCE == null){
                    //这个sleep是模拟执行时间
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    /**
                     * new一个对象是有一个过程的，比如从a到b，这段路走完了，才new出来一个对象
                     * 步骤是：（比如对象M m，里面有个int i = 5；）
                     * 1：先分配内存空间
                     * 2：刚开始初始化，m = null，i=0；
                     * 3: 初始过程initial
                     * 4：把初始化好了的对象，给到m
                     *
                     * 当一个线程T1，拿到锁进来走到这一步，开始new
                     * 这个时候另外一个线程T2过来，走到第一个INSTANCE == null进行判断
                     * 这时，因为cpu指令重排，原本1-2-3-4的顺序，可能变成了1-2-4-3，
                     * 就是先把m=null，i=0给赋值了，在进行3，执行了i=5
                     * 此时，执行了3后，正好有个T2过来了，T2判断，这个对象已经不为空了，就直接return了
                     * 但是，其实这个时候对象还没有初始化完成。
                     *
                     * volatile 让这个顺序一直是1234
                     *
                     * 极大的并发量下，才可能出现的问题
                     * 这个太吹毛求疵了，扩展知识面
                     */
                    INSTANCE = new Mgr02();
                }
            }
        }
        return INSTANCE;
    }

    public static void main(String[] args) {
        Mgr02 m1 = Mgr02.getInstance();
        Mgr02 m2 = Mgr02.getInstance();
        System.out.println(m1==m2);
    }
}
