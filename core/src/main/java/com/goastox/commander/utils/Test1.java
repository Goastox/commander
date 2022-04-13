package com.goastox.commander.utils;

import java.util.concurrent.atomic.AtomicLong;

public class Test1 {
    public static void main(String[] args) {
//                   101 0000 0001 0010 0011 0100
//        1111 1111 1011 1111 1111 1111 1111 1111
//        System.out.println(Integer.toBinaryString(41));
        AtomicLong atomicLong=new AtomicLong(3);
        System.out.println(atomicLong.getAndSet(223));
        System.out.println(atomicLong.compareAndSet(3, 5));
        System.out.println(atomicLong.get());



    }

}
