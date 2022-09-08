package com.goastox.commander.source;

public class RichSourceBuilder<OUT> {

    private String name;
    private Integer version;
    private String tracedId;
    private Long currentTime;
    private Spender spender;

    public RichSourceBuilder setTracedId(String tracedId){
        this.tracedId = tracedId;
        return this;
    }

    public RichSourceBuilder setCurrentTime(Long time){
        this.currentTime = time;
        return this;
    }

    public RichSourceBuilder setName(String name, Integer version){
        this.name = name;
        this.version = version;
        return this;
    }

    public RichSourceBuilder<OUT> setDeserializer(Spender<OUT> spender){
        this.spender = spender;
        return this;
    }


    public RichSource<OUT> build(){
        return new RichSource<>();
    }

}
