package javaproject.designpattern.iterator;


/**
 * 这是链表的实现
 */
public class LinkedList_<E>  implements Collection_<E> {
    Node head = null;
    Node tail = null;
    //目前容器中有多少个元素
    private int size = 0;

    @Override
    public void add(E o){
        Node n = new Node(o);
        n.next = null;
        if (head == null) {
            head = n;
            tail = n;
        }

        tail.next = n;
        tail = n;
        size++;
    }
    public int size(){
        return size;
    }

    private class Node{
        private E o;   //  数据
        Node next;  //指向下一个节点的指针

        public Node(E o) {
            this.o = o;
        }
    }

    @Override
    public Iterator_ iterator() {
        return null;
    }

}



