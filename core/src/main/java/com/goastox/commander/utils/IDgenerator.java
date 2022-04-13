package com.goastox.commander.utils;

import java.util.UUID;

public class IDgenerator {
    private IDgenerator() {
    }

    public static String generator(){
        return UUID.randomUUID().toString();
    }
}
