package javaproject.designpattern.factory.abstractfactory;

public class ModernFactory extends AbstractFactory {

    @Override
    public Vehicle createVehicle() {
        return new Car();
    }

    @Override
    public Weapon createWeapon() {
        return new AK47();
    }

    @Override
    public Food createFood() {
        return new Bread();
    }
}
