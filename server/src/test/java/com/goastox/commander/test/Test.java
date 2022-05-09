package com.goastox.commander.test;

import com.alibaba.fastjson.JSONObject;
import com.goastox.commander.Commander;
import com.goastox.commander.common.TaskType;
import com.goastox.commander.common.template.TaskTemplate;
import com.goastox.commander.common.template.WorkflowTemplate;
import com.goastox.commander.mapper.MetadataMapper;
import com.goastox.commander.service.TemplateServiceImpl;
import com.goastox.commander.service.WorkflowServiceImpl;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Commander.class)
public class Test {

    @Autowired
    private MetadataMapper metadataMapper;
    @Autowired
    private WorkflowServiceImpl workflowService;
    @Autowired
    private TemplateServiceImpl templateService;

    @org.junit.Test
    public void te(){
//        Optional<WorkflowTemplate> wk = metadataMapper.getWorkflowTemplate("test", 0);
//        System.out.println(JSONObject.toJSONString(wk.get()));
        HashMap<String, Object> input = new HashMap<>();
        input.put("uid", "3242342343432");
        workflowService.startWorkflow("test", 0, input);
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
