package com.goastox.commander.test;


import com.alibaba.fastjson.JSON;
import com.github.dadiyang.equator.FieldInfo;
import com.github.dadiyang.equator.GetterBaseEquator;
import com.goastox.commander.common.Auditable;
import com.goastox.commander.common.entity.Workflow;
import com.goastox.commander.task.WorkflowTask;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Test {


//    aviator
    public static void main(String[] args) {

        String str = "2983-1-CU-AC转账-预交易-新银>=2/同盾行卡-H";
        Pattern pattern2 = Pattern.compile("(?<=-).*(?=-)");
        Matcher matcher2 = pattern2.matcher(str);
        if (matcher2.find()){
            matcher2.group();
        }



        // 获取CPU核数
        System.out.println(Runtime.getRuntime().availableProcessors());


        GetterBaseEquator getterBaseEquator = new GetterBaseEquator();
        Workflow workflow = new Workflow();
        workflow.setWorkflowId("test1");
        Workflow workflow2 = new Workflow();
        workflow2.setWorkflowId("test2");
        List<FieldInfo> diffFields = getterBaseEquator.getDiffFields(workflow, workflow2);
        System.out.println(JSON.toJSONString(diffFields));

    }


    private static long convertTimeToLong(String time){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(time, dateTimeFormatter).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

}
