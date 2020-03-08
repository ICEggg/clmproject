package javaproject.java.algorithm;

/**
 * 选择排序
 *
 * 最简单但是最没用的排序算法，因为最不稳定，基本用不上
 * 思路：一个数组5个数，找到最小的位置，然后在做交换，和排在第一个的数调换。然后再循环，找第二小的，和第二个数换位子，以此类推
 * 改进：1.每次循环找到最小值的时候，也可以同时，把最大值也找到
 *       2.每次比较是前一个值和后一个值比较，可不可以前一个值和后面两个（三个等），其中最小的一个比较？
 * 不稳定：比如一个数组有两个相等的数字，当多次比较的时候，这两个的顺序是不固定的，会变化，所以不稳定
 */
public class SelectSort {
    public static void main(String[] args) {
        int[] array ={3,7,9,2,10,4,6,5,8,1};

        //定义minPos是第几个，0,1,2,3,4。。。
        for (int j = 0; j <array.length-1 ; j++) {
            int minPos = j;
            for (int i = minPos+1; i < array.length; i++) {
                minPos = array[minPos] < array[i] ? minPos : i;
            }
            swap(array,minPos,j);
        }
        print(array);
    }

    /**
     * 两数交换方法，
     * @param array
     * @param minPos
     * @param i
     */
    public static void swap(int[] array,int minPos,int i){
        int temp = array[minPos];
        array[minPos] = array[i];
        array[i] = temp;
    }

    public static void print(int[] array){
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]+"  ");
        }
    }
}
