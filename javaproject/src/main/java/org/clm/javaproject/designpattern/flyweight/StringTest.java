package org.clm.javaproject.designpattern.flyweight;

public class StringTest {
    public static void main(String[] args) {
        String s1 = "abc";
        String s2 = "abc";
        String s3 = new String("abc");
        String s4 = new String("abc");

        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s3 == s4);
        //intern这个方法的意思是：new出来的这个对象，会出常量池中找，是否有这个值得常亮，若有，就指向这个常量，所以是true
        System.out.println(s3.intern() == s1);
        System.out.println(s3.intern() == s4.intern());
    }
}
