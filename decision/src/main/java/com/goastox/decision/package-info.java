package com.goastox.decision;

// 是否要接入数据源，如果入参是所有规则数据太浪费资源，如果接入数据源怎么优化（提前加载临阶层级的规则数据）
// 预留规则版本发布记录，分析发布版本 和 数据结果 对比分析，帮助业务人员调优参数，以及后期可能的话加入决策树算法拟合最优结果对人工调参加以辅助


// y = f(x...)
//https://toutiao.io/posts/rdni5i8/preview
// 规则  评分卡（简单评分卡、复杂评分卡） 模型  表达式  决策树(规则树) 决策表（决策表需要专用的解析器或编译器） 决策流（工作流）
// 规则集（导向式规则集、脚本式规则集）

/**
 * rete 是一个有向无环图
 *
 * RootNode 是图的根节点
 * ObjectTypeNode 是对象类型节点，保证所有传入的对象只会进入自己类型所在的网络
 * AlphaNode  是条件判断节点，只有符合条件才能向下传播
 * JoinNode 是连接节点，将两个分支进行连接，相当与 and操作
 * NotNode  过滤节点，过滤掉数组中不存在的元素
 * LeftInputAdapterNodes 将单个对象转化为数组
 * TerminalNode 终结节点，说明已经完成所有的条件
 */

/**
 * Working-Memory 存放入参
 * RuleBase 存放规则
 * Pattern-Matcher 执行器
 * Agenda  判断逻辑执行完之后 then的执行部分（Action）
 * Execution-Engine
 *
 */


