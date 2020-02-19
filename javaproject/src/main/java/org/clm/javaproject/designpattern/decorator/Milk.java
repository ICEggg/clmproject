package org.clm.javaproject.designpattern.decorator;

/**
 * 牛奶调味品
 */
public class Milk extends Decorator {
    public String description="牛奶";
    double mycost = 10; //牛奶调味品10块
    public Beverage beverage;

    public Milk(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public double getPrice() {
        return mycost+beverage.getPrice();
    }

    @Override
    public String getDescription() {
        return beverage.getDescription()+"---"+description;
    }
}
