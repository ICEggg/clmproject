package javaproject.mydp;

import java.lang.reflect.Proxy;

public class MyProxy{

    public static void main(String[] args) {
        Moveable proxy = new TimeProxy(new LogProxy(new Tank()));
        proxy.move();
    }

    static class Tank implements Moveable{
        public Tank() {
        }

        @Override
        public void move() {
            System.out.println("tank go go");
        }
    }

    interface Moveable{
        void move();
    }

    static class TimeProxy implements Moveable{
        Moveable moveable;

        public TimeProxy(Moveable moveable) {
            this.moveable = moveable;
        }

        @Override
        public void move() {
            System.out.println("time start");
            moveable.move();
            System.out.println("time end");
        }
    }

    static class LogProxy implements Moveable{
        Moveable moveable;

        public LogProxy(Moveable moveable) {
            this.moveable = moveable;
        }

        @Override
        public void move() {
            System.out.println("log start");
            moveable.move();
            System.out.println("log end");
        }
    }



}
