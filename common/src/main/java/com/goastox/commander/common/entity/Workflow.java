package com.goastox.commander.common.entity;

import com.goastox.commander.common.Auditable;
import com.goastox.commander.common.WorkflowStatus;
import com.goastox.commander.common.template.WorkflowTemplate;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Data
public class Workflow extends Auditable {

    private String workflowId;

    private String parentWorkflowId;

    private String parentWorkflowTaskId;

    private Map<String, Object> input = new HashMap<>();
    private Map<String, Object> output = new HashMap<>();
    private Map<String, Integer> tasks = new HashMap<>();

    private String workflowType;
    private Integer version;
    private long endTime;
    private WorkflowStatus status;

    private WorkflowTemplate template;

    private Map<Integer, AtomicLong> graph;
    private Integer startToken;



}
