package com.goastox.commander.task;

import com.goastox.commander.common.TaskType;
import com.goastox.commander.common.entity.Task;
import com.goastox.commander.core.Node;
import com.goastox.commander.execution.ContextWorkflow;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public abstract class WorkflowTask{

    // TODO 记录节点执行过程，便于回溯

    public static Map<TaskType, WorkflowTask> registry = new HashMap<>();
    public WorkflowTask(){}
    public WorkflowTask(TaskType type){
        registry.put(type, this);
    }

    public static WorkflowTask get(TaskType type){
        return registry.get(type);
    }

    public Task execute(ContextWorkflow contextWorkflow, Map<Integer, Node> graph, int token){
        return null;
    }

    public void start(){
    }

    public boolean isAsync() {
        return false;
    }

    public void relax(){

    }

    public final void callback(ContextWorkflow contextWorkflow, Map<Integer, Node> graph, int token){
        Task task = this.execute(contextWorkflow, graph, token);
        contextWorkflow.decide(task.getToken());
    }

}
