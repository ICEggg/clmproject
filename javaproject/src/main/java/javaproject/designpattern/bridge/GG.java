package javaproject.designpattern.bridge;


public class GG {
    public void chase(MM mm){
        Gift g = new WarmGift(new Flower());

    }

    public void give(MM nn,Gift gift){
        System.out.println(gift+"gived!");
    }
}
