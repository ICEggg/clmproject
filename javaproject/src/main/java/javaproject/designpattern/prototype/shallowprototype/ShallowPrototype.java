package javaproject.designpattern.prototype.shallowprototype;

/**
 * 浅克隆
 */
public class ShallowPrototype {
    public static void main(String[] args) throws CloneNotSupportedException {
        Person p1 = new Person();
        Person p2 = (Person)p1.clone();
        System.out.println(p2.age+"   "+p2.score);
        System.out.println(p2.loc);

        System.out.println(p1.loc == p2.loc);
        /**
         * 浅克隆
         * 这时，两个person是两块地址，但是location指向的是同一个地址。
         *         所以p1的loc变成sh了，p2的loc也变成上海了。
         *  但是，既然克隆了，两个loc就不应该指向同一个loc
         *  所以这个叫：浅克隆
         */
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
        //这是浅克隆
        return super.clone();
    }
}

class Location {
    String address;
    int doorbnum;

    public Location(String address, int doorbnum) {
        this.address = address;
        this.doorbnum = doorbnum;
    }

    @Override
    public String toString() {
        return "Location{" +
                "address='" + address + '\'' +
                ", doorbnum=" + doorbnum +
                '}';
    }
}
