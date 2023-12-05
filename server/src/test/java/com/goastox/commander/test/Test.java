package com.goastox.commander.test;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.goastox.commander.Commander;
import com.goastox.commander.mapper.MetadataMapper;
import com.goastox.commander.service.TemplateServiceImpl;
import com.goastox.commander.service.WorkflowServiceImpl;
import com.google.common.base.CharMatcher;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Commander.class)
public class Test {

    @Autowired
    private MetadataMapper metadataMapper;
    @Autowired
    private WorkflowServiceImpl workflowService;
    @Autowired
    private TemplateServiceImpl templateService;

    @org.junit.Test
    public void te(){
//        Optional<WorkflowTemplate> wk = metadataMapper.getWorkflowTemplate("test", 0);
//        System.out.println(JSONObject.toJSONString(wk.get()));
        HashMap<String, Object> input = new HashMap<>();
        input.put("uid", "3242342343432");
        workflowService.startWorkflow("test", 0, input);
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @org.junit.jupiter.api.Test
    public void test3(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("ActivityId","MAC_ZSY_KF_GCGD");
        HttpEntity<JSONObject> objectHttpEntity = new HttpEntity<>(httpHeaders);

        ArrayList<Hot> hots = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            String url = "https://app.c.nf.migu.cn/act-gcgd/query-song-list?period=202308&sort=hot&page="+ i +"&pageSize=100";
            ResponseEntity<JSONObject> exchange = restTemplate.exchange(url, HttpMethod.GET, objectHttpEntity, JSONObject.class);
            JSONObject body = exchange.getBody();
            JSONArray datas = body.getJSONArray("data");
            for (int x = 0; x < datas.size(); x++) {
                JSONObject data = datas.getJSONObject(x);
                hots.add(new Hot(data.getString("copyrightId"),data.getString("name"),data.getString("singer"),data.getInteger("hot")));
            }
        }
        HashMap<String, List<Hot>> map = new HashMap<>();
        hots.forEach( x ->{
            String[] split = x.getSinger().split("\\|");
            boolean flag = true;
            for (int i = 0; i < split.length; i++) {
                if ("王力宏".equals(split[i]) || (map.get(split[i]) != null && map.get(split[i]).size() >= 3)) {
                    flag = false;
                }
            }
            if(flag){
                for (int i = 0; i < split.length; i++) {
                    if(map.get(split[i]) == null){
                        ArrayList<Hot> h = new ArrayList<>(3);
                        h.add(x);
                        map.put(split[i], h);
                    }else {
                        List<Hot> h = map.get(split[i]);
                        if(h.size() < 3){
                            h.add(x);
                        }
                    }
                }
            }
        });
        HashSet<Hot> list = new HashSet<>();
        map.forEach((k,v) -> {
            list.addAll(v.stream().collect(Collectors.toSet()));
        });
        List<Hot> collect1 = list.stream().sorted(Comparator.comparing(Hot::getHot).reversed()).collect(Collectors.toList());
        EasyExcel.write("/Users/konghanghang/Downloads/hot-08.xlsx", Hot.class).sheet().doWrite(collect1);
    }


    public static void main(String[] args) {
        System.out.println(CharMatcher.is('-').removeFrom("2022-01-03"));
    }

}
