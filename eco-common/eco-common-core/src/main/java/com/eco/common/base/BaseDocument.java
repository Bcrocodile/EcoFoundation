package com.eco.common.base;

import lombok.Data;

/**
 * document 是 ES 里的一个 JSON 对象，包括零个或多个field，类比关系数据库的一行记录
 */
@Data
public class BaseDocument {

    /**
     * 数据唯一标识
     */
    private String id;

    /**
     * 索引名称
     */
    private String index;
}
