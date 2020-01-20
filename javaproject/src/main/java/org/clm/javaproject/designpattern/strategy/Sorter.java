package org.clm.javaproject.designpattern.strategy;

public class Sorter<T> {

    //这是策略模式，
    public void sort(T[] arr,Comparator<T> comparator){
        for (int i = 0; i < arr.length; i++) {
            int minPos = i;
            for (int j = i+1; j < arr.length; j++) {
                minPos = comparator.compare(arr[j],arr[minPos])<0  ? j : minPos;
            }
            swap(arr,i,minPos);
        }
    }

    void swap(T arr[],int i ,int j){
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    //冒泡排序，为了演示策略模式,这个不是策略模式，参数是Comparable
    /*public static void sort(Comparable[] arr){
        for (int i = 0; i < arr.length; i++) {
            int minPos = i;
            for (int j = i+1; j < arr.length; j++) {
                minPos = arr[j].compareTo(arr[minPos])<0 ? j : minPos;
            }
            swap(arr,i,minPos);
        }
    }

    static void swap(Comparable arr[],int i ,int j){
        Comparable temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }*/



}
