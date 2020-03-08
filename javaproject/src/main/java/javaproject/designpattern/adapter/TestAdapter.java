package javaproject.designpattern.adapter;

/**
 * 适配器模式
 * 怎么才可以在目标接口中的 request() 调用 Adaptee 的 adapteeRequest() 方法呢？
 */
public class TestAdapter {

    public static void main(String[] args) {
        Target target = new ConcreteTarget();
        target.request();

        Target adapterTarget = new Adapter();
        adapterTarget.request();
    }

    static class Adaptee {
        public void adapteeRequest() {
            //System.out.println("被适配者的方法");
            System.out.println("别的国家的插座");
        }
    }

    static interface Target {
        void request();
    }

    static class ConcreteTarget implements Target {
        @Override
        public void request() {
            //System.out.println("concreteTarget目标方法");
            System.out.println("我的手机充电器，通过适配器，能调用");
        }
    }

    //类适配器
    static class Adapter extends Adaptee implements Target{
        @Override
        public void request() {
            //...一些操作...
            super.adapteeRequest();
            //...一些操作...
        }
    }

    //对象适配器
    /*public class Adapter implements Target{
        // 适配者是对象适配器的一个属性
        private Adaptee adaptee = new Adaptee();

        @Override
        public void request() {
            //...
            adaptee.adapteeRequest();
            //...
        }
    }*/


}
