package com.goastox.commander.utils;

import org.springframework.util.PropertyPlaceholderHelper;

import java.util.Properties;

public class ParametersUtils {


    private static final PropertyPlaceholderHelper helper
            = new PropertyPlaceholderHelper("${", "}",
            null, false);

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("uid", "212324234324");
        properties.setProperty("phone", "18888888888");

        String s = helper.replacePlaceholders("${uid}", properties);
        System.out.println(s);

    }
}
