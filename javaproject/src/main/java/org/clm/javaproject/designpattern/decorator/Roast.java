package org.clm.javaproject.designpattern.decorator;

/**
 * 烘烤过的咖啡
 */
public class Roast implements Beverage {
    public String description="";
    public Roast() {
        description = "这是烘烤过的咖啡";
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getPrice() {
        return 22;
    }

}


