package com.goastox.commander;

import com.goastox.commander.annotation.EnableTaskExecutor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableTaskExecutor
@SpringBootApplication
public class Commander {


    public static void main(String[] args) {
        SpringApplication.run(Commander.class);
    }

}
