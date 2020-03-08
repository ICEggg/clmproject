package javaproject.java.fanxing;


public class Mytest {
    public static void main(String[] args) {
        /*List<String> stringArrayList = new ArrayList<String>();
        List<Integer> integerArrayList = new ArrayList<Integer>();

        Class classStringArrayList = stringArrayList.getClass();
        Class classIntegerArrayList = integerArrayList.getClass();

        if(classStringArrayList.equals(classIntegerArrayList)){
            System.out.print("泛型测试"+"类型相同");

        }*/

        FruitGenerator f = new FruitGenerator();
        System.out.println(f.next());

    }
}
