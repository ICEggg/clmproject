package javaproject.interviewquestion;

/**
 * 重写object的finalize方法
 * 当垃圾回收的时候，finalize方法会被调用，一般不推荐使用，
 * 它不知道什么时候会被调用，也不一定会被调用
 * 这就是为了演示而已
 *
 */
public class M {
    @Override
    protected void finalize() throws Throwable {
        System.out.println("my definded finalize");
    }
}
