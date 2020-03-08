package javaproject.mydp.list;

public class Main {
    public static void main(String[] args) {
        Collection<String> array = new MyArraylist<>();
        array.add("a");
        array.add("b");
        array.add("c");
        System.out.println(array.size());

        Iterator<String> iterator = array.iterator();
        boolean falg = iterator.hasNext();
        System.out.println(falg);
        while(falg){
             String a = iterator.next();
             System.out.println(a);
         };

    }
}
