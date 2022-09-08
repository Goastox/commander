package com.goastox.commander.function.demo;

import com.goastox.commander.function.Collector;
import com.goastox.commander.function.Container;
import com.goastox.commander.function.MapFunction;
import org.springframework.util.StringValueResolver;

import javax.annotation.Resource;

public class FeatureFunction implements MapFunction {

    @Resource
    private StringValueResolver stringValueResolver;

    @Override
    public void map(Container container, Collector collect) {





    }
}
