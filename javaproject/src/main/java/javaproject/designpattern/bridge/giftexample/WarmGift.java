package javaproject.designpattern.bridge.giftexample;


public class WarmGift extends Gift {
    public WarmGift(GiftImpl impl) {
        this.impl = impl;
    }
}
