package javaproject.designpattern.strategy;

public class Cat{
    int weight,height;
    public Cat(){ }
    public Cat(int weight,int height){
        this.weight = weight;
        this.height = height;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "weight=" + weight +
                ", height=" + height +
                '}';
    }


}
