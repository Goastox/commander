package com.goastox.commander.graph;

import com.goastox.commander.function.Function;
import com.goastox.commander.function.MapFunction;
import com.goastox.commander.source.Source;

public class Graph<T> {

    private final GraphExecutionEnvironment environment;

    private Source<T> source;

    public Graph(GraphExecutionEnvironment environment) {
        this.environment = environment;
    }


    public Graph(GraphExecutionEnvironment environment, Source<T> source){
        this.environment = environment;
        this.source = source;
    }

    public GraphExecutionEnvironment getEnvironment(){
        return environment;
    }

    // 拼图，把所有 task 穿起来
    // 针对不同的操作设置不同的返回值，以实现链式调用

    public <R> Graph<R> map(MapFunction<T, R> var){
        Processor<T> objectProcessor = new Processor<>();
        getEnvironment().addProcess(null);

        return (Graph<R>) this;
    }

//    action

    public Graph skip(Function var){
        return null;
    }

    public void fork(Function var){}

    public void join(){}

    public void sub(){
        // 子任务
    }

    public void async(){}

    public void decision(){}

}
