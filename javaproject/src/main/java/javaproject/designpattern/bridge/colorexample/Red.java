package javaproject.designpattern.bridge.colorexample;

public class Red implements Color {

    @Override
    public String getColor() {
        System.out.println("this is red");
        return "red";
    }
}
