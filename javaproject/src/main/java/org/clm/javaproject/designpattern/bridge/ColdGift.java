package org.clm.javaproject.designpattern.bridge;

public class ColdGift extends Gift{
    public ColdGift(GiftImpl impl) {
        this.impl = impl;
    }
}
