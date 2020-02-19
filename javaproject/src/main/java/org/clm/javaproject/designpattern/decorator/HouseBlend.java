package org.clm.javaproject.designpattern.decorator;

/**
 * 黑咖啡
 */
public class HouseBlend implements Beverage {
    public String description="";
    public HouseBlend() {
        description = "这是黑咖啡";
    }

    @Override
    public double getPrice() {
        return 15;
    }

    @Override
    public String getDescription() {
        return description;
    }


}
