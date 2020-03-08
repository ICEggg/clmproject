package javaproject.designpattern.command;


public class InsertCommand extends Command {
    Content c;
    String strToInsert="ccccccccccc";

    @Override
    public void doit() {
        c.msg += strToInsert;
    }

    @Override
    public void undo() {
        //相当于ctrl+z的作用
        c.msg = c.msg.substring(0,c.msg.length()-strToInsert.length());
    }
}
