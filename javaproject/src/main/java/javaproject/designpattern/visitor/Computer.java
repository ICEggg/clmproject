package javaproject.designpattern.visitor;

import java.sql.Driver;

/**
 * 访问者模式，比如电脑配件，有cpu，内存，等。
 * 对不同的用户有不同的打折方式，比如学生，cpu打1折，内存打2折,主板打三折，对白领等其他职业打不同的折扣
 * 普通的想法是：在不同的配件里，根据用户的类型，做if else判断。
 * 但是这样，如果又加了一个用户，那每个配件里面都要修改代码，这样不好。
 *
 * 所以引出访问者模式，在每个配件里，传入Visitor，调用visitor的对应方法，
 * 这样还有用户来的时候，就不需要修改原来的代码了。
 *
 * 问：如果电脑配件增加了，那不还是要修改原来的代码么？？？
 * 遇到这种情况，就不适合用访问者模式了
 */
public class Computer {
    ComputerPart cpu = new CPU();
    ComputerPart memory = new Memory();
    ComputerPart board = new Board();

    public void accept(Visitor v){
        cpu.accept(v);
        memory.accept(v);
        board.accept(v);
    }

    public static void main(String[] args) {
        PersonalVisitor p = new PersonalVisitor();
        Computer computer = new Computer();
        computer.accept(p);
        System.out.println(p.totalPrice);
    }
}

abstract class ComputerPart{
    abstract void accept(Visitor v);
    abstract double getPrice();
}

class CPU extends ComputerPart{
    @Override
    void accept(Visitor v) {
        v.visitCpu(this);
    }
    @Override
    double getPrice() {
        return 100;
    }
}
class Memory extends ComputerPart{
    @Override
    void accept(Visitor v) {
        v.visitMemory(this);
    }
    @Override
    double getPrice() {
        return 200;
    }
}
class Board extends ComputerPart{
    @Override
    void accept(Visitor v) {
        v.visitBoard(this);
    }
    @Override
    double getPrice() {
        return 300;
    }
}

interface Visitor{
    void visitCpu(CPU cpu);
    void visitMemory(Memory memory);
    void visitBoard(Board board);
}

class PersonalVisitor implements Visitor{
    double totalPrice = 0.0;

    @Override
    public void visitCpu(CPU cpu) {
        totalPrice += cpu.getPrice()*0.9;
    }

    @Override
    public void visitMemory(Memory memory) {
        totalPrice += memory.getPrice()*0.9;
    }

    @Override
    public void visitBoard(Board board) {
        totalPrice += board.getPrice()*0.9;
    }
}

class CorpVisitor implements Visitor{
    double totalPrice = 0.0;

    @Override
    public void visitCpu(CPU cpu) {
        totalPrice += cpu.getPrice()*0.8;
    }

    @Override
    public void visitMemory(Memory memory) {
        totalPrice += memory.getPrice()*0.8;
    }

    @Override
    public void visitBoard(Board board) {
        totalPrice += board.getPrice()*0.8;
    }
}




















