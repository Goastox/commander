package com.goastox.decision.test2;

import java.math.BigDecimal;
import java.util.HashMap;

public class Test2 {
    public static void main2(String[] args) {
        System.out.println(Double.MAX_VALUE);

        BigDecimal bigDecimal = new BigDecimal("1.635637775333E10");
        System.out.println(bigDecimal.toString());


        String aa = "16356377753.33333";
        BigDecimal naN = new BigDecimal(aa).setScale(2, BigDecimal.ROUND_HALF_UP);
        System.out.println(naN.toString());

//        bitmapAndCardinality(CG_26_20220525, CG_35_20220525)
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        System.out.println(stringIntegerHashMap.get("") == null);

    }

    public static void main(String[] args) {
        for (;; main2(null)){

        }
    }
}
