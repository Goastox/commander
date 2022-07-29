package com.goastox.commander.mapper;

import com.goastox.commander.common.template.WorkflowTempRequest;

import java.util.List;
import java.util.Optional;

public interface MetadataMapper {

    void createWorkflowTemplate(WorkflowTempRequest workflowTempRequest);

    void updateWorkflowTemplate(WorkflowTempRequest workflowTempRequest);

    void removeWorkflowTemplate(String name, Integer version);

    List<WorkflowTempRequest> getAllWorkflowTemplate();

    Optional<WorkflowTempRequest> getWorkflowTemplate(String name, Integer version);

    Optional<WorkflowTempRequest> getLatestWorkflowTemplate(String name);

}
