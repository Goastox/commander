package com.goastox.commander.service;

import com.goastox.commander.common.TaskType;
import com.goastox.commander.common.template.TaskTempRequest;
import com.goastox.commander.common.template.WorkflowTempRequest;
import com.goastox.commander.core.Painter;
import com.goastox.commander.exception.ApplicationException;
import com.goastox.commander.exception.ApplicationException.Code;
import com.goastox.commander.mapper.MetadataMapper;
import com.goastox.commander.utils.Preconditions;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service
public class TemplateServiceImpl {

    private static final Integer START_TASK_TOKEN = 0;

    @Resource
    private MetadataMapper metadataMapper;


    public void registerWorkflowTemplate(WorkflowTempRequest workflowTempRequest){
        if(workflowTempRequest.getName().contains(":")){
            throw new ApplicationException(Code.INVALID_INPUT,
                    "Workflow name cannot contain the following set of characters: ':'");
        }
        Painter painter = this.detection(workflowTempRequest);
        workflowTempRequest.setCreateTime(System.currentTimeMillis());
        metadataMapper.createWorkflowTemplate(workflowTempRequest);
        log.info("template created successfully");
        Set<Integer> inverted = painter.getInverted();
        Preconditions.checkWaring(inverted.isEmpty(), String.format("Waring cycle: %s", inverted.toString()));
    }



    public WorkflowTempRequest getWorkflowTemplate(String name, Integer version){
        Optional<WorkflowTempRequest> template;
        if (version == null){
            template = metadataMapper.getLatestWorkflowTemplate(name);
        }else{
            template = metadataMapper.getWorkflowTemplate(name, version);
        }
        return template.orElseThrow(()-> new ApplicationException(Code.NOT_FOUND,
                String.format("No such workflow found by name: %s, version: %d", name, version)));
    }

    public List<WorkflowTempRequest> getAll(){
        return metadataMapper.getAllWorkflowTemplate();
    }

    public void unregisterWorkflowTemplate(String name, Integer version){
        metadataMapper.removeWorkflowTemplate(name, version);
    }

    public void updateWorkflowTemplate(WorkflowTempRequest template){
        Painter painter = this.detection(template);
        template.setUpdateTime(System.currentTimeMillis());
        metadataMapper.updateWorkflowTemplate(template);
        log.info("template updated successfully");
        Set<Integer> inverted = painter.getInverted();
        Preconditions.checkWaring(inverted.isEmpty(), String.format("Waring cycle: %s", inverted.toString()));
    }

    public Painter detection(WorkflowTempRequest workflowTempRequest){
        Map<Integer, TaskTempRequest> tasks = workflowTempRequest.getTasks();
        // 除首节点之外其他token不能为0，切其他节点的下游节点不能是首节点，检测节点token是否有重复值
        Preconditions.checkArgument(tasks.get(START_TASK_TOKEN).getType() == TaskType.START_TASK,
                "The start node of workflow must be START_TASK" );
        HashMap<Integer, int[]> next = Maps.newHashMapWithExpectedSize(tasks.size());
        HashMap<Integer, TaskType> type = Maps.newHashMapWithExpectedSize(tasks.size());
        tasks.forEach( (k,v)->{
            next.put(k,v.getNext());
            type.put(k, v.getType());
        });
        // creating painter
        Painter painter = Painter.size(tasks.size())
                .tasks(next)
                .type(type)
                .create();
        workflowTempRequest.setPainter(painter.graph());
        return painter;
    }

}
