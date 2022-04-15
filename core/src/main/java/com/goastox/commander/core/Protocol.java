package com.goastox.commander.core;

public abstract class Protocol {
    /**
     * 1-4 表示状态 最多15种状态   新建(初始化状态)、运行、阻塞、结束  <<6
     * 5-8 表示当前节点的权值，等于0才可以被执行  7  <<10
     * 9-12 表示节点出现了环，  relax
     * 13-16 节点类型
     * 17-64 表示当前节点的下游节点ID
     *
     * //TODO 考虑一下skip
     */
    public static final int BIT_STATE = 0;
    public static final int BIT_WEIGHT = 4;
    public static final int BIT_RELAX = 8;
    public static final int BIT_TYPE = 12;
    public static final int BIT_FOLLOW = 16;

    public static final long STATE_MAX = 0xf << BIT_STATE;
    public static final long WEIGHT_MAX = 0xf << BIT_WEIGHT;
    public static final long RELAX_MAX = 0xf << BIT_RELAX;
    public static final long TYPE_MAX = 0xf << BIT_TYPE;
    public static final long FOLLOW_MAX = 0xffffffffffffL << BIT_FOLLOW;


    public static final int CREATE = 0 << BIT_STATE;
    public static final int RUNNING = 1 << BIT_STATE;
    public static final int BLOCKED = 2 << BIT_STATE;
    public static final int STOP = 3 << BIT_STATE;

    public static final int TYPE_START = 0;
    public static final int TYPE_DECISION = 1;
    public static final int TYPE_DO_WHILE = 2;
    public static final int TYPE_OTHER = 0xe;
    public static final int TYPE_END = 0xf;

    public static final int CYCLE = 0xf;
    public static final int FOLLOW_BIT = 6;

}
