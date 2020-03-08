package javaproject.java.algorithm;

/**
 * 冒泡排序
 *
 * 相邻的两个之间比，索引依次递增
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] array ={3,7,9,2,10,4,6,5,8,1};

        for (int j = array.length; j >0 ; j--) {
            for (int i = 0; i < j-1; i++) {
                if(array[i]>array[i+1]){
                    swap(array,i,i+1);
                }
            }
        }

        print(array);
    }

    /**
     * 两数交换方法，
     * @param array
     */
    public static void swap(int[] array,int a,int b){
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    public static void print(int[] array){
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]+"  ");
        }
    }
}
