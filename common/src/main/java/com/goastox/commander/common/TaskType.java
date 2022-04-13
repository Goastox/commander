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
}
