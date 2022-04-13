package com.goastox.commander.utils;

import java.util.HashMap;

public class Test {

    public static void main(String[] args) {
        HashMap<Integer, int[]> map = new HashMap<>();

        map.put(0, new int[]{1, 2, 3});
        map.put(1, new int[]{4});
        map.put(2, new int[]{5});
        map.put(3, new int[]{5});
        map.put(4, new int[]{6, 7});
        map.put(5, new int[]{8});
        map.put(6, new int[]{1,10});
        map.put(7, new int[]{10});
        map.put(8, new int[]{9, 11});
        map.put(9, new int[]{11});
        map.put(10, new int[]{11, 12});
        map.put(11, null);
        map.put(12, null);

    }

}
