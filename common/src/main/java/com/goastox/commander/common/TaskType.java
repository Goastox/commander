package com.goastox.commander.common;

public enum TaskType {


    START_TASK(true),
    END_TASK(true),

    DECISION(true),
    DO_WHILE(true),


    SUB_WORKFLOW(false),
    WAIT(false),
    HTTP(false),
    LAMBDA(false),
    SIMPLE(false),
    DYNAMIC(false),
    FORK_JOIN(false),
    FORK_JOIN_DYNAMIC(false),
    JOIN(false),
    EVENT(false),
    USER_DEFINED(false);

    private boolean isSystemTask;

    TaskType(boolean isSystemTask) {
        this.isSystemTask = isSystemTask;
    }

    // TODO 考虑是否支持其他语言，如果是解释型考虑是否进行预编译
}
