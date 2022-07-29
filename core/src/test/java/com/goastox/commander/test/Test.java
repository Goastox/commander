package com.goastox.commander.test;


import com.alibaba.fastjson.JSON;
import com.github.dadiyang.equator.FieldInfo;
import com.github.dadiyang.equator.GetterBaseEquator;
import com.goastox.commander.common.Auditable;
import com.goastox.commander.common.entity.Workflow;
import com.goastox.commander.task.WorkflowTask;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
        GetterBaseEquator getterBaseEquator = new GetterBaseEquator();
        Workflow workflow = new Workflow();
        workflow.setWorkflowId("test1");

        Workflow workflow2 = new Workflow();
        workflow2.setWorkflowId("test2");



        List<FieldInfo> diffFields = getterBaseEquator.getDiffFields(workflow, workflow2);
        System.out.println(JSON.toJSONString(diffFields));

    }

}
