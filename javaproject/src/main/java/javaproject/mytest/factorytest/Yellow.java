package javaproject.mytest.factorytest;

public class Yellow implements Color{

    @Override
    public String getColor() {
        System.out.println("this is yellow");
        return "yellow";
    }
}
