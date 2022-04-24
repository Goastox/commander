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

    //图
    private Map<Integer, Node> painter;

    private Map<Integer, TaskTemplate> tasks;

    @Resource(name = "simpleThreadPool")
    private ExecutorService service;
    public void decide(Integer token){
        Node node = this.painter.get(token);
        if (node.getWeight() == 0){//判断各种情况的状态
            Arrays.stream(node.followToArray()).forEach(x -> {
                service.execute(()->{
                    Task task = new Task();
//                    WorkflowTask.get(tasks.get(token).getType()).execute(task, this);
                });
            });
        }
    }


}
