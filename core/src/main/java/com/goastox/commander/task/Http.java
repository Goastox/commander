package com.goastox.commander.task;

import com.goastox.commander.common.TaskType;
import com.goastox.commander.common.entity.Task;
import com.goastox.commander.common.template.TaskTemplate;
import com.goastox.commander.core.Node;
import com.goastox.commander.execution.ContextWorkflow;
import com.goastox.commander.utils.ParametersUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class Http extends WorkflowTask{
    public Http() {
        super(TaskType.HTTP);
    }

    @Override
    public Task execute(ContextWorkflow contextWorkflow, int token) {
        Task task = new Task();
        task.setType(TaskType.HTTP);

        TaskTemplate template = contextWorkflow.getTasks().get(token);
        Map<String, String> inputParameters = template.getInputTemplate();

        Map<String, String> taskInput = ParametersUtils.getTaskInput(inputParameters, contextWorkflow.getContextParams());

        System.out.println("HTTP 节点");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        task.setToken(token);
//        graph.get(token).toCompleted();
        return task;
    }
}
