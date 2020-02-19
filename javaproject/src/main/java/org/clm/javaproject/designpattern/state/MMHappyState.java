package org.clm.javaproject.designpattern.state;

public class MMHappyState extends MMState {
    @Override
    void smile() {
        System.out.println("开心的笑");
    }

    @Override
    void cry() {
        System.out.println("开心的哭");
    }

    @Override
    void say() {
        System.out.println("开心的说");
    }
}
