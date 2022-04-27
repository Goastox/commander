package com.goastox.commander.task;

import com.goastox.commander.common.TaskType;
import com.goastox.commander.common.entity.Task;
import com.goastox.commander.core.Node;
import com.goastox.commander.execution.ContextWorkflow;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.locks.Condition;

@Component
public class End extends WorkflowTask{
    public End() {
        super(TaskType.END_TASK);
    }

    @Override
    public Task execute(ContextWorkflow contextWorkflow, Map<Integer, Node> graph, int token) {
        Task task = new Task();
        task.setToken(token);
        task.setType("END_TASK");
        System.out.println("尾节点");
        graph.get(token).toCompleted();
        return task;
    }
}
