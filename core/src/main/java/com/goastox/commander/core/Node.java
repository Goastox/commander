package com.goastox.commander.core;

import com.alibaba.fastjson.JSONObject;
import com.goastox.commander.exception.ApplicationException;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

import static com.goastox.commander.core.Painter.GRAPH_MAX;
import static com.goastox.commander.core.Protocol.*;

public class Node {

    private AtomicLong node;

    public Node(AtomicLong node){
        this.node = node;
    }

    public int getState() {
        return (int)(node.get() & STATE_MAX) >> BIT_STATE;
    }

    public int getWeight() {
        return (int)(node.get() & WEIGHT_MAX) >> BIT_WEIGHT;
    }

    public int getType() {
        return (int)(node.get() & TYPE_MAX) >> BIT_TYPE;
    }

    public int getRelax() {
        return (int)(node.get() & RELAX_MAX) >> BIT_RELAX;
    }

    public int[] followToArray(){
        return LongStream.range(0, 8)
                .mapToInt(value -> {
                    return (int) ((node.get() >> (BIT_FOLLOW + (value * FOLLOW_BIT))) & GRAPH_MAX);
                })
                .toArray();
    }

    public boolean stateOf_COMPLETED(){
        return (node.get() & STATE_MAX) == COMPLETED;
    }


    private boolean compareAndSet(long expect, long update){
        // TODO 修改失败逻辑处理
        if(!node.compareAndSet(expect, update)){
            throw new ApplicationException(ApplicationException.Code.BACKEND_ERROR, "修改失败");
        }
        return true;
    }
    // TODO 修改操作考虑是否有数据安全问题
    public void updateWeight(int weight){
        this.compareAndSet(node.get(), (node.get() & (~WEIGHT_MAX)) | (weight << BIT_WEIGHT));
    }

    // TODO 并行问题
    public synchronized int decrementWeight(){
        int weight = this.getWeight() - 1;
        this.compareAndSet(node.get(), (node.get() & (~WEIGHT_MAX)) | ( weight << BIT_WEIGHT));
        return weight;
    }


//    public int decrementWeight(){
//        int weight = this.getWeight() - 1;
//        node.set((node.get() & (~WEIGHT_MAX)) | ( weight << BIT_WEIGHT));
//        return weight;
//    }


    // TODO 负权节点需要考虑修改负权


    // 修改节点的下游节点
    //TODO 只有分叉类型需要修改下游节点
    public void updateFollow(){

    }

    public void toRunning(){
        this.compareAndSet(node.get(), node.get() & (~STATE_MAX) | RUNNING);
    }
    public void toCompleted(){
        this.compareAndSet(node.get(), node.get() & (~STATE_MAX) | COMPLETED);
    }



    public void toFailed(){
        this.compareAndSet(node.get(), node.get() & (~STATE_MAX) | FAILED);
    }
    public void toCanceled(){
        this.compareAndSet(node.get(), node.get() & (~STATE_MAX) | CANCELED);
    }

    public void toTimeout(){
        this.compareAndSet(node.get(), node.get() & (~STATE_MAX) | TIMED_OUT);
    }

    public void toSkipped(){
        this.compareAndSet(node.get(), node.get() & (~STATE_MAX) | SKIPPED);
    }




    public boolean weightOf_ZERO(){
        return (node.get() & WEIGHT_MAX) >> BIT_WEIGHT == WEIGHT_PASS;
    }



}
