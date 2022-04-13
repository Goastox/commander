package com.goastox.commander.core;

import com.goastox.commander.utils.PreNumberConditions;

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
    public NodeBuilder type(int type){
        this.type = type;
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

}
