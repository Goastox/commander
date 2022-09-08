package com.goastox.commander.constant;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public enum RejectedExecutionUnit {

    ABORT_POLICY{
        public RejectedExecutionHandler handle(){
            return new ThreadPoolExecutor.AbortPolicy();
        }
    },

    CALLER_RUNS_POLICY{
        public RejectedExecutionHandler handle(){
            return new ThreadPoolExecutor.CallerRunsPolicy();
        }
    },

    DISCARD_OLDEST_POLICY{
        public RejectedExecutionHandler handle(){
            return new ThreadPoolExecutor.DiscardOldestPolicy();
        }
    },

    DISCARD_POLICY{

        public RejectedExecutionHandler handle(){
            return new ThreadPoolExecutor.AbortPolicy();
        }

    };

    public abstract RejectedExecutionHandler handle();


}
