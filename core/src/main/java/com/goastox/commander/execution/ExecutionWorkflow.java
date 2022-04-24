package com.goastox.commander.execution;

import com.goastox.commander.common.TaskType;
import com.goastox.commander.common.WorkflowStatus;
import com.goastox.commander.common.entity.Task;
import com.goastox.commander.common.entity.Workflow;
import com.goastox.commander.common.template.TaskTemplate;
import com.goastox.commander.common.template.WorkflowTemplate;
import com.goastox.commander.core.Node;
import com.goastox.commander.core.NodeBuilder;
import com.goastox.commander.core.Painter;
import com.goastox.commander.task.WorkflowTask;
import com.goastox.commander.utils.IDgenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ExecutionWorkflow {
    public Map<Object, Object> startWorkflow(WorkflowTemplate workflowTemplate, Map<String, Object> input){

        // TODO 不需要考虑模板加锁问题

        // 验证入参
        String workflowId = IDgenerator.generator();

        Workflow workflow = new Workflow();
        workflow.setWorkflowId(workflowId);
        workflow.setStatus(WorkflowStatus.RUNNING);
        workflow.setInput(input);
        workflow.setGraph(workflowTemplate.getPainter());
        workflow.setCreatedBy(null);
        workflow.setCreateTime(System.currentTimeMillis());
        workflow.setStartToken(workflowTemplate.getStartToken());

        //创建工作流实例上下文
        ContextWorkflow context = new ContextWorkflow();
        context.setTasks(workflowTemplate.getTasks());
        Map<Integer, Node> graph = NodeBuilder.format(workflowTemplate.getPainter());
        context.setPainter(graph);
        //解析全局入参
        HashMap<String, Object> map = new HashMap<>();
        input.forEach( (k,v) -> map.put("workflow.input." +k, v) );
        context.setContextInput(map);
        boolean execute = WorkflowTask.get(TaskType.START_TASK).execute(new Task(), context, graph);
        context.decide(workflow.getStartToken());

//        TODO 吊起首节点


        return null;
    }




}
