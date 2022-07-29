package com.goastox.commander.test2;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.Arrays;

public class test1 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> source = env.fromElements("java,springboot", "scala,ceshi","goast");
        env.setParallelism(1);// 设置并行度
        source.print("before");
//        DataSet  批处理
        DataStream<String> map = source.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String var, Collector<String> collector) throws Exception {
                String[] split = var.split(",");
                Arrays.stream(split).forEach(x -> collector.collect(x));
            }
        });
        map.print("after");
        JobExecutionResult test1_job = env.execute("test1 job");

    }
}

