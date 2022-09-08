package com.goastox.commander.monitor;

public interface Recorder<T> {

    // 记录者，记录全局所以信息

    void record(T value);

}
