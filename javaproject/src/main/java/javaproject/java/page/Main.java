package javaproject.java.page;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i < 52; i++) {
            list.add(i);
        }

        fenye(list, 10);

    }

    public static  void fenye(List list,int pagesize) {

        int totalcount = list.size();
        int pagecount = 0;
        int m = totalcount % pagesize;
        if (m > 0) {
            pagecount = totalcount / pagesize + 1;
        } else {
            pagecount = totalcount / pagesize;
        }

        for (int i = 1; i <= pagecount; i++) {
            if (m == 0) {
                List<Integer> subList = list.subList((i - 1) * pagesize, pagesize * (i));
                System.out.println(subList);
            } else {
                if (i == pagecount) {
                    List<Integer> subList = list.subList((i - 1) * pagesize, totalcount);
                    System.out.println(subList);
                } else {
                    List<Integer> subList = list.subList((i - 1) * pagesize, pagesize * (i));
                    System.out.println(subList);
                }


            }
        }
    }






}
