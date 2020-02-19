package org.clm.javaproject.designpattern.command;

public class DelCommand extends Command {
    Content c;
    String deleted;

    public DelCommand(Content c) {
        this.c = c;
    }

    @Override
    public void doit() {
        c.msg = c.msg.substring(0,5);
        c.msg = c.msg.substring(5,c.msg.length());
    }

    @Override
    public void undo() {
        c.msg = deleted+c.msg;
    }
}
