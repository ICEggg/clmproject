package javaproject.mydp.factory;

import java.util.Calendar;

public abstract class Pizza {
    String name;

    abstract void prepare();

    public void bake(){
        System.out.println("pizza bake");
    }
    public void cut(){
        System.out.println("pizza cut");
    }
    public void box(){
        System.out.println("pizza box");
    }
}

class Main{
    public static void main(String[] args) {
        Pizza pepperpizza = new PepperPizza();
        Pizza cheesspizza = new CheessPizza();
        Order order = new Order(cheesspizza);
        order.orderPizza();
    }
}

class PepperPizza extends Pizza{
    public PepperPizza(){name = "Pepper";}
    @Override
    void prepare() {
        System.out.println("prepare for pepperpizza");
    }
}

class CheessPizza extends Pizza{
    public CheessPizza(){name = "Cheess";}
    @Override
    void prepare() {
        System.out.println("prepare for cheesspizza");
    }
}

 class Order{
    Pizza pizza;
    public Order(Pizza pizza){this.pizza = pizza;}
    public void orderPizza(){
        System.out.println("this is "+pizza.name);
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

    }
}





