package javaproject.java.algorithm;

/**
 * 希尔排序
 *
 * 工程上几乎不用，面试也少考
 */
public class ShellSort {

    public static void main(String[] args) {
        int[] array ={9,6,11,3,5,12,8,7,10,15,14,4,1,13,2};

        //理论上最好的间隔gap的计算方式：Knuth序列，是：h=1,h=3*h+1
        int h = 1;
        while(h >array.length/3){
            h = h*3+1;
        }

        //Knuth序列，h大于数组长度的三分之一就不适合了
        for (int gap = h; gap >0; gap =(gap-1)/3) {
        //最常用的方式：每次间隔都缩小两倍
        //for (int gap = array.length/2; gap >0; gap /= 2) {
            //这个下面就是插入排序，原本间隔是1，只是现在间隔变为了认为定义的
            for (int i = gap; i < array.length; i++) {
                for (int j = i; j >gap-1; j-=gap) {
                    if(array[j]<array[j-gap]){
                        swap(array,j,j-gap);
                    }
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
