package javaproject.designpattern.decorator;


public class Main {
    public static void main(String[] args) {
        //不加调料的HouseBlend
        Beverage beverage = new HouseBlend();
        System.out.println(beverage.getDescription()+"     $"+beverage.getPrice());

        //加入调料的HouseBlend（前两种最好，后两种帮助理解）
        Beverage beverage2 = new HouseBlend();
        beverage2 = new Milk(beverage2);
        beverage2 = new Whip(beverage2);
        System.out.println(beverage2.getDescription()+"    $"+beverage2.getPrice());

        /*Beverage beverage2 = new Whip(new Milk(new HouseBlend()));
        System.out.println(beverage2.getDescription()+"    $"+beverage2.getPrice());*/

        /*Beverage beverage2 = new HouseBlend();
        Beverage decorator = new Milk(beverage2);
        Beverage decorator2 = new Whip(decorator);
        System.out.println(decorator2.getDescription()+"    $"+decorator2.getPrice());*/

    }
}
