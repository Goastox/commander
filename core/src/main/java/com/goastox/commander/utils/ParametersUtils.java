package com.goastox.commander.utils;

import com.google.common.collect.Maps;
import org.springframework.util.PropertyPlaceholderHelper;

import java.util.Map;
import java.util.Properties;

public class ParametersUtils {


    private static final PropertyPlaceholderHelper helper
            = new PropertyPlaceholderHelper("${", "}");

    public static Map<String, String> getTaskInput(Map<String, String> map, Properties context){
        Map<String, String> out = Maps.newHashMapWithExpectedSize(map.size());
        map.forEach( (k,v) -> out.put(k, helper.replacePlaceholders(v, context)));
        return out;
    }

}
