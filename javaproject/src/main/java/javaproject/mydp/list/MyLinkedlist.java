package javaproject.mydp.list;

public class MyLinkedlist<E> implements Collection<E>,Iterator<E>{
    Node head = null;
    Node tail = null;
    private int size = 0;

    @Override
    public void add(E e) {
        Node n = new Node(e);
        if(head == null){
            head = n;
            tail = n;
        }

        tail.next = n;
        tail = n;
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        return (Iterator<E>) new MyArraylist<E>();
    }

    @Override
    public boolean hasNext() {
        int currentindex = 0;
        if(currentindex <size) return true;
        return false;
    }

    @Override
    public E next() {
        int currentindex = 0;

        return null;
    }


    class Node{
        private E e ;
        private Node next;

        public Node(E e) {
            this.e = e;
        }
    }
}
