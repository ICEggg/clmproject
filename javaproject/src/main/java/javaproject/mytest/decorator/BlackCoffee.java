package javaproject.mytest.decorator;

public class BlackCoffee extends AbstractDrink {

    public BlackCoffee() {
        price = 10;
        des = "blackcoffee";
    }

    @Override
    int cost() {
        System.out.println(des+": "+price);
        return price;
    }

}
