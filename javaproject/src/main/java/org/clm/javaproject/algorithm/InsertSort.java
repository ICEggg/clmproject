package org.clm.javaproject.algorithm;

/**
 * 插入排序
 * 像打牌一样，从小到大排，拿到一张就放到合适的位置
 *
 * 比如1,5,3  1和5比：不动；然后3和5比，换位置；然后 3和1比，不动
 */
public class InsertSort {
    public static void main(String[] args) {
        int[] array ={7,3,9,2,10,4,6,5,8,1};

        for (int j = 1; j <array.length ; j++) {
            for (int i = j; i >0; i--) {
                if(array[i] < array[i-1]){
                    swap(array,i,i-1);
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
