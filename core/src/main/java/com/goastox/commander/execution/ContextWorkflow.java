package com.goastox.commander.execution;

import com.goastox.commander.common.entity.Workflow;
import com.goastox.commander.common.template.TaskTempRequest;
import com.goastox.commander.common.template.WorkflowTempRequest;
import com.goastox.commander.core.Node;
import com.goastox.commander.task.WorkflowTask;
import com.goastox.commander.utils.SpringContextUtil;
import lombok.Data;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ThreadPoolExecutor;

import static com.goastox.commander.core.Protocol.*;

@Data
public class ContextWorkflow {

    private Workflow workflow;

    private WorkflowTempRequest workflowTempRequest;

    private Properties contextParams;

    private Map<String, Object> outData;

    private Map<Integer, Node> painter;

    private Map<Integer, TaskTempRequest> tasks;

    public Object lock = new Object();

    public ThreadPoolExecutor threadPoolExecutor = SpringContextUtil.getBean(ThreadPoolExecutor.class);
    public TaskThreadPoolExecutor http = SpringContextUtil.getBean("", TaskThreadPoolExecutor.class);

    public void decide(Integer token){// 入参当前已执行成功的 token
        Node node = this.painter.get(token);

        if (node.stateOf_COMPLETED()){//判断各种情况的状态
            //判断是否是 end任务，吊起主线程
            if(node.getType() == TYPE_END){
                synchronized (lock){
                    lock.notifyAll();
                }
                return;
            }
            Arrays.stream(node.followToArray())
                .filter(x-> x > 0)
                .filter(x -> {//判断权值是否为0  环路逻辑处理
                    return this.painter.get(x).decrementWeight() == 0;
                })
                .forEach(x -> {
                    threadPoolExecutor.execute(()->{
                            WorkflowTask.get(tasks.get(x).getType()).callback(this, x);
                        });
                    });
        }else{
            // 状态未完成
        }
    }
}
