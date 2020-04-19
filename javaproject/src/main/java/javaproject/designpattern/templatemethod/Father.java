package javaproject.designpattern.templatemethod;

public abstract class Father {

    public void way(){
        before();
        System.out.println("this is father");
        after();
    }

    public abstract void before();

    public abstract void after();

}
