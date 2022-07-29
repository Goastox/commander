package com.goastox.commander.test;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class Test {
    public static void main(String[] args) {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        System.out.println(runtime.getName());
    }
}
