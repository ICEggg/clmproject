package javaproject.mytest.decorator;

public class WhiteCoffee extends AbstractDrink {
    public WhiteCoffee() {
        des = "whitecoffee";
        price = 20;
    }

    @Override
    int cost() {
        System.out.println(des+": "+price);
        return price;
    }
}
