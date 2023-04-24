package com.eco.common.utils.valid;

import lombok.Data;

import javax.validation.ValidationException;
import java.io.Serializable;
import java.util.List;

/**
 * 参数校验返回结果
 * @author Administrator
 */
@Data
public class ValidResult implements Serializable {

    /**
     *  是否验证通过
     */
    private boolean success;

    /**
     * 未通过验证的参数名
     * 仅快速失败模式有值
     */
    private String paramName = "";

    /**
     * 未通过验证参数错误信息
     * 仅快速失败模式有值
     */
    private String paramErrorMsg = "";

    /**
     * 错误信息(仅全部校验模式有值)
     * 参数名:报错信息
     */
    private List<String> errorMessages;

    public static ValidResult success() {
        ValidResult result = new ValidResult();
        result.setSuccess(true);
        return result;
    }

    public static ValidResult fail(String paramName, String paramErrorMsg) {
        ValidResult result = new ValidResult();
        result.setSuccess(false);
        result.setParamName(paramName);
        result.setParamErrorMsg(paramErrorMsg);
        return result;
    }

    public static ValidResult fail(List<String> errorMessages) {
        ValidResult result = new ValidResult();
        result.setSuccess(false);
        result.setErrorMessages(errorMessages);
        return result;
    }

    /**
     * 获取完整报错信息
     * 仅快速失败模式
     * @return
     */
    public String getFailFastMsg() {
        return String.format("%s:%s", this.paramName, this.paramErrorMsg);
    }

    public void throwException() {
        if(!this.success) {
            throw new ValidationException(this.paramErrorMsg);
        }
    }
}
