package javaproject;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.Path;

import java.util.*;
import java.util.function.Supplier;

public class Test {
    public static void main(String[] args) {
        List<String> a = new ArrayList<>();
        a.add("1");
        a.add("2");

        List<String> b = new ArrayList<>();
        b.add("2");
        b.add("1");


        a.sort(Comparator.comparing(String::hashCode));
        b.sort(Comparator.comparing(String::hashCode));
        System.out.println(a.equals(b));


        Supplier<Double> pp = Math::random;
        System.out.println(pp.get());
    }

    public static int minus(){
        System.out.println("aa");
        return 0;
    }
}
