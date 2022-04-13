package com.goastox.commander.core;

import com.goastox.commander.common.TaskType;
import com.goastox.commander.exception.ApplicationException;
import com.goastox.commander.exception.ApplicationException.Code;
import com.goastox.commander.utils.PreNumberConditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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
    private final HashMultimap<Integer, Integer> invertTasks = HashMultimap.create();
    private final Map<Integer, Integer> weights = Maps.newHashMap();
    private Map<Integer, TaskType> typeMap;
    private Set<Integer> inverted;

    public static final int GRAPH_MAX = 0x3f;

    //当前节点
    private int present;

    private static Painter create(int size){
        PreNumberConditions.of(size).min(0).max(GRAPH_MAX);
        Painter painter = new Painter();
        painter.graph = Maps.newLinkedHashMapWithExpectedSize(size);
        return painter;
    }

    public static Painter create(Map<Integer, int[]> map, Map<Integer, TaskType> map2){
        Painter painter = create(map.size());
        painter.tasks = map;
        painter.typeMap = map2;
        painter.invert();
        painter.initWeight();
        painter.cycleCheck();
        painter.addAll(map);
        return painter;
    }


    private void invert(){
        tasks.forEach((k, v) -> {
            if (v != null && v.length > 0) {
                Arrays.stream(v).forEach(x -> invertTasks.put(x, k));
            } else {
                //没有下游节点
                stack.offer(k);
            }
        });
    }

    private void initWeight(){
        invertTasks.asMap()
                .forEach((k,v)-> weights.put(k, v.size()));
    }

    //回环检测
    public void cycleCheck(){
        while (!stack.isEmpty()) {
            invertTasks.removeAll(stack.poll()).stream().forEach( x -> {
                if(!invertTasks.containsValue(x)){
                    stack.offer(x);
                }
            });
        }
        inverted = invertTasks.keySet();
    }

    private Painter add(int token, int... follow){
        AtomicLong atomicLong = NodeBuilder.of()
                .weight(this.weights.get(token))
                .type(this.typeMap.get(token))
                .relax(inverted.contains(token)? CYCLE : 0)//初始化最大值
                .follow(follow)
                .build();
        this.graph.put(token, atomicLong);
        return this;
    }

    public Painter addAll(Map<Integer, int[]> map){
        map.forEach(this::add);
        return this;
    }

    public Map<Integer, AtomicLong> graph(){
        return this.graph;
    }

    public static Painter format(Map<Integer, AtomicLong> graph){
        return new Painter(graph);
    }

    private long get(int token){
        return this.graph.get(token).get();
    }

    public AtomicLong get(Integer token){
        return this.graph.get(token);
    }

    public Node getNode(Integer token){
        return new Node(this.graph.get(token));
    }



    private boolean compareAndSet(long expect, long update, int token){
        // TODO 修改失败逻辑处理
        if(!this.graph.get(token).compareAndSet(expect, update)){
            throw new ApplicationException(Code.BACKEND_ERROR, "修改失败");
        }
        return true;
    }
    // TODO 修改操作考虑是否有数据安全问题
    public void updateWeight(int state, int token){
        long node = this.get(token);
        PreNumberConditions<Long> of = PreNumberConditions.of(node);
        if(of.isPresent()){
            this.compareAndSet(node, (node & (~WEIGHT_MAX)) | (state << BIT_WEIGHT), token);
        }
    }

    // TODO 负权节点需要考虑修改负权


    // 修改节点的下游节点
    //TODO 只有分叉类型需要修改下游节点
    public void updateFollow(){

    }

    public void createToRuning(int token){
        long node = this.get(token);
        this.compareAndSet(node, node & (~STATE_MAX) | RUNNING, token);
    }
    public void createToBlocked(int token){
        long node = this.get(token);
        this.compareAndSet(node, node & (~STATE_MAX) | BLOCKED, token);
    }
    public void runningToBlocked(int token){
        long node = this.get(token);
        this.compareAndSet(node, node & (~STATE_MAX) | BLOCKED, token);
    }
    public void blockedToRunning(int token){
        long node = this.get(token);
        this.compareAndSet(node, node & (~STATE_MAX) | RUNNING, token);
    }
    public void runningToStop(int token){
        long node = this.get(token);
        this.compareAndSet(node, node & (~STATE_MAX) | STOP, token);
    }
}
