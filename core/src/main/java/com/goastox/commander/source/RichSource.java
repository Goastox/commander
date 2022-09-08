package com.goastox.commander.source;

public class RichSource<OUT> implements Source<OUT> {
    // 自定义 流程图之前的准备工作


    public static <OUT> RichSourceBuilder<OUT> builder() {
        return new RichSourceBuilder();
    }

    @Override
    public OUT getType() {
        return null;
    }
}
