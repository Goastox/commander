package com.goastox.commander.core;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

import static com.goastox.commander.core.Protocol.*;
import static com.goastox.commander.core.Painter.GRAPH_MAX;

public class Node {

    private int state;
    private int weight;
    private int type;
    /**
     * 负值环路
     */
    private int relax;
    private long follow_sum;

    public Node(AtomicLong atomicLong){
        this.state = (int)(atomicLong.get() & STATE_MAX) >> BIT_STATE;
        this.weight = (int)(atomicLong.get() & WEIGHT_MAX) >> BIT_WEIGHT;
        this.type = (int)(atomicLong.get() & TYPE_MAX) >> BIT_TYPE;
        this.relax = (int)(atomicLong.get() & RELAX_MAX) >> BIT_RELAX;
        this.follow_sum = atomicLong.get() >> BIT_FOLLOW;
    }

    public int getState() {
        return state;
    }

    public int getWeight() {
        return weight;
    }

    public int getType() {
        return type;
    }

    public int getRelax() {
        return relax;
    }

    public int[] followToArray(){
        return LongStream.range(0, this.weight)
                .mapToInt(value -> (int) (this.follow_sum & (GRAPH_MAX & (value * FOLLOW_BIT)) ))
                .toArray();
    }


}
