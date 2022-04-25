package com.goastox.commander.execution;

import com.goastox.commander.common.entity.Task;
import com.goastox.commander.common.entity.Workflow;
import com.goastox.commander.common.template.TaskTemplate;
import com.goastox.commander.common.template.WorkflowTemplate;
import com.goastox.commander.core.Node;
import com.goastox.commander.task.WorkflowTask;
import lombok.Data;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicLong;

@Data
public class ContextWorkflow {

    private Workflow workflow;

    private WorkflowTemplate workflowTemplate;

    private Map<String, Object> contextInput;
    private Map<String, Object> contextOutput;

    private Map<Integer, Node> painter;

    private Map<Integer, TaskTemplate> tasks;

    @Resource(name = "simpleThreadPool")
    private ExecutorService service;
    public void decide(Integer token){// 入参当前已执行成功的 token
        Node node = this.painter.get(token);
        if (node.stateOf_COMPLETED()){//判断各种情况的状态
            Arrays.stream(node.followToArray())
                .filter(x -> {//判断权值是否为0  环路逻辑处理
                    return this.painter.get(x).weightOf_ZERO();
                })
                .forEach(x -> {
                    service.execute(()->{
                            WorkflowTask.get(tasks.get(token).getType()).execute(this, painter);
                        });
                    });
        }
    }


}
