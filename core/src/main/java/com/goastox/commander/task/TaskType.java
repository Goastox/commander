package com.goastox.commander.task;

public enum TaskType {

    SUB_WORKFLOW(true),
    HTTP(true),
    LAMBDA(true),
    WAIT(true),


    SIMPLE(true),
    DYNAMIC(true),
    FORK_JOIN(true),
    FORK_JOIN_DYNAMIC(true),
    DECISION(true),
    JOIN(true),
    DO_WHILE(true),
    EVENT(true),
    USER_DEFINED(false),


    EXCLUSIVE_JOIN(true),
    TERMINATE(true),
    KAFKA_PUBLISH(true),
    JSON_JQ_TRANSFORM(true),
    SET_VARIABLE(true);

    private boolean isSystemTask;

    TaskType(boolean isSystemTask) {
        this.isSystemTask = isSystemTask;
    }
}
