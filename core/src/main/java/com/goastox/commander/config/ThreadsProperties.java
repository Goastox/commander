package com.goastox.commander.config;

import com.goastox.commander.constant.RejectedExecutionUnit;
import com.goastox.commander.constant.ThreadsConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

@ConfigurationProperties(
        prefix = "spring.threads"
)
@Data
public class ThreadsProperties {

    // 设计思路，所有的任务会根据类型来区分线程池，但也不是绝对的，当其他线程池队列较满对分配到其他线程池
    // 设计不同的分发器

    private ThreadPoolProperties http;
    private ThreadPoolProperties rpc;
    private ThreadPoolProperties common;
    // 适用于IO密集型线程池，调优策略 队列到达峰值可以吧任务丢给其他线程池
    private ThreadPoolProperties eager;

    @Data
    public static class ThreadPoolProperties{

        private String name;
        private int corePoolSize;
        private int maximumPoolSize;
        private long keepAliveTime;
        private TimeUnit unit;
        private String workQueue;
        private RejectedExecutionUnit handler; // 拒绝策略


//        private boolean allowCoreThreadTimeOut;//是否运行核心线程池超时
//        private long runTimeout;// 任务执行超时阈值 单位（ms）
//        private long queueTimeout;//任务在队列等待超时阈值，单位（ms）
    }

}
