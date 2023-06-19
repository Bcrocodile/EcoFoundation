package com.eco.common.web.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;


@Data
@ConfigurationProperties("task.pool")
public class TaskThreadPoolConfig {

    /**
     * 核心线程池大小
     */
    private int corePoolSize =1;

    /**
     * 最大线程数
     */
    private int maxPoolSize =5;

    /**
     * 队列容量
     */
    private int queueCapacity =1;

    /**
     * 活跃时间
     */
    private int keepAliveSeconds = 5;

}
