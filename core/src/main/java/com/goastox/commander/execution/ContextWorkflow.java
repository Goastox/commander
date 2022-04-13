package com.goastox.commander.execution;

import com.goastox.commander.common.entity.Workflow;
import com.goastox.commander.common.template.TaskTemplate;
import com.goastox.commander.common.template.WorkflowTemplate;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Data
public class ContextWorkflow {

    private Workflow workflow;

    private WorkflowTemplate workflowTemplate;

    private Map<String, Object> contextInput;
    private Map<String, Object> contextOutput;

    //图
    private Map<Integer, AtomicLong> painter;

    private Map<Integer, TaskTemplate> tasks;

}
