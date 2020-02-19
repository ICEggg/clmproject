package org.clm.javaproject.designpattern.iterator;

public class Main {
    public static void main(String[] args) {
        //如果用户要遍历数组或者链表的话，比如hashset等其他结构，应该在自己的类中实现遍历的方法
        Collection_ list = new ArrayList_();
        for (int i = 0; i < 15; i++) {
            list.add(new Integer(i));
        }
        System.out.println(list.size());

        /**
         * 遍历数组,这种遍历方式不通用，像ArrayList_和Linkedlist可以都实现一个get方法，
         * 但是如果想Hashset等各种其他容器的话，没有get方法的话，那这个遍历方法就不通用了
         * 所以最好，每个容器实现自己的遍历
         */
//        ArrayList_ newlist = (ArrayList_)list;
//        for (int i = 0; i < newlist.size(); i++) {
//
//        }

        //通用的方法如下
        Iterator_ iterator = list.iterator();
        while (iterator.hasNext()){
            Object o = iterator.next();
            System.out.println(o);
        }


    }

}
