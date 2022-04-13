package com.goastox.commander.service;

import com.goastox.commander.common.template.TaskTemplate;
import com.goastox.commander.common.template.WorkflowTemplate;
import com.goastox.commander.core.Painter;
import com.goastox.commander.exception.ApplicationException;
import com.goastox.commander.exception.ApplicationException.Code;
import com.goastox.commander.mapper.MetadataMapper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TemplateServiceImpl {

    private final MetadataMapper metadataMapper;

    public TemplateServiceImpl(MetadataMapper metadataMapper) {
        this.metadataMapper = metadataMapper;
    }

    public void registerWorkflowTemplate(WorkflowTemplate workflowTemplate){
        if(workflowTemplate.getName().contains(":")){
            throw new ApplicationException(Code.INVALID_INPUT,
                    "Workflow name cannot contain the following set of characters: ':'");
        }

        Map<Integer, TaskTemplate> tasks = workflowTemplate.getTasks();

        Map<Integer, int[]> collect = tasks.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getNext()));

        Map<Integer, AtomicLong> painter = Painter.create(collect).graph();

        // TODO 子任务，同步，异步

        workflowTemplate.setCreateTime(System.currentTimeMillis());
        metadataMapper.createWorkflowTemplate(workflowTemplate);
    }



    public WorkflowTemplate getWorkflowTemplate(String name, Integer version){
        Optional<WorkflowTemplate> template;
        if (version == null){
            template = metadataMapper.getLastWorkflowTemplate(name);
        }else{
            template = metadataMapper.getWorkflowTemplate(name, version);
        }
        return template.orElseThrow(()-> new ApplicationException(Code.NOT_FOUND,
                String.format("No such workflow found by name: %s, version: %d", name, version)));
    }

}
