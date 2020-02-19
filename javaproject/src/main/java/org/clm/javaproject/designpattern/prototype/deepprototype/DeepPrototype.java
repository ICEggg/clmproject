package org.clm.javaproject.designpattern.prototype.deepprototype;

/**
 * 深克隆
 * 对象类型需要进行深克隆，
 * string类型不需要进行深克隆
 */
public class DeepPrototype {
    public static void main(String[] args) throws CloneNotSupportedException {
        Person p1 = new Person();
        Person p2 = (Person)p1.clone();
        System.out.println(p2.age+"   "+p2.score);
        System.out.println(p2.loc);

        System.out.println(p1.loc == p2.loc);
        p1.loc.address = "sh" ;
        System.out.println(p2.loc);
    }
}

//一定要实现Cloneable接口
class Person implements Cloneable{
    int  age =8;
    int score=100;
    Location loc = new Location("bj",22);

    @Override
    public Object clone() throws CloneNotSupportedException {
        //这是深克隆
        Person p = (Person)super.clone();
        p.loc = (Location) loc.clone();
        return p;
    }
}

/**
 * 要实现cloneable接口
 */
class Location implements Cloneable{
    String address;
    int doorbnum;

    public Location(String address, int doorbnum) {
        this.address = address;
        this.doorbnum = doorbnum;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Location{" +
                "address='" + address + '\'' +
                ", doorbnum=" + doorbnum +
                '}';
    }
}
