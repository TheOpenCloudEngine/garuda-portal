package org.uengine.bss.monetization.util;

/**
 * Created by swsong on 2015. 1. 9..
 */
public class MutableInteger {
    private int value;
    public MutableInteger(){}
    public MutableInteger(int value) {
        this.value = value;
    }
    public void increment() {
        value++;
    }
    public void decrement() {
        value--;
    }
    public void set(int value) {
        this.value = value;
    }
    public int intValue() {
        return value;
    }
}
