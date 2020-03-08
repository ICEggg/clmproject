package javaproject.designpattern.factory.abstractfactory;

/**
 * 扩展产品一族
 * 比如：汽车 AK 面包
 *       飞机 沙鹰  白菜
 */
public abstract class AbstractFactory {
    public abstract Vehicle createVehicle();
    public abstract Weapon createWeapon();
    public abstract Food createFood();
}
