package javaproject.mydp.list;

public interface Collection<E> {
    void add(E e);
    int size();

    Iterator<E> iterator();
}
