package javaproject.mytest.factorytest;

public class Main {
    public static void main(String[] args) {
        Sharp circle = new Circle();
        circle.setColor(new Red());
        circle.getSharp();

        Sharp circle2 = new Circle();
        circle2.setColor(new Yellow());
        circle2.getSharp();

    }
}

