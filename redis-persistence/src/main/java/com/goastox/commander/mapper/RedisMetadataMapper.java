package com.goastox.commander.mapper;

import com.alibaba.fastjson.JSONObject;
import com.goastox.commander.common.TaskType;
import com.goastox.commander.common.template.TaskTemplate;
import com.goastox.commander.common.template.WorkflowTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class RedisMetadataMapper implements MetadataMapper{
    @Override
    public void createWorkflowTemplate(WorkflowTemplate workflowTemplate) {
    }

    @Override
    public void updateWorkflowTemplate(WorkflowTemplate workflowTemplate) {

    }

    @Override
    public void removeWorkflowTemplate(String name, Integer version) {

    }

    @Override
    public List<WorkflowTemplate> getAllWorkflowTemplate() {
        return null;
    }

    @Override
    public Optional<WorkflowTemplate> getWorkflowTemplate(String name, Integer version) {
        WorkflowTemplate workflowTemplate = new WorkflowTemplate();
        workflowTemplate.setName("test1");
        workflowTemplate.setVersion(1);

        TaskTemplate task = new TaskTemplate();
        task.setToken(0);
        task.setReferenceName("开始节点");
        task.setType(TaskType.START_TASK);
        task.setNext(new int[]{1,2});

        TaskTemplate task1 = new TaskTemplate();
        task1.setToken(1);
        task1.setReferenceName("第一个节点");
        task1.setType(TaskType.HTTP);
        task1.setNext(new int[]{3});

        TaskTemplate task2 = new TaskTemplate();
        task2.setToken(2);
        task2.setReferenceName("第二个节点");
        task2.setType(TaskType.HTTP);
        task2.setNext(new int[]{3});

        TaskTemplate task3 = new TaskTemplate();
        task3.setToken(3);
        task3.setReferenceName("尾结点");
        task3.setType(TaskType.END_TASK);

        HashMap<Integer, TaskTemplate> map = new HashMap<>();
        map.put(0, task);
        map.put(1, task1);
        map.put(2, task2);
        map.put(3, task3);
        workflowTemplate.setTasks(map);

//        {0:8454144,1:253968,2:253968,3:61472}
        HashMap<Integer, AtomicLong> graph = new HashMap<>();
        graph.put(0,new AtomicLong(8454144));
        graph.put(1,new AtomicLong(253968));
        graph.put(2,new AtomicLong(253968));
        graph.put(3,new AtomicLong(61472));
        workflowTemplate.setPainter(graph);

        return Optional.of(workflowTemplate);
    }

    @Override
    public Optional<WorkflowTemplate> getLastWorkflowTemplate(String name) {
        return Optional.empty();
    }
}
