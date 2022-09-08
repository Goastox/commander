package com.goastox.commander.graph;

import com.alibaba.fastjson.JSON;
import com.goastox.commander.function.Collector;
import com.goastox.commander.function.Container;
import com.goastox.commander.function.MapFunction;
import com.goastox.commander.function.demo.FeatureFunction;
import com.goastox.commander.source.RichSource;
import com.goastox.commander.source.Source;
import com.goastox.commander.source.Spender;
import com.goastox.commander.source.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphExecutionEnvironment {

    private final List<Processor<?>> processor = new ArrayList<>(64);

    private GraphExecutionEnvironment() {
    }




    public static GraphExecutionEnvironment getExecutionEnvironment(){
        return new GraphExecutionEnvironment();
    }

    public <T> Graph<T> addSource(Source<T> source){

        // 把 source 包装成 processor 和 start_processor 合并

        Graph<T> outGraph = new Graph<>(this, source);
        return outGraph;
    }

    public <T> Graph<T> addSource(Source<T> source, TimeoutStrategy timeoutStrategy){
        Graph<T> outGraph = new Graph<>(this, source);
        return outGraph;
    }




    public void addProcess(Processor<?> processor){
        this.processor.add(processor);
    }


    public static void main(String[] args) {

        GraphExecutionEnvironment executionEnvironment = GraphExecutionEnvironment.getExecutionEnvironment();
        RichSource<User> build = RichSource.<User>builder().setDeserializer(new Spender<User>() {
            @Override
            public User deserialize(String param) throws Exception {
                return null;
            }
        }).build();
        Graph<User> graph = executionEnvironment.addSource(build, TimeoutStrategy.noTimeout());
        graph.map(new MapFunction<User, Object>() {
            @Override
            public void map(Container<User> container, Collector<Object> collect) {
                User contain = container.contain("");
            }
        });
    }

}
