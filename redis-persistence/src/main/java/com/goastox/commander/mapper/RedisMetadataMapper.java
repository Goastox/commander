package com.goastox.commander.mapper;

import com.alibaba.fastjson.JSON;
import com.goastox.commander.common.template.WorkflowTemplate;
import com.goastox.commander.exception.ApplicationException;
import com.goastox.commander.exception.ApplicationException.Code;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.*;

public class RedisMetadataMapper implements MetadataMapper{
    private static final String TEMP_NAME = "workflow_template";

    private static final String TEMP_NAME_ALL = "all_workflow_template_name";

    private static final String NAMESPACE_SEP = ".";
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void createWorkflowTemplate(WorkflowTemplate temp) {
        if(stringRedisTemplate.opsForHash().hasKey(nsKey(TEMP_NAME, temp.getName()), String.valueOf(temp.getVersion()))){
            throw new ApplicationException(Code.INVALID_INPUT, temp.getName() + " already exists!");
        }
        this.createOrUpdate(temp);
    }

    @Override
    public void updateWorkflowTemplate(WorkflowTemplate temp) {
        this.createOrUpdate(temp);
    }

    @Override
    public void removeWorkflowTemplate(String name, Integer version) {
        Long result = stringRedisTemplate.opsForHash().delete(nsKey(TEMP_NAME, name), String.valueOf(version));
        if(!result.equals(1L)){
            throw new ApplicationException(Code.NOT_FOUND,
                    String.format("Cannot remove the workflow - no such workflow  definition: %s version: %d", name, version));
        }
    }

    @Override
    public List<WorkflowTemplate> getAllWorkflowTemplate() {
        LinkedList<WorkflowTemplate> templates = new LinkedList<>();
        Set<String> members = stringRedisTemplate.opsForSet().members(TEMP_NAME_ALL);
        members.stream().forEach(x -> {
            List<Object> values = stringRedisTemplate.opsForHash().values(nsKey(TEMP_NAME, x));
            templates.addAll((List<WorkflowTemplate>)(Object)values);
        });
        return templates;
    }

    @Override
    public Optional<WorkflowTemplate> getWorkflowTemplate(String name, Integer version) {
            Object o = stringRedisTemplate.opsForHash().get(nsKey(TEMP_NAME, name), String.valueOf(version));
            WorkflowTemplate workflowTemplate = JSON.parseObject((String) o, WorkflowTemplate.class);
            return Optional.of(workflowTemplate);
    }

    @Override
    public Optional<WorkflowTemplate> getLatestWorkflowTemplate(String name){
        OptionalInt maxVersion = getWorkflowMaxVersion(name);
        if (maxVersion.isPresent()){
            Object o = stringRedisTemplate.opsForHash().get(nsKey(TEMP_NAME, name), String.valueOf(maxVersion.getAsInt()));
            WorkflowTemplate workflowTemplate = JSON.parseObject((String) o, WorkflowTemplate.class);
            return Optional.of(workflowTemplate);
        }
        return Optional.empty();
    }
    private void createOrUpdate(WorkflowTemplate template){
        stringRedisTemplate.opsForHash().put(nsKey(TEMP_NAME, template.getName()), String.valueOf(template.getVersion()), toJson(template));
        stringRedisTemplate.opsForSet().add(nsKey(TEMP_NAME_ALL), template.getName());
    }
    private OptionalInt getWorkflowMaxVersion(String name){
        return stringRedisTemplate.opsForHash().keys(nsKey(TEMP_NAME, name)).stream().mapToInt(x -> Integer.valueOf(x.toString())).max();
    }

    String nsKey(String... var){
        //读取配置文件加前缀
        StringBuffer stringBuffer = new StringBuffer();
        Arrays.stream(var).forEach( v ->{
            stringBuffer.append(v).append(NAMESPACE_SEP);
        });
        return StringUtils.removeEnd(stringBuffer.toString(), NAMESPACE_SEP);
    }
    String toJson(Object var){
        return JSON.toJSONString(var);
    }
}
