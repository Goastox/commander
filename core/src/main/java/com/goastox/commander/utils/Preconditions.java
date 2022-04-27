package com.goastox.commander.utils;

import com.goastox.commander.exception.ApplicationException;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class Preconditions {
    private Preconditions() {
    }

    public static void checkArgument(boolean expression){
        if(!expression){
            throw new ApplicationException(ApplicationException.Code.INVALID_INPUT, String.valueOf("INVALID_INPUT"));
        }
    }

    public static void checkArgument(boolean expression, @Nullable Object errorMessage){
        if(!expression){
            throw new ApplicationException(ApplicationException.Code.INVALID_INPUT, String.valueOf(errorMessage));
        }
    }
    public static void checkWaring(boolean expression, @Nullable Object errorMessage){
        if(!expression){
            throw new ApplicationException(ApplicationException.Code.WARNING_INPUT, String.valueOf(errorMessage));
        }
    }


}

