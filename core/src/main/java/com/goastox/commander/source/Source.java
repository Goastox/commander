package com.goastox.commander.source;

import java.io.Serializable;

public interface Source<T> extends Serializable {
    //出入数据
    T getType();
}
