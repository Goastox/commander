package com.goastox.commander.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(TaskExecutorSelector.class)
public @interface EnableTaskExecutor {
}
