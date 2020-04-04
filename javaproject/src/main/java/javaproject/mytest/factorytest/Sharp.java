package javaproject.mytest.factorytest;

public abstract class Sharp {
    Color color;

    public void setColor(Color color){
        this.color = color;
    }

    abstract void getSharp();
}
