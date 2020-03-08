package javaproject.java.algorithm;

/**
 * 归并排序
 * 重点：一个数组分成两半，然后对两边的数组在分成两半，分到最后，知道只有一个元素的时候
 *
 * 思想：首先要会，对两个已经排好序的数组A,B，进行排序。(代码在下面的merge方法)
 * A,B两个数组两两从头比较，比如A的小，就放到新数组里，索引往后移，B的大，索引不变，然后继续比较。
 *
 * *****实际应用中挺多的,因为稳定性，常用在对象排序中*****
 *
 * 扩展：TimSort,是归并排序的再加工版本，不需掌握。
 * 大概原理是：归并排序是一个数组第一次就分成两半，然后不断分，
 *              TimSort是一开始把一个数组分成多分。然后再各个部分里，进行归并排序
 */
public class MergeSort {
    public static void main(String[] args) {
        //int[] array ={1,4,6,7,10,2,3,5,8};
        int[] array ={3,7,9,2,10,4,6,5,8,1};
        sort(array,0,array.length-1);
        print(array);
        //merge(array,0,4,7);
    }

    public static void sort(int[] array,int left,int right){
        //当分到最后，每个分组只有一个元素的时候，就返回
        if(left == right) return;

        //这样写可能会有一个问题，left+right的值可能会大于int的最大值,所以用下面那个
        //int mid = (left+right)/2;
        int mid = left +(right-left)/2;

        //左半部分排序
        sort(array,left,mid);
        //右半部分排序
        sort(array,mid+1,right);

        merge(array,left,mid,right);
    }

    /**
     * 对两个已经排好序的数组A,B，进行排序
     * @param array 数组
     * @param left  左边界
     * @param middle 中间的那个数的索引
     * @param right 右边界
     *
     *  健壮性不够，开发时候要增加left，right的判断，这些边界的检查
     */
    public static void merge(int[] array,int left,int middle,int right){
        int mid = middle;

        int i = left;
        int j = mid+1;
        int k = 0;
        int[] temp = new int[right-left+1];

        while(i <= mid && j <= right){
            //这里用<就是不稳定的，用<=才是稳定的
            if(array[i] <= array[j]){
                temp[k++] = array[i++];
            }else{
                temp[k++] = array[j++];
            }
        }

        while(i <= mid) temp[k++] = array[i++];
        while(j <= right) temp[k++] = array[j++];

        //print(temp);
        for (int q = 0; q < temp.length; q++) {
            array[left+q] = temp[q];
        }
    }


    public static void print(int[] array){
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]+"  ");
        }
    }
}
