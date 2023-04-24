package com.eco.common.web.util.resubmit;
import java.lang.annotation.*;

/**
 * @author chenz
 * @title: ResubmitCheck
 * @description: 防重复提交
 * @date 2021/9/24 16:32
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResubmitCheck {

    /**
     * 防重复操作过期时间
     * @return
     */
    int timeout() default 2;

}
