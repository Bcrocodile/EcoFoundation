package com.eco.user.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author bcro
 * @title: UserProperties
 * @description: TODO
 * @date 2023/6/19 16:21
 */
@ConfigurationProperties(prefix = "user")
@Component
@Data
public class UserProperties {
    private String sex;
}
