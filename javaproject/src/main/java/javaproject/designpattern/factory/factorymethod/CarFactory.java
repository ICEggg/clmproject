package javaproject.designpattern.factory.factorymethod;

public class CarFactory {
    public Moveable create(){
        return new Car();
    }
}
