package com.goastox.commander.config;

public class Configuration {

    String DB_PROPERTY_NAME = "db";
    String DB_DEFAULT_VALUE = "memory";

    enum DB {
        REDIS,
        DYNOMITE,
        MEMORY,
        REDIS_CLUSTER,
        MYSQL
    }

}
