package com.goastox.commander.task;

import com.goastox.commander.common.TaskType;
import com.goastox.commander.common.entity.Task;
import com.goastox.commander.core.Node;
import com.goastox.commander.execution.ContextWorkflow;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
@Slf4j
public class Start extends WorkflowTask {

    public Start() {
        super(TaskType.START_TASK);
    }

    @Override
    public Task execute(ContextWorkflow contextWorkflow, Map<Integer, Node> graph) {
        Task task = new Task();
        graph.get(task).toCompleted();
        log.info("首节点success");
        return task;
    }
}
