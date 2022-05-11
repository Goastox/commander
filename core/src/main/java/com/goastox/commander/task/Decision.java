package com.goastox.commander.task;

import com.goastox.commander.common.TaskType;
import com.goastox.commander.common.entity.Task;
import com.goastox.commander.core.Node;
import com.goastox.commander.execution.ContextWorkflow;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class Decision extends WorkflowTask{

    public Decision() {
        super(TaskType.DECISION);
    }

    @Override
    public Task execute(ContextWorkflow contextWorkflow, int token) {
        return null;
    }
}
