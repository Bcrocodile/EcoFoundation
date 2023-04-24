package com.eco.common.redis.redisson;

import cn.hutool.core.util.StrUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson 配置
 */
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient(RedissonProperties properties) {
        if (properties.getServerAddress() == null) {
            return null;
        }
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        String port = properties.getPort() == null ? "6379" : properties.getPort();
        singleServerConfig.setAddress(properties.getServerAddress() + ":" + port);
        singleServerConfig.setDatabase(properties.getDatabase());
        String password = properties.getPassword();
        if (StrUtil.isNotBlank(password)) {
            singleServerConfig.setPassword(password);
        }
        return Redisson.create(config);
    }


}
