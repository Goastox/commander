package com.goastox.commander.utils;

import lombok.Data;

@Data
public class User {

    public User() {
        System.out.println("init user ----");
    }

    private String name;
    private String age;
}
