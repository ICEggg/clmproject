package javaproject.mydp.list;


public class MyArraylist<E> implements Collection<E> {
    E[] array = (E[])new Object[10];
    private int index = 0;

    @Override
    public void add(E o) {
        if(index == array.length){
            int size = array.length*2;
            E[] newArray = (E[])new Object[size];
            System.arraycopy(array,0,newArray,0,array.length);
            array = newArray;
        }
        array[index] = o;
        index++;
    }

    @Override
    public int size() {
        return index;
    }

    public E get(int index){
        return array[index];
    }

    @Override
    public Iterator<E> iterator() {
        return new InnerArray();
    }

    class InnerArray implements Iterator<E>{
        private int currentIndex;
        public boolean hasNext(){
            if(currentIndex >= index) return false;
            return true;
        }

        public E next(){
            E o = array[currentIndex];
            currentIndex++;
            return o;
        }
    }


}
