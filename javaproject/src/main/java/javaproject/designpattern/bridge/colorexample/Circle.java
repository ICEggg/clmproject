package javaproject.designpattern.bridge.colorexample;

public class Circle extends Sharp {

    @Override
    public void getSharp() {
        System.out.println("this is "+ color.getColor() +" circle");
    }
}
