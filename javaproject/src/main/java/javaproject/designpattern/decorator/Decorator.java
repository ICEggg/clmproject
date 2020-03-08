package javaproject.designpattern.decorator;


/**
 * 定义装饰者类
 */
public abstract class Decorator implements Beverage {
    public String description="我只是装饰器，不知道具体的描述";

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getPrice() {
        return 0;
    }
}
