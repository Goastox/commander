package com.goastox.commander.service;

import com.goastox.commander.common.template.WorkflowTemplate;
import com.goastox.commander.exception.ApplicationException;
import com.goastox.commander.execution.ExecutionWorkflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WorkflowServiceImpl {

    @Autowired
    private TemplateServiceImpl templateService;
    @Autowired
    private ExecutionWorkflow executionWorkflow;

    // TODO 子任务，同步，异步
    public Map<String, Object> startWorkflow(String name, Integer version, Map<String, Object> input){
        WorkflowTemplate workflowTemplate = templateService.getWorkflowTemplate(name, version);
        if (workflowTemplate == null) {
            throw new ApplicationException(ApplicationException.Code.NOT_FOUND,
                    String.format("No such workflow found by name: %s, version: %d", name, version));
        }
        return executionWorkflow.startWorkflow(workflowTemplate, input);
    }

}
