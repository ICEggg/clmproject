package javaproject.mytest.factorytest;

public class Circle extends Sharp{

    @Override
    public void getSharp() {
        System.out.println("this is "+ color.getColor() +" circle");
    }
}
