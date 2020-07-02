package javaproject.designpattern.iterator;

import java.util.ArrayList;

/**
 * 迭代器模式
 * 构建可以动态扩展的容器，数组，链表
 *
 * 这是数组的实现
 */
public class ArrayList_<E> implements Collection_<E> {
    E[] objects = (E[])new Object[10];
    //objects中下一个空的位置在哪，或者说，目前容器中有多少个元素
    private int index = 0;

    @Override
    public void add(E o){
        if(index == objects.length){
            E[] newObjects = (E[])new Object[objects.length*2];
            System.arraycopy(objects,0,newObjects,0,objects.length);
            objects = newObjects;
        }

        objects[index] = o;
        index++;
    }

    public int size(){
        return index;
    }

    public Iterator_ iterator(){
        return new ArraylistIterator();
    };

    //可以用内部类也可以不用内部类，ArrayList_直接实现Iterator_接口也是可以的
    class ArraylistIterator implements Iterator_<E> {
        private int currentIndex;
        @Override
        public boolean hasNext() {
            if(currentIndex >= index) return false;
            return true;
        }

        @Override
        public E next() {
            E o = objects[currentIndex];
            currentIndex++;
            return o;
        }
    }
}