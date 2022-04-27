package com.goastox.commander.execution;

import com.goastox.commander.common.entity.Workflow;
import com.goastox.commander.common.template.TaskTemplate;
import com.goastox.commander.common.template.WorkflowTemplate;
import com.goastox.commander.core.Node;
import com.goastox.commander.task.WorkflowTask;
import com.goastox.commander.utils.SpringContextUtil;
import lombok.Data;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

@Data
public class ContextWorkflow {

    private Workflow workflow;

    private WorkflowTemplate workflowTemplate;

    private Map<String, Object> contextInput;
    private Map<String, Object> contextOutput;

    private Map<Integer, Node> painter;

    private Map<Integer, TaskTemplate> tasks;

    public ThreadPoolExecutor threadPoolExecutor = SpringContextUtil.getBean(ThreadPoolExecutor.class);

    public void decide(Integer token){// 入参当前已执行成功的 token
        Node node = this.painter.get(token);
        if (node.stateOf_COMPLETED()){//判断各种情况的状态
            Arrays.stream(node.followToArray())
                .filter(x->x > 0)
                .filter(x -> {//判断权值是否为0  环路逻辑处理
                    return this.painter.get(x).decrementWeight() == 0;
                })
                .forEach(x -> {
                    threadPoolExecutor.execute(()->{
                            WorkflowTask.get(tasks.get(x).getType()).callback(this, painter, x);
                        });
                    });
        }
    }


}
