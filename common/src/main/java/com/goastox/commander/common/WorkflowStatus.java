package com.goastox.commander.common;

public enum WorkflowStatus {
    /**
     * 运行中
     */
    RUNNING(false, false),
    /**
     * 已完成
     */
    COMPLETED(true, true),
    /**
     * 失败
     */
    FAILED(true, false),
    /**
     * 超时
     */
    TIMED_OUT(true, false),
    /**
     * 结束
     */
    TERMINATED(true, false),
    /**
     * 暂停
     */
    PAUSED(false, true);



    private final boolean terminal;

    private final boolean successful;



    WorkflowStatus(boolean terminal, boolean successful) {
        this.terminal = terminal;
        this.successful = successful;
    }
}
