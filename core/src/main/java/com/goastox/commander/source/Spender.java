package com.goastox.commander.source;


import java.io.Serializable;
import java.util.Map;

public interface Spender<T> extends Serializable {

    // 拦截入参 处理， 替换 占位符

    T deserialize(String param) throws Exception;

}
