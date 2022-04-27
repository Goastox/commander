package com.goastox.commander.core;

import com.goastox.commander.common.TaskType;
import com.goastox.commander.exception.ApplicationException;
import com.goastox.commander.exception.ApplicationException.Code;
import com.goastox.commander.utils.PreNumberConditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public final class Painter extends Protocol {
    private Painter(){}
    private Painter(Map<Integer, AtomicLong> graph) {
        this.graph = graph;
    }

    private Map<Integer, AtomicLong> graph;
    private Map<Integer, int[]> tasks;
    private final Queue<Integer> stack = Lists.newLinkedList();
    private final HashMultimap<Integer, Integer> inverted = HashMultimap.create();
    private final Map<Integer, Integer> weights = Maps.newHashMap();
    private Map<Integer, TaskType> typeMap;

    private Integer startTask;

    public static final int GRAPH_MAX = 0x3f;

    public static Painter of(int size){
        PreNumberConditions.of(size).min(0).max(GRAPH_MAX);
        Painter painter = new Painter();
        painter.graph = Maps.newLinkedHashMapWithExpectedSize(size);
        return painter;
    }

    public Painter startTask(int token){
        this.startTask = token;
        this.weights.put(token, 0);
        return this;
    }

    public Painter tasks(Map<Integer, int[]> tasks){
        this.tasks = tasks;
        return this;
    }

    public Painter type(Map<Integer, TaskType> typeMap){
        this.typeMap = typeMap;
        return this;
    }

    public Painter create(){
        this.invert();
        this.initWeight();
        this.cycleCheck();
        this.addAll(tasks);
        return this;
    }


    private void invert(){
        tasks.forEach((k, v) -> {
            if (v != null && v.length > 0) {
                Arrays.stream(v).forEach(x -> this.inverted.put(x, k));
            } else {
                //没有下游节点
                this.stack.offer(k);
            }
        });
    }

    private void initWeight(){
        inverted.asMap()
                .forEach((k,v)-> this.weights.put(k, v.size()));
    }

    //回环检测
    public void cycleCheck(){
        while (!stack.isEmpty()) {
            inverted.removeAll(stack.poll()).stream().forEach(x -> {
                if(!inverted.containsValue(x)){
                    stack.offer(x);
                }
            });
        }
    }

    private Painter add(int token, int... follow){
        AtomicLong atomicLong = NodeBuilder.of()
                .weight(this.weights.get(token))//没有首节点
                .type(this.typeMap.get(token))
                .relax(inverted.keySet().contains(token)? CYCLE : 0)//初始化最大值
                .follow(follow)
                .build();
        this.graph.put(token, atomicLong);
        return this;
    }

    public Painter addAll(Map<Integer, int[]> map){
        map.forEach(this::add);
        return this;
    }

    public Set<Integer> getInverted(){
        return this.inverted.keySet();
    }

    public Map<Integer, AtomicLong> graph(){
        return this.graph;
    }

    private long get(int token){
        return this.graph.get(token).get();
    }

    public AtomicLong get(Integer token){
        return this.graph.get(token);
    }

    public Integer getStartTask() {
        return startTask;
    }
}
