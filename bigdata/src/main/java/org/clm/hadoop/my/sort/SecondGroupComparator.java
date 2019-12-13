package org.clm.hadoop.my.sort;


import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.io.WritableComparator;

public class SecondGroupComparator implements RawComparator<MyNewKey> {
    @Override
    public int compare(MyNewKey o1, MyNewKey o2) {
        return o1.getFirstnum().compareTo(o2.getFirstnum());
    }

    @Override
    public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
        return WritableComparator.compareBytes(b1, s1, 8, b2, s2, 8);
    }
    /*
     * 字节比较
     * arg0,arg3为要比较的两个字节数组
     * arg1,arg2表示第一个字节数组要进行比较的收尾位置，arg4,arg5表示第二个
     * 从第一个字节比到组合key中second的前一个字节，因为second为int型，所以长度为4
     */
    /*@Override
    public int compare(byte[] arg1, int arg2, int arg3, byte[] arg4, int arg5, int arg6) {
        return WritableComparator.compareBytes(arg1,0,arg2-4,arg4,0,arg5-4);
    }*/


}
