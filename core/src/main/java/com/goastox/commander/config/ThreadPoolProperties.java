package com.goastox.commander.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "pool")
public class ThreadPoolProperties {

    // 设计思路，所有的任务会根据类型来区分线程池，但也不是绝对的，当其他线程池队列较满对分配到其他线程池
    // 设计不同的分发器

    private DynamicThreadPoolProperties http;
    private DynamicThreadPoolProperties rpc;
    private DynamicThreadPoolProperties common;
    // 适用于IO密集型线程池，调优策略 队列到达峰值可以吧任务丢给其他线程池
    private DynamicThreadPoolProperties eager;

    @Data
    @NoArgsConstructor
    public static class DynamicThreadPoolProperties{
        private String threadPoolName;
        private int corePoolSize;
        private int maximumPoolSize;
        private long keepAliveTime;
        private String unit;
        private String workQueue;
        private boolean allowCoreThreadTimeOut;//是否运行核心线程池超时
        private long runTimeout;// 任务执行超时阈值 单位（ms）
        private long queueTimeout;//任务在队列等待超时阈值，单位（ms）
        private String rejectedHandlerType; // 拒绝策略
    }

}
