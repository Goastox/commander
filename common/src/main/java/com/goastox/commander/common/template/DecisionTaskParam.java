package com.goastox.commander.common.template;

import com.goastox.commander.common.template.TaskTempRequest;

import java.util.HashMap;
import java.util.Map;

public class DecisionTaskParam extends TaskTempRequest {

    //分支参数
    private String caseParam;
    private String caseExpression;
    private String scriptExpression;
    private Map<String, Integer> decisionCases = new HashMap<>();//可选项

}
