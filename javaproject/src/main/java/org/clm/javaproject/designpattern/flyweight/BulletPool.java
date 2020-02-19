package org.clm.javaproject.designpattern.flyweight;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 享元模式（重复利用对象）
 * 比如一个word文档，有ABCD..Z这些字母，如果每敲一个字母就生成一个对象的话，那太伤了。
 * 所以，每敲一个字母的时候，就从一个池子里面拿。共享对象
 *
 * 下面这个例子，是坦克的子弹，当坦克发射子弹的时候，从池子里拿子弹，
 * 一个池子里的子弹都是可用的：living=true，当子弹飞出屏幕后，living=false，
 * 飞出屏幕的子弹，变成没用的子弹后，就可以重新再拿来复用了
 *
 * 比如a,b分别是两个对象，bbaba根据享元模式，理论上应该也是从池子里拿出a和b组合成bbaba，
 * 这个可以结合composite组合模式，比如a和b是bbaba的子集，还没试验过。。。
 */
class Bullet{
    public UUID id = UUID.randomUUID();
    boolean living = true;

    @Override
    public String toString() {
        return "Bullet{" +
                "id=" + id +
                ", living=" + living +
                '}';
    }
}

public class BulletPool {
    List<Bullet> bullets = new ArrayList<>();
    {
        for (int i = 0; i < 5; i++) {
            bullets.add(new Bullet());
        }
    }

    //如果有已经飞出屏幕，变成失效的子弹，就返回这个失效的子弹，如果没有，则新生成一个子弹
    public Bullet getBullet(){
        for (int i = 0; i <bullets.size() ; i++) {
            Bullet bullet = bullets.get(i);
            if(!bullet.living){
                return bullet;
            }
        }
        return new Bullet();
    }

    public static void main(String[] args) {
        BulletPool pool = new BulletPool();
        for (int i = 0; i < 10; i++) {
            Bullet bullet = pool.getBullet();
            System.out.println(bullet);
        }
    }
}
