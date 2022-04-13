package com.goastox.commander.service;

import com.goastox.commander.common.TaskType;
import com.goastox.commander.common.template.TaskTemplate;
import com.goastox.commander.common.template.WorkflowTemplate;
import com.goastox.commander.core.Painter;
import com.goastox.commander.exception.ApplicationException;
import com.goastox.commander.exception.ApplicationException.Code;
import com.goastox.commander.mapper.MetadataMapper;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
        Map<Integer, TaskTemplate> map = workflowTemplate.getTasks();
        HashMap<Integer, int[]> next = Maps.newHashMapWithExpectedSize(map.size());
        HashMap<Integer, TaskType> type = Maps.newHashMapWithExpectedSize(map.size());
        map.forEach( (k,v)->{
            next.put(k,v.getNext());
            type.put(k, v.getTaskType());
        });

        Map<Integer, AtomicLong> painter = Painter.create(next, type).graph();

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
