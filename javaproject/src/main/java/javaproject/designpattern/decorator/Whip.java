package javaproject.designpattern.decorator;


/**
 * 奶油调味品
 */
public class Whip extends Decorator {
    public String description="奶油";
    double mycost = 8; //奶油调味品10块

    public Whip(Beverage beverage) {
        super(beverage);
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
