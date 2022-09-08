package com.goastox.commander.annotation;

import com.goastox.commander.config.ThreadsProperties;
import com.goastox.commander.constant.ThreadsConstant;
import com.goastox.commander.execution.TaskThreadPoolExecutor;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;

@Slf4j
public class TaskExecutorSelector implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Environment environment;
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override                               //当前类的注解信息                          注册类
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        ThreadsProperties properties = new ThreadsProperties();
        Binder binder = Binder.get(environment);
        Bindable<?> target = Bindable.of(ResolvableType.forClass(ThreadsProperties.class)).withExistingValue(properties);
        binder.bind("spring.threads", target);
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(TaskThreadPoolExecutor.class);

//        int corePoolSize,
//        int maximumPoolSize,
//        long keepAliveTime,
//        TimeUnit unit,
//        BlockingQueue<Runnable> workQueue,
//        ThreadFactory threadFactory,
//        RejectedExecutionHandler handler

        ThreadsProperties.ThreadPoolProperties http = properties.getHttp();


        Arrays.asList(properties.getHttp(),
                properties.getCommon(),
                properties.getEager(),
                properties.getRpc()).stream().
                filter(Objects::nonNull)
                .forEach(x-> {


                    builder.addConstructorArgValue(x.getCorePoolSize());
                    builder.addConstructorArgValue(x.getMaximumPoolSize());
                    builder.addConstructorArgValue(x.getKeepAliveTime());
                    builder.addConstructorArgValue(x.getUnit());

                    builder.addConstructorArgValue(new ArrayBlockingQueue<>(5));

                    builder.addConstructorArgValue(new ThreadFactoryBuilder().setNameFormat(x.getName()).build());

                    builder.addConstructorArgValue(x.getHandler().handle());

                    registry.registerBeanDefinition(x.getName(), builder.getBeanDefinition());
                });

    }
}
