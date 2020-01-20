package org.clm.javaproject.fanxing;

import java.util.Random;

public class FruitGenerator implements Generic<String>{
    private String[] fruits = new String[]{"Apple", "Banana","Pear"};

    @Override
    public String next() {
        Random rand = new Random();
        return fruits[rand.nextInt(3)];
    }

}
