package org.clm.javaproject.algorithm;

import java.util.Random;

/**
 * 快速排序
 *
 * 思想：一个数组，随意选一个数当做轴，然后比轴大的放右边，比轴小的放左边
 *
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] array ={3,7,9,2,1,10,6,5,8,4};

        sort(array,0,array.length-1);
        print(array);
        //partition(array,0,array.length-1);


    }

    public static void sort(int[] array,int left,int right){
        if (left>=right) return;

        int mid = partition(array, left, right);
        //左边排序
        sort(array,left,mid-1);
        //右边排序
        sort(array,mid+1,right);


    }

    public static int partition(int[] array,int leftBound,int rightBound){
        int pivot = array[rightBound];

        int left=leftBound;
        int right=rightBound-1;

        while(left<=right){
            while(left<=right && array[left]<=pivot) left++;
            while(left<=right && array[right]>pivot) right--;

            if(left<right) swap(array,left,right);
        }
        swap(array,left,rightBound);
        //print(array);
        //把轴的位置返回
        return left;




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
