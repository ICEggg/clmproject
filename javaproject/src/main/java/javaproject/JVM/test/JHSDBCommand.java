package javaproject.JVM.test;

/**
 * JHSDB 命令 （没成功）
 */
public class JHSDBCommand {
    private static class ObjectHolder{

    }

    static class Test{
        static ObjectHolder staticObj = new ObjectHolder();
        ObjectHolder instanceObj = new ObjectHolder();
        void foo(){
            ObjectHolder localObj = new ObjectHolder();
            System.out.println("done");
        }
    }

    public static void main(String[] args) {
        Test test = new JHSDBCommand.Test();
        test.foo();
    }
}
