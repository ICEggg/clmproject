package javaproject.mytest.decorator;

public class Decorator extends AbstractDrink{
    String des = "这是装饰者";
    public AbstractDrink drink;

    public Decorator(AbstractDrink drink){
        this.drink = drink;
    }

    @Override
    int cost() {
        return this.price+ drink.cost();
    }

    @Override
    public String toString() {
        return this.des + "--" + drink.des;
    }
}
