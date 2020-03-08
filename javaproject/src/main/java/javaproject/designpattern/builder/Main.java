package javaproject.designpattern.builder;


public class Main {
    public static void main(String[] args) {
        TerrainBuilder t = new ComplexTerrainBuilder();
        //构建器可以通过链式构造
        Terrain ter = t.buildFort().buildMine().buildWall().build();


        /**
         * 这是平常代码里使用的方式，比如一个人有很多种属性，就可以用这种方式
         * 优点是：有时只要构造两个参数，有时构造四个参数，如果每次构造都写一个构造函数那就会写太多了，
         *          构建器可以帮助，只选择需要的参数进行构建，而不需要再去写一个一个的构造函数了
         */
        Person p = new Person.PersonBuilder()
                .baseInfo(1, "aaa", 10)
               // .weight(100)
               // .score(60)
                .loc("街道", "601")
                .build();
    }
}
