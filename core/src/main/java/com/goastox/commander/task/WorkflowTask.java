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


    public static Map<TaskType, WorkflowTask> registry = new HashMap<>();
    public WorkflowTask(){}
    public WorkflowTask(TaskType type){
        registry.put(type, this);
    }

    public static WorkflowTask get(TaskType type){
        return registry.get(type);
    }

    public boolean execute(Task task, ContextWorkflow contextWorkflow, Map<Integer, Node> graph){
        return true;
    }

    public void start(){
    }

    public boolean isAsync() {
        return false;
    }

    public void relax(){

    }
}
