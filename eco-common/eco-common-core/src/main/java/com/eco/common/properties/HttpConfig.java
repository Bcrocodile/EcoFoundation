package com.eco.common.properties;

import lombok.Data;

@Data
public class HttpConfig {
    /**
     * 请求超时时间
     */
    private int connectTimeSeconds = 60;
    /**
     * http读取超时时间
     */
    private int readTimeSeconds = 30;
    /**
     * http写超时时间
     */
    private int writeTimeSeconds = 30;
}