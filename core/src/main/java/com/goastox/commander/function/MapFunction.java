package com.goastox.commander.function;

@FunctionalInterface
public interface MapFunction<T, O> extends Function {

    // task 实现接口

    void map(Container<T> container, Collector<O> collect);

}
