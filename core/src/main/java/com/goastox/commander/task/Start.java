package com.goastox.commander.task;

import com.goastox.commander.common.TaskType;
import com.goastox.commander.common.entity.Task;
import com.goastox.commander.core.Node;
import com.goastox.commander.execution.ContextWorkflow;

import java.util.Map;

import static com.goastox.commander.core.Protocol.COMPLETED;

public class Start extends WorkflowTask {

    public Start() {
        super(TaskType.START_TASK);
    }

    @Override
    public boolean execute(Task task, ContextWorkflow contextWorkflow, Map<Integer, Node> graph) {
        graph.get(task.getToken()).updateWeight(COMPLETED);
        return true;
    }
}
