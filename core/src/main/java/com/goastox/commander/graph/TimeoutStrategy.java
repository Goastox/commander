package com.goastox.commander.graph;

public interface TimeoutStrategy<T> {

    // 全局超时策略
    static <T> TimeoutStrategy<T> noTimeout(){
        return new NoTimoutGenerator();
    }
}
