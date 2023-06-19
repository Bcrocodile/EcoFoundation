package com.eco.common.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * 雪花算法生成工具类
 *
 * @author 桂枝
 */
public class IdUtils {

    private static Snowflake snowflake = IdUtil.getSnowflake(1,1);

    /**
     * 生成long 类型的ID
     * @return
     */
    public static Long getId() {
        return snowflake.nextId();
    }

    /**
     * 生成String 类型的ID
     * @return
     */
    public static String getIdStr() {
        return snowflake.nextIdStr();
    }

    public static void main(String[] args) {
        System.out.println(getId());
    }

}
