package com.goastox.commander.execution;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class ExecutionTask {

    // 执行task调用逻辑


    @Bean
    public ThreadPoolExecutor executor(){
        ThreadFactory name = new ThreadFactoryBuilder().setNameFormat("simple-thread-%d").build();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,
                5,
                0L,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(5),
                name, new ThreadPoolExecutor.AbortPolicy());

        return threadPoolExecutor;
    }
}
