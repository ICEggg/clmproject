package org.clm.javaproject.designpattern.state;

/**
 * 状态模式
 */
public class Main {
    public static void main(String[] args) {
        MMState state = new MMHappyState();
        MM mm = new MM("小红",state);
        mm.smile();
        mm.cry();
        mm.say();
    }
}
