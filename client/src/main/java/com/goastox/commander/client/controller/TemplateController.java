package com.goastox.commander.client.controller;

import com.goastox.commander.common.template.WorkflowTempRequest;
import com.goastox.commander.service.TemplateServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.goastox.commander.client.controller.RequestMappingConstants.TEMPLATE;

@RestController
@RequestMapping(value = TEMPLATE)
public class TemplateController {

    @Resource
    private TemplateServiceImpl templateService;

    @PostMapping("workflow")
    public void create(@RequestBody WorkflowTempRequest workflowTempRequest){
        templateService.registerWorkflowTemplate(workflowTempRequest);
    }


    // TODO 发布之后 不提供修改功能，否则执行工作流需要对模板加 读写锁，导致效率不高还要依赖分布式锁，可以在版本方面进行优化，实现同样效果

    // 编辑需要考虑锁问题

    @PutMapping("workflow")
    public void update(@RequestBody WorkflowTempRequest workflowTempRequest){
        templateService.updateWorkflowTemplate(workflowTempRequest);
    }

    @GetMapping("workflow/{name}")
    public WorkflowTempRequest get(@PathVariable("name") String name,
                                   @RequestParam(value = "version", required = false) Integer version){
        return templateService.getWorkflowTemplate(name, version);
    }
    @GetMapping("workflow")
    public List<WorkflowTempRequest> getAll(){
        return templateService.getAll();
    }

    @DeleteMapping("workflow/{name}/{version}")
    public void unregister(@PathVariable("name") String name,
                           @PathVariable("version") Integer version){
        templateService.unregisterWorkflowTemplate(name, version);
    }

}
