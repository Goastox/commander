package com.goastox.commander.utils;

import com.goastox.commander.exception.ApplicationException;
import com.goastox.commander.exception.ApplicationException.Code;


public final class PreNumberConditions<T extends Number> {

    private PreNumberConditions(T value) {
        this.value = value;
    }

    private final T value;

    public static <T extends Number> PreNumberConditions<T> of(T value){
        return new PreNumberConditions<T>(value);
    }

    public PreNumberConditions orElseZero(){
        if(value == null){
            return new PreNumberConditions(0);
        }
        return this;
    }

    public boolean isPresent(){
        return value != null;
    }

    public PreNumberConditions max(T maxVar){
        if(this.value.longValue() > maxVar.longValue()){
            throw new ApplicationException(Code.INVALID_INPUT,
                    String.format("The parameter exceeds the maximum value:{%s}", maxVar.longValue()));
        }
        return this;
    }

    public PreNumberConditions min(T minVar){
        if(this.value.longValue() < minVar.longValue()){
            throw new ApplicationException(Code.INVALID_INPUT,
                    String.format("The parameter exceeds the minimum value:{%s}", minVar.longValue()));
        }
        return this;
    }

}
