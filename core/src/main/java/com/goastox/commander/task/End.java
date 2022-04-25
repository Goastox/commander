package com.goastox.commander.task;

import com.goastox.commander.common.TaskType;
import com.goastox.commander.common.entity.Task;
import com.goastox.commander.core.Node;
import com.goastox.commander.execution.ContextWorkflow;

import java.util.Map;

public class End extends WorkflowTask{
    public End() {
        super(TaskType.END_TASK);
    }

    @Override
    public Task execute(ContextWorkflow contextWorkflow, Map<Integer, Node> graph) {
        return super.execute(contextWorkflow, graph);
    }
}
