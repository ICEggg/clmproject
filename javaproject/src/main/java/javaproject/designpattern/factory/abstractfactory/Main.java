package javaproject.designpattern.factory.abstractfactory;

public class Main {
    public static void main(String[] args) {
        AbstractFactory f = new ModernFactory();

        Vehicle car = f.createVehicle();
        car.go();
        Weapon ak = f.createWeapon();
        ak.shoot();
        Food bread = f.createFood();
        bread.printName();
    }
}
