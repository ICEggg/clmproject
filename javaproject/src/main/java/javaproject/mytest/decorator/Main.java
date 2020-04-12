package javaproject.mytest.decorator;

public class Main {
    public static void main(String[] args) {
        AbstractDrink drink = new BlackCoffee();
        drink = new ZhengZhu(drink);
        drink = new ZhengZhu(drink);
        System.out.println(drink.cost());
    }
}
