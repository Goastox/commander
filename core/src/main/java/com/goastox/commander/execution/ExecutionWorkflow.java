package com.goastox.commander.execution;

import com.goastox.commander.common.WorkflowStatus;
import com.goastox.commander.common.entity.Workflow;
import com.goastox.commander.common.template.TaskTemplate;
import com.goastox.commander.common.template.WorkflowTemplate;
import com.goastox.commander.core.Node;
import com.goastox.commander.core.NodeBuilder;
import com.goastox.commander.core.Painter;
import com.goastox.commander.task.WorkflowTask;
import com.goastox.commander.utils.IDgenerator;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

import static com.goastox.commander.core.Protocol.*;

@Service
public class ExecutionWorkflow {
    private final Set<Integer> set = Sets.newHashSet();

    private final Map<Integer, Integer> map = new HashMap<>();

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

        Map<Integer, TaskTemplate> tasks = workflowTemplate.getTasks();

        //创建工作流实例上下文
        ContextWorkflow context = new ContextWorkflow();
        context.setTasks(tasks);
        context.setPainter(workflow.getGraph());
        //解析全局入参
        HashMap<String, Object> map = new HashMap<>();
        input.forEach( (k,v) -> map.put("workflow.input." +k, v) );
        context.setContextInput(map);

        Painter painter = Painter.format(workflowTemplate.getPainter());

        set.add(workflowTemplate.getStartToken());
//        this.decide(painter, tasks);
        //TODO 处理output


        // 获取 工作流任务图
        // 取出 start 节点，交给 task并行执行器
        // 更新图状态，权值 当前节点执行完更新自身节点
        // 更新下游节点

        return null;
    }

    @Resource(name = "simpleThreadPool")
    private ExecutorService service;

    //TODO 并行计算模型如何设计
    private void decide(Map<Integer, TaskTemplate> tasks, Integer startToken, Map<Integer, AtomicLong> graph,Painter painter){
        //
        while (!set.isEmpty()){
            set.stream().forEach( x -> {
                service.execute( ()-> WorkflowTask.get(tasks.get(x).getTaskType().name()).execute());
                set.remove(x);
            });
            Node node = NodeBuilder.format(graph.get(startToken));
            Arrays.stream(node.followToArray()).filter(Objects::nonNull).forEach(x->{
                painter.updateWeight(0, x);
                set.add(x);
            });
        }
    }

    //不同类型的task对应不同的线程池

}
