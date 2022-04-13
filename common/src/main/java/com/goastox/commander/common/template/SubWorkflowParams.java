package com.goastox.commander.common.template;

import java.util.Map;

public class SubWorkflowParams {

    private String name;

    private int version;

    //作用域，设置执行环境
    private Map<String, String> taskToDomain;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Map<String, String> getTaskToDomain() {
        return taskToDomain;
    }

    public void setTaskToDomain(Map<String, String> taskToDomain) {
        this.taskToDomain = taskToDomain;
    }
}
