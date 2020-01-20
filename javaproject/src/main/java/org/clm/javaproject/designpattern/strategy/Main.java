package org.clm.javaproject.designpattern.strategy;

import java.util.Arrays;

/**
 * comparator 就是策略模式的实现
 */
public class Main {
    /**
     * 这个sort方法，public static void sort(int[] arr)是对int类型的进行排序，
     * 如果要对double或者float等排序，要怎么做？？
     * 最傻的办法就是再写一个double的方法
     *
     * 如果要对猫，狗，这类的对象中的值，比如身高 ，体重，进行排序，又该怎么办？
     * 1.比如可以规定，sort方法接收的参数是：实现了comparable<>的类，
     *  然后，sort方法就可以改成public static void sort(Comparable[] arr)
     *  这样，所有实现了comparable接口的类，都可以作为参数传进去了，然后就可以实现自己的compareTo方法
     *  现在，猫类里，实现compareTo方法，比较的是猫的weight，如果要比较height又怎么办呢？
     *
     *  这里引入策略模式，可以传入策略，进行比较    Comparator<T>
     *   这是sort传入的参数是：public void sort(T[] arr,Comparator<T> comparator)
     *   传入要比较的数组，和实现了Comparator的比较器。
     *   这里传入的是猫的体重比较器，比较的是猫的weight
     *   如果要比较猫的height，就在写一个类实现Comparator，比较猫的height，
     *   这样就可在不改变原来代码的基础上，添加新的功能，只要实现新的比较器就行了
     */

    public static void main(String[] args) {
        //int[] a = {9,2,3,5,7,1,4};
        Cat[] a = {
                new Cat(20,30),
                new Cat(10,80),
                new Cat(30,40)
        };
        Sorter<Cat> sorter = new Sorter();
        sorter.sort(a,new CatHeightComparator());
        System.out.println(Arrays.toString(a));
    }
}
