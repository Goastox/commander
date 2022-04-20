package com.goastox.commander.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

@Component
public class WorkflowTask implements ITask{


    public Map<String, ITask> registry;

    public WorkflowTask(Map<String, ITask> registry) {
        this.registry = registry;
    }

    public WorkflowTask(){}

    public static WorkflowTask get(String type){
//        return registry.get(type);
        return null;
    }

    @Override
    public boolean execute() {
        return false;
    }
}
