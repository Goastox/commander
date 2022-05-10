package com.goastox.commander.common.template;

import com.goastox.commander.common.TaskType;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public class TaskTemplate {

    private Integer token;

    private String referenceName;
    private String description;

    private TaskType type;
    private int[] next;
    private Map<String, Object> inputParameters = new HashMap<>();

    //分支参数
    private String caseParam;
    private String caseExpression;
    private String scriptExpression;
    private Map<String, Integer> decisionCases = new HashMap<>();//可选项

    // 根据不同类型的task 灵活的拓展字段

    private String condition;

    private SubWorkflowParams subWorkflowParams;

    //重试次数
    private Integer retryCount;

}
