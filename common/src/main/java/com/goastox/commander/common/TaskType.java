package com.goastox.commander.common;

public enum TaskType {


    START_TASK(true),
    END_TASK(true),

    DECISION(true),

    // TODO 是否要接入数据源，如果入参是所有规则数据太浪费资源，如果接入数据源怎么优化（提前加载临阶层级的规则数据）
    DECISION_PLUS(true),

    DO_WHILE(true),

    REDIS(true),


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

    // TODO 考虑不同任务走不同的线程池，或者是相关处理

    // TODO 考虑是否支持其他语言，如果是解释型考虑是否进行预编译

    // 天生支持并行

    // TODO 需要包装一下RPC调用，减少后边自定义开发

    // TODO 发送消息

    // TODO 发送邮件

    // 空跑 分流 AB试验

    // TODO 选择注册中心 配置中心

    // 异步日志落库

    //监控、链路追踪

    //TODO 共有问题就是 无效节点无法定位，无法回收，大量占用资源
}
