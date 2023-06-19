package com.eco.common.result;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Administrator
 */

@AllArgsConstructor
@NoArgsConstructor
public enum ResultCode implements IResultCode, Serializable {

    SUCCESS(200, "访问成功"),

    /**
     * 系统异常码 A
     */
    USER_ERROR(001, "用户端错误"),
    USER_LOGIN_ERROR(200, "用户登录异常"),
    USER_NOT_EXIST(201, "用户或角色不存在"),
    USER_ACCOUNT_LOCKED(202, "用户账户被冻结"),
    USER_ACCOUNT_INVALID(203, "用户账户已作废"),

    USERNAME_OR_PASSWORD_ERROR(210, "用户名或密码错误"),
    INPUT_PASSWORD_EXCEED_LIMIT(211, "用户输入密码次数超限"),
    CLIENT_AUTHENTICATION_FAILED(212, "客户端认证失败"),
    TOKEN_INVALID_OR_EXPIRED(230, "token无效或已过期"),
    TOKEN_ACCESS_FORBIDDEN(231, "token已被禁止访问"),

    AUTHORIZED_ERROR(300, "访问权限异常"),
    ACCESS_UNAUTHORIZED(301, "访问未授权"),

    PARAM_ERROR(400, "用户请求参数错误"),
    RESOURCE_NOT_FOUND(401, "请求资源不存在"),
    PARAM_IS_NULL(410, "请求必填参数为空"),
    QUERY_MODE_IS_NULL(411, "查询模式为空"),

    SYSTEM_EXECUTION_ERROR(412, "系统执行出错"),

    SYSTEM_DISASTER_RECOVERY_TRIGGER(415, "系统容灾功能被触发"),
    FLOW_LIMITING(416, "系统限流"),
    DEGRADATION(417, "系统功能降级"),
    RESUBMIT_ERROR(418, "重复提交"),


    ;


    private Integer code;
    private String msg;

    public static ResultCode getValue(String code) {
        for (ResultCode value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        // 默认系统执行错误
        return SYSTEM_EXECUTION_ERROR;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public ResultCode setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "\"code\":\"" + code + '\"' +
                ", \"msg\":\"" + msg + '\"' +
                '}';
    }
}
