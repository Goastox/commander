package com.goastox.commander.test;

import com.goastox.commander.Commander;
import com.goastox.commander.task.WorkflowTask;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Commander.class)
public class Test {
    @Autowired
    private WorkflowTask workflowTask;

    @org.junit.Test
    public void te(){
        System.out.println(workflowTask.toString());
    }

}
