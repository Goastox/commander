package com.goastox.commander.common.template;

import com.goastox.commander.common.template.TaskTempRequest;
import lombok.Data;

@Data
public class HttpTaskParam extends TaskTempRequest {

    private String url;

    private String header;

}
