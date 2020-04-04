package javaproject.designpattern.bridge.giftexample;


public class ColdGift extends Gift {
    public ColdGift(GiftImpl impl) {
        this.impl = impl;
    }
}
