package javaproject.mytest.decorator;

public class ZhengZhu extends Decorator{

    public ZhengZhu(AbstractDrink drink) {
        super(drink);
        price = 1;
    }

    @Override
    public int cost() {
        return super.cost();
    }
}
