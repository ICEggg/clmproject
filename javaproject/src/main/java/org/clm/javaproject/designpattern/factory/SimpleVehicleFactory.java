package org.clm.javaproject.designpattern.factory;

/**
 * 简单工厂模式，可扩展性不好，当要再添加一个交通工具的时候，
 * 就要修改这个工厂的代码
 *
 *
 */
public class SimpleVehicleFactory {
    public Car createCar(){
        //before processing
        return new Car();
    }

    public Plane createPlane(){
        return new Plane();
    }

}
