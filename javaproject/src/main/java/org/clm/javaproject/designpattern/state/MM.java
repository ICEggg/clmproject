package org.clm.javaproject.designpattern.state;

public class MM {
    String name;
    MMState state;

    public MM(String name, MMState state) {
        this.name = name;
        this.state = state;
    }

    public void smile(){
        state.smile();
    }
    public void cry(){
        state.cry();
    }
    public void say(){
        state.say();
    }

}
