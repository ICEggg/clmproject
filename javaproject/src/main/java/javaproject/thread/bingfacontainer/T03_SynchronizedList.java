package javaproject.thread.bingfacontainer;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Collections.synchronizedmap(map)/synchronizedlist(list) 可以把不带锁的map/list变成带锁的（并发量不高的时候）
 */
public class T03_SynchronizedList {
    public static void main(String[] args) {
        List<String> strs = new ArrayList<>();  //没有加锁的list
        List<String> strSnyc = Collections.synchronizedList(strs);  //变成了加锁的list
    }

}
