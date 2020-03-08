package javaproject.designpattern.command;

public abstract class Command {
    public abstract void doit();    // 执行
    public abstract void undo();    //回退
}
