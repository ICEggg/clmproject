package javaproject.designpattern.proxy;

import java.util.Random;

/**
 * 在cglib里，被代理类，目前看来要是public才可以代理成功，否则会报错
 */
public class Tank {
        public void move() {
            System.out.println("tank moving claclacla.......");
            try {
                Thread.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
}
