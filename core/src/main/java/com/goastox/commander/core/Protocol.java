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

    public static final long FOLLOW_ONE = 0x3f0000;
    public static final long FOLLOW_TWO = 0x7e0000;
    public static final long FOLLOW_THREE = 0xfc0000;
    public static final long FOLLOW_FOUR = 0x1f80000;
    public static final long FOLLOW_FIVE = 0x3f00000;
    public static final long FOLLOW_SIX = 0x7e00000;
    public static final long FOLLOW_SEVEN = 0xfc00000;
    public static final long FOLLOW_EIGHT = 0x1f800000;


    public static final int CREATE = 0 << BIT_STATE;
    public static final int RUNNING = 1 << BIT_STATE;
    public static final int BLOCKED = 2 << BIT_STATE;

    public static final int COMPLETED = 3 << BIT_STATE;

    public static final int SKIPPED = 4 << BIT_STATE;

    public static final int FAILED = 12 << BIT_STATE;

    public static final int TIMED_OUT = 13 << BIT_STATE;

    public static final int CANCELED = 14 >> BIT_STATE;


    public static final int TYPE_START = 0;
    public static final int TYPE_DECISION = 1;
    public static final int TYPE_DO_WHILE = 2;
    public static final int TYPE_OTHER = 0xe;
    public static final int TYPE_END = 0xf;

    public static final int CYCLE = 0xf;
    public static final int FOLLOW_BIT = 6;

    public static final int WEIGHT_PASS = 0;

}
