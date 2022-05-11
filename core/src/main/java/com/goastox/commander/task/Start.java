package com.goastox.commander.task;

import com.goastox.commander.common.TaskType;
import com.goastox.commander.common.entity.Task;
import com.goastox.commander.common.template.TaskTemplate;
import com.goastox.commander.core.Node;
import com.goastox.commander.execution.ContextWorkflow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.locks.Condition;

@Slf4j
@Component
public class Start extends WorkflowTask {

    public Start() {
        super(TaskType.START_TASK);
    }

    @Override
    public Task execute(ContextWorkflow contextWorkflow, int token) {
        Map<Integer, TaskTemplate> tasks = contextWorkflow.getTasks();

        Map<Integer, Node> painter = contextWorkflow.getPainter();
        Node node = painter.get(token);
        node.toCompleted();



        Task task = new Task();
        task.setType(TaskType.START_TASK);
        task.setToken(token);
//        graph.get(token).toCompleted();
        log.info("首节点success");
        return task;
    }
}
