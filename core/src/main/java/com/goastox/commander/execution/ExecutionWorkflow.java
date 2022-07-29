package com.goastox.commander.execution;

import com.goastox.commander.common.WorkflowStatus;
import com.goastox.commander.common.entity.Workflow;
import com.goastox.commander.common.template.WorkflowTempRequest;
import com.goastox.commander.core.Node;
import com.goastox.commander.core.NodeBuilder;
import com.goastox.commander.task.Start;
import com.goastox.commander.utils.IDgenerator;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Properties;

@Service
public class ExecutionWorkflow {

    private static final Integer START_TASK_TOKEN = 0;

    // 参考flink架构设计，client负责图构建，master负责作业调度，worker负责任务执行
    // akka 分布式通信

    public Map<String, Object> startWorkflow(WorkflowTempRequest workflowTempRequest, Map<String, Object> input) {

        // TODO 不需要考虑模板加锁问题

        // 验证入参
        String workflowId = IDgenerator.generator();

        Workflow workflow = new Workflow();
        workflow.setWorkflowId(workflowId);
        workflow.setStatus(WorkflowStatus.RUNNING);
        workflow.setInput(input);
        workflow.setGraph(workflowTempRequest.getPainter());
        workflow.setCreatedBy(null);
        workflow.setCreateTime(System.currentTimeMillis());

        //创建工作流实例上下文
        ContextWorkflow context = new ContextWorkflow();
        context.setTasks(workflowTempRequest.getTasks());
        Map<Integer, Node> graph = NodeBuilder.format(workflowTempRequest.getPainter());
        context.setPainter(graph);
        //解析全局入参
        Properties properties = new Properties();
        input.forEach( (k,v) -> properties.setProperty("workflow.input." +k, (String) v) );
        context.setContextParams(properties);
        //  正常阻塞 后期服务改为 webflux
        new Start().callback(context, START_TASK_TOKEN);
        try {
            synchronized (context.lock){
                context.lock.wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return workflow.getOutput();
    }




}
