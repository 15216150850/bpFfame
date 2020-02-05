package com.bp.common.utils;

import com.bp.common.exception.BpException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author 
 * @version 1.0
 * @Description: 数据校验
 * @date 2017年04月11日
 * hibernate-validator校验工具类
 *  参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 */
public class ValidatorUtils {

    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     * @param object        待校验对象
     * @throws BpException  校验不通过，则报BpException异常
     */
    public static void validateEntity(Object object)throws BpException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
        if (!constraintViolations.isEmpty()) {
        	ConstraintViolation<Object> constraint = constraintViolations.iterator().next();
            throw new BpException(constraint.getMessage());
        }
    }
}
