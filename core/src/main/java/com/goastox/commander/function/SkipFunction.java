package com.goastox.commander.function;

import com.goastox.commander.function.Function;

import java.io.Serializable;

@FunctionalInterface
public interface SkipFunction<T> extends Function, Serializable {

    boolean skip(T value) throws Exception;
}
