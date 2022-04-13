package com.goastox.commander.execution;

import com.goastox.commander.common.WorkflowStatus;
import com.goastox.commander.common.entity.Workflow;
import com.goastox.commander.common.template.TaskTemplate;
import com.goastox.commander.common.template.WorkflowTemplate;
import com.goastox.commander.core.Node;
import com.goastox.commander.core.Painter;
import com.goastox.commander.utils.IDgenerator;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
        workflow.setPainter(workflowTemplate.getPainter());
        workflow.setCreatedBy(null);
        workflow.setCreateTime(System.currentTimeMillis());

        Map<Integer, TaskTemplate> tasks = workflowTemplate.getTasks();

        //创建工作流实例上下文
        ContextWorkflow context = new ContextWorkflow();
        context.setTasks(tasks);
        context.setPainter(workflow.getPainter());
        //解析全局入参
        HashMap<String, Object> map = new HashMap<>();
        input.forEach( (k,v) -> map.put("workflow.input." +k, v) );
        context.setContextInput(map);

        Painter painter = Painter.format(workflowTemplate.getPainter());
        this.BFS(0, painter, tasks);
        //TODO 处理output


        // 获取 工作流任务图
        // 取出 start 节点，交给 task并行执行器
        // 更新图状态，权值 当前节点执行完更新自身节点
        // 更新下游节点

        return null;
    }
    private void BFS(Integer token, Painter painter, Map<Integer, TaskTemplate> tasks){
        Node node = painter.getNode(token);

        if(node.getWeight() == 0 && node.getType() == 0xf){
            // 执行 end task 任务
            return;
        }
        //调用task任务
        int[] follow = node.followToArray();
        Arrays.stream(follow).forEach(x->{
            //实现并行逻辑
            // 分叉逻辑需要特殊对待
            // 核心调用点
            this.BFS(x, painter, tasks);
        });
    }

}
