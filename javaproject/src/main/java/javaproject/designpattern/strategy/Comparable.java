package javaproject.designpattern.strategy;

/**
 * 自定义Comparable是为了讲解泛型
 */
public interface Comparable<T> {
    //如果要比较多种类型的值，参数只能是object
    int compareTo(T t);
}
