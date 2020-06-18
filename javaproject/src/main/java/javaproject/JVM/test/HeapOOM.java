package javaproject.JVM.test;

import java.util.ArrayList;
import java.util.List;

/**
 * 内存溢出
 * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 * -X:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=D://oom//HeapOOM
 */
public class HeapOOM {
    static class OOMObject{

    }

    public static void main(String[] args) {
        
        List<OOMObject> list = new ArrayList<>();
        while(true){
            list.add(new OOMObject());
        }
    }
}
