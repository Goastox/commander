package com.goastox.commander.function;

public interface Collector<T> {
    // 返参收集器

    void collect(T record);

}
