package com.goastox.commander.source.bpmn.param;

import lombok.Data;

import java.util.Map;

@Data
public class RichVariable extends Variable{


    private Map<String, String> normalVar;

    private Map<String, String> normalVal;

    private boolean async;

}
