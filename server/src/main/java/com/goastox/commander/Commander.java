package com.goastox.commander;

import com.alibaba.fastjson.JSON;
import com.goastox.commander.annotation.EnableTaskExecutor;
import com.goastox.commander.utils.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@EnableTaskExecutor
@SpringBootApplication
public class Commander {

    public static void main(String[] args) {
        SpringApplication.run(Commander.class);
    }
}
