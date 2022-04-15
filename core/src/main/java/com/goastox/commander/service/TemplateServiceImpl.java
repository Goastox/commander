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
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TemplateServiceImpl {


    private MetadataMapper metadataMapper;

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
        Painter painter = Painter.create(next, type);
        workflowTemplate.setPainter(painter.graph());
        workflowTemplate.setCreateTime(System.currentTimeMillis());
        metadataMapper.createWorkflowTemplate(workflowTemplate);
        Set<Integer> inverted = painter.getInverted();
        if(!inverted.isEmpty()){
            throw new ApplicationException(Code.WARNING_INPUT, String.format("警告循环提醒 %s", inverted.toString()));
        }
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
