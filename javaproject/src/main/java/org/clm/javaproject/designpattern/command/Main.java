package org.clm.javaproject.designpattern.command;

/**
 * 可以结合责任链
 *     比如要发生很多的回退
 */
public class Main {
    public static void main(String[] args) {
        Command insert = new InsertCommand();
        insert.doit();
        insert.undo();

        Command del = new InsertCommand();
        del.doit();
        del.undo();
    }
}
