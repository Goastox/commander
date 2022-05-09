package com.goastox.commander.common.entity;

import com.goastox.commander.common.TaskType;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Task {

    private Integer token;
    private String taskId;
    private TaskType type;

    private String referenceTaskName;

    private Map<String, Object> inputData = new HashMap<>();
    private Map<String, Object> outputData = new HashMap<>();

    private String condition;
    private int retryCount;

    private String subWorkflowId;


}
