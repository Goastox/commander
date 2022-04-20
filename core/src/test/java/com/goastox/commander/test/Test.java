package com.goastox.commander.test;


import com.goastox.commander.task.WorkflowTask;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Test {

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("-?[0-9]+(\\.[0-9]+)?");
        Matcher matcher = pattern.matcher("-32rerf");
        System.out.println(matcher.matches());
//        TimeUnit.MILLISECONDS.convert()

    }

}
