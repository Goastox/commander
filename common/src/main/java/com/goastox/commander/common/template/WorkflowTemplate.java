package com.goastox.commander.common.template;

import com.goastox.commander.common.Auditable;
import com.goastox.commander.common.TimeoutPolicy;
import lombok.Data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Data
public class WorkflowTemplate extends Auditable {

    private String name;
    private String description;
    private Integer version = 1;

    private Map<Integer, TaskTemplate> tasks = new HashMap();
    private List<String> inputParameters = new LinkedList<>();
    private Map<String, String> outputParameters = new HashMap<>();


    //当前文件结构版本
    private final Integer schemaVersion = 1;

    //工作流失败处理逻辑
    private String failureWorkflow;
    // 是否可以重启工作流
    private boolean restartable = false;

//    private boolean workflowStatusListenerEnabled = false;

    //超时策略、超时时间
    private TimeoutPolicy timeoutPolicy = TimeoutPolicy.ALERT_ONLY;
    private long timeoutSeconds;
    //异步
    private boolean async = false;
    //事务
    private boolean transactional = false;

    private Map<Integer, AtomicLong> painter;

}
