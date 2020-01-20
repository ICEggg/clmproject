package org.clm.javaproject.designpattern.strategy;

//策略模式
public interface Comparator<T> {
    int compare(T t1,T t2);
}
