package com.eco.common.utils.valid;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Administrator
 */
public class ValidationUtils {

    private static Validator fastFailValidator  = Validation.byProvider(HibernateValidator.class).configure()
            .failFast(true).buildValidatorFactory().getValidator();

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 注解验证参数
     * 快速失败模式
     * @param object
     * @param groups
     * @param <T>
     * @return
     */
    public static <T> ValidResult fastFailValidate(T object, Class<?>... groups) {
        if (groups == null) {
            groups = new Class[0];
        }
        Set<ConstraintViolation<T>> constraintViolations = fastFailValidator.validate(object, groups);
        if (CollectionUtils.isNotEmpty(constraintViolations)) {
            ConstraintViolation<T> constraintViolation = constraintViolations.iterator().next();
            return ValidResult.fail(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
        }
        return ValidResult.success();
    }

    /**
     * 注解验证参数
     * 全部校验
     * @param object
     */
    public static <T> ValidResult allCheckValidate(T object, boolean isShowField) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);
        //返回异常result
        return getAllCheckValidResult(isShowField, constraintViolations);
    }

    private static <T> ValidResult getAllCheckValidResult(boolean isShowField, Set<ConstraintViolation<T>> constraintViolations) {
        //返回异常result
        if (CollectionUtils.isNotEmpty(constraintViolations)) {
            List<String> errorMessages = new LinkedList<>();
            Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<T> violation = iterator.next();
                if(isShowField){
                    errorMessages.add(String.format("%s:%s", violation.getPropertyPath().toString(), violation.getMessage()));
                }else{
                    errorMessages.add(violation.getMessage());
                }
            }
            return ValidResult.fail(errorMessages);
        }
        return ValidResult.success();
    }
}
