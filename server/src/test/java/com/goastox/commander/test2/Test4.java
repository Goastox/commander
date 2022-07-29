package com.goastox.commander.test2;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Test4 {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DataStreamSource<Tuple2<Integer, Integer>> stream = env.fromElements(Tuple2.of(1, 1), Tuple2.of(2, 2), Tuple2.of(1,3));

        DataStream<Tuple2<Integer, Integer>> sum = stream.keyBy(0).sum(1);
        sum.print("测试---");
        env.execute();



    }

}
