package com.goastox.commander.monitor;

import com.goastox.commander.common.WorkflowStatus;
import com.goastox.commander.utils.IDgenerator;

public class AbstractRecorder implements Recorder{

    private String workflowId;
    private final Long createTime = System.currentTimeMillis();
    private WorkflowStatus currentState = WorkflowStatus.RUNNING;

    public AbstractRecorder() {
    }

    @Override
    public void record(Object value) {

    }


    public static AbstractRecorder of(){
        return of(IDgenerator.generator());
    }

    public static AbstractRecorder of(String workflowId){
        AbstractRecorder abstractRecorder = new AbstractRecorder();
        abstractRecorder.workflowId = workflowId;
        return abstractRecorder;
    }
}
