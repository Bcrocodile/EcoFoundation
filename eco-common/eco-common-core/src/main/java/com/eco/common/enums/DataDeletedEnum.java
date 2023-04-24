package com.eco.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Administrator
 * @DESCRIPTION/描述：数据逻辑删除状态
 **/
@AllArgsConstructor
@Getter
public enum DataDeletedEnum {

    /**
     * 是否有效数据
     * 0 未删除, 数据有效,
     * 1 已删除, 数据无效
     */
    ZERO(0),
    ONE(1),
    ;
    private Integer value;
}
