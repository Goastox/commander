package com.goastox.commander.mapper;

import com.goastox.commander.common.template.WorkflowTemplate;

import java.util.List;
import java.util.Optional;

public interface MetadataMapper {

    void createWorkflowTemplate(WorkflowTemplate workflowTemplate);

    void updateWorkflowTemplate(WorkflowTemplate workflowTemplate);

    void removeWorkflowTemplate(String name, Integer version);

    List<WorkflowTemplate> getAllWorkflowTemplate();

    Optional<WorkflowTemplate> getWorkflowTemplate(String name, Integer version);

    Optional<WorkflowTemplate> getLastWorkflowTemplate(String name);

}
