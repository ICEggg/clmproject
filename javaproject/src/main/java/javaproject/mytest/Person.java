package javaproject.mytest;

public abstract class Person {
    int a = 10;

    public void output(){
        System.out.println(this.a);
    }
}

class xiaoming2 extends Person{
    public void output(){
        a = 11;
        super.output();
        //System.out.println(getA());
    }
}

class xiaoming extends Person{
    public void output(){
        a = 12;
        super.output();
        //System.out.println(getA());
    }

    public static void main(String[] args) {
        Person p = new xiaoming();


        Person p1 = new xiaoming2();
        p.output();
        p1.output();
    }
}
