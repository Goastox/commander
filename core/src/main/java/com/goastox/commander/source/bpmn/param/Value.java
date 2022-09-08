package com.goastox.commander.source.bpmn.param;

import com.goastox.commander.source.Source;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Value<T extends Variable> implements Source {
    //  bpmn 参数定义
    private List<String> val;

    private Map<Integer, T> var;

    private Map<String, String> output;

    private long timeout;

    private String name;

    private Integer version;

    private String describe;

    @Override
    public Object getType() {
        return null;
    }
}
