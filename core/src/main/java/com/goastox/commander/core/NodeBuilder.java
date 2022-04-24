package com.goastox.commander.core;

import com.goastox.commander.common.TaskType;
import com.goastox.commander.utils.PreNumberConditions;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public final class NodeBuilder extends Protocol {

    private static final int STATE = CREATE;
    private int weight;
    private int type;
    /**
     * TODO 负值环路
     */
    private int relax;
    private long follow_sum;


    public static NodeBuilder of(){
        return new NodeBuilder();
    }

    public NodeBuilder weight(int weight){
        this.weight = weight;
        return this;
    }
    public NodeBuilder type(TaskType type){
        this.type = this.converter(type);
        return this;
    }
    public NodeBuilder relax(int relax){
        this.relax = relax;
        return this;
    }

    public NodeBuilder follow(int... follow) {
        if(follow == null){
            return this;
        }
        PreNumberConditions.of(follow.length).max(8);
        long followSum = IntStream.range(0, follow.length)
                .map(w -> follow[w] << (w * FOLLOW_BIT))
                .sum();
        this.follow_sum = followSum;
        return this;
    }

    // TODO LongAdder AtomicLong 怎么选择??
    public AtomicLong build(){
        long node = (long) STATE & (weight << BIT_WEIGHT)
                & (relax << BIT_RELAX) &(type << BIT_TYPE)
                & (follow_sum << BIT_FOLLOW);
        return new AtomicLong(node);
    }

    public static Node format(AtomicLong atomicLong){
        return new Node(atomicLong);
    }

    public static Map<Integer, Node> format(Map<Integer, AtomicLong> graph){
        HashMap<Integer, Node> map = Maps.newHashMapWithExpectedSize(graph.size());
        graph.forEach((k,v) -> map.put(k, format(v)));
        return map;
    }


    private static int converter(TaskType type){
        switch (type){
            case START_TASK:
                return TYPE_START;
            case END_TASK:
                return TYPE_END;
            case DECISION:
                return TYPE_DECISION;
            case DO_WHILE:
                return TYPE_DO_WHILE;
            default:
                return TYPE_OTHER;
        }
    }

}
