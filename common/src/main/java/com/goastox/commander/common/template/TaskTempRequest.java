package com.goastox.commander.common.template;

import com.goastox.commander.common.TaskType;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public class TaskTempRequest {

    private Integer token;

    private String referenceName;

    private String description;

    private TaskType type;

    //下游节点
    private int[] next;

    private Map<String, String> inputTemplate = new HashMap<>();

    // 执行条件 fasle 跳过，可接受全局入参或 task 返参
    private String condition;

    // 子流程
    private SubWorkflowParams subWorkflowParams;

    //重试次数
    private Integer retryCount;

    // 失败处理流程 或 节点

}
