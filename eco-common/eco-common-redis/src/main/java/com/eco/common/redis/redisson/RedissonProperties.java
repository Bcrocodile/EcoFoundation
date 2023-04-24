package com.eco.common.redis.redisson;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "redisson")
@Data
public class RedissonProperties {

    private String serverAddress;

    private String port;

    private String password;

    private Integer database;

    public Integer getDatabase() {
        if (null == database) {
            return 0;
        }
        return database;
    }
}
