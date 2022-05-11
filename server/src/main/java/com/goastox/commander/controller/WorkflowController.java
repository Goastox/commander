package com.goastox.commander.controller;

import com.goastox.commander.service.WorkflowServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import static com.goastox.commander.controller.RequestMappingConstants.WORKFLOW;

@RestController
@RequestMapping(value = WORKFLOW)
public class WorkflowController {
    @Autowired
    private WorkflowServiceImpl workflowService;

    //支持临时模板
    public String startWorkflow(){
        return null;
    }

    @PostMapping("{name}")
    public Map<String, Object> startWorkflow(@PathVariable("name") String name,
                                             @PathVariable(value = "version", required = false) Integer version,
                                             @RequestBody Map<String, Object> input){
        return workflowService.startWorkflow(name, version, input);
    }




}
