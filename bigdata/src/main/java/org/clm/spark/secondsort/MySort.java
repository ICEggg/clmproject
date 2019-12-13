package org.clm.spark.secondsort;

import scala.math.Ordered;

import java.io.Serializable;

public class MySort implements Serializable,Ordered<MySort> {
    private int first;
    private int second;

    public MySort(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    @Override
    public int compare(MySort other) {
        if(this.first - other.first != 0){
            return this.first - other.getFirst();
        }else{
            return -(this.second - other.getSecond());
        }
    }

    @Override
    public int compareTo( MySort other) {
        if(this.first - other.first != 0){
            return this.first - other.getFirst();
        }else{
            return -(this.second - other.getSecond());
        }
    }

    @Override
    public boolean $less( MySort other) {
        return !this.$greater$eq(other);
    }

    @Override
    public boolean $greater(MySort other) {
        if(this.first > other.getFirst()){
            return true;
        }else if(this.first == other.getFirst() && this.second < other.getSecond()){
            return true;
        }
        return false;
    }

    @Override
    public boolean $less$eq(MySort other) {
        return !this.$greater(other);
    }

    @Override
    public boolean $greater$eq( MySort other) {
        if(this.$greater(other)){
            return true;
        }else if(this.first == other.getFirst() && this.second == other.getSecond()){
            return true;
        }
        return false;
    }

}
