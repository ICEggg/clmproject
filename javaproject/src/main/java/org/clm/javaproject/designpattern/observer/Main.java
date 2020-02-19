package org.clm.javaproject.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 观察者模式
 * 在很多系统中，observer系统往往和责任链共同负责对于事件的处理，
 * 其中的某一个observer负责是否将事件进一步传递
 *
 * 当一个孩子醒了，爸，妈会有相对应的行为。爸妈就相当于观察者，孩子就是事件源
 *
 * 很多时候，观察者需要根据事件的具体情况来进行处理，大多数时候。
 * 我们处理事件的时候，需要事件源对象，事件也可以形成继承体系
 *
 * 在观察者模式中，事件类是必不可少的
 */
class Child {
    private boolean cry = false;
    private List<Observer> list = new ArrayList<>();

    {
        list.add(new Dad());
        list.add(new Mum());
    }

    public void wakeUp() {
        cry = true;

        wakeUpEvent event = new wakeUpEvent(System.currentTimeMillis(),"bed",this);

        for (Observer observer : list) {
            observer.actionOnWakeUp(event);
        }
    }

}

//观察者接口
interface Observer {
    void actionOnWakeUp(wakeUpEvent event);
}

class Dad implements Observer {
    public void hug(){
        System.out.println("爸爸去抱孩子");
    }

    @Override
    public void actionOnWakeUp(wakeUpEvent event) {
        //event.getSource();
        hug();
    }
}

class Mum implements Observer {
    public void feed(){
        System.out.println("妈妈去喂奶");
    }

    @Override
    public void actionOnWakeUp(wakeUpEvent event) {
        feed();
    }
}

//定义一个抽象事件，泛型可以传递各种事件源
abstract class Event<T> {
    abstract T getSource();
}

class wakeUpEvent extends Event<Child> {
    long timestamp;
    String loc;
    Child source;

    public wakeUpEvent(long timestamp, String loc, Child source) {
        this.timestamp = timestamp;
        this.loc = loc;
        this.source = source;
    }

    @Override
    Child getSource() {
        return source;
    }
}

class Main {
    public static void main(String[] args) {
        Child child = new Child();
        child.wakeUp();
    }
}


