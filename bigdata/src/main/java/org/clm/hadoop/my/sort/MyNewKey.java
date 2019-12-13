package org.clm.hadoop.my.sort;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class MyNewKey implements WritableComparable<MyNewKey> {
    Long firstnum;
    Long secondnum;
    public MyNewKey(){

    }

    public MyNewKey(Long firstnum, Long secondnum) {
        this.firstnum = firstnum;
        this.secondnum = secondnum;
    }

    @Override
    public int compareTo(MyNewKey mynewkey) {
        int min = firstnum.compareTo(mynewkey.firstnum);
        if(min!=0){
            return min;
        }else{
            return secondnum.compareTo(mynewkey.secondnum);
        }
    }


    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(firstnum);
        out.writeLong(secondnum);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        firstnum = in.readLong();
        secondnum = in.readLong();
    }

    public Long getFirstnum() {
        return firstnum;
    }

    public void setFirstnum(Long firstnum) {
        this.firstnum = firstnum;
    }

    public Long getSecondnum() {
        return secondnum;
    }

    public void setSecondnum(Long secondnum) {
        this.secondnum = secondnum;
    }
}
