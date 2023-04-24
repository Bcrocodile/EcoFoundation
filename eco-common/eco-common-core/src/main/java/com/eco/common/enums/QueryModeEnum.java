package com.eco.common.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Administrator
 */

public enum QueryModeEnum {
    // 分页查询
    PAGE("page"),
    //列表查询
    LIST("list"),
    //树形列表
    TREE("tree"),
    //  级联列表 对应级联选择器的下拉格式数据
    CASCADER("cascader"),
    ;

    @Getter
    @Setter
    private String code;

    QueryModeEnum(String code) {
        this.code = code;
    }

    public static QueryModeEnum getByCode(String code) {
        for (QueryModeEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        // 默认分页查询
        return PAGE;
    }

}
