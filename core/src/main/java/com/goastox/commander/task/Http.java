package com.goastox.commander.task;

import com.goastox.commander.common.TaskType;
import com.goastox.commander.common.entity.Task;
import com.goastox.commander.core.Node;
import com.goastox.commander.execution.ContextWorkflow;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Http extends WorkflowTask{
    public Http() {
        super(TaskType.HTTP);
    }

    @Override
    public Task execute(ContextWorkflow contextWorkflow, Map<Integer, Node> graph, int token) {
        Task task = new Task();
        System.out.println("HTTP 节点");
        task.setToken(token);
        graph.get(token).toCompleted();
        return task;
    }
}
