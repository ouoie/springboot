package com.greathealth.greathealth.utils;

import com.alibaba.fastjson2.JSON;
import com.greathealth.greathealth.common.exception.ServiceException;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author zjm
 * @description: 参数校验工具类
 * @date 2022/08/02
 * @time 15:13
 */
public class ValidationUtil {
    /**
     * 开启快速结束模式 failFast (true)
     */
    private static Validator failFastValidator = Validation.byProvider(HibernateValidator.class)
            .configure()
            .failFast(true)
            .buildValidatorFactory().getValidator();

    /**
     * 全部校验
     */
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    private ValidationUtil() {
    }

    /**
     * 注解验证参数(快速失败模式)
     *
     * @param obj
     */
    public static <T> void fastFailValidate(T obj) {
        Set<ConstraintViolation<T>> constraintViolations = failFastValidator.validate(obj);
        //返回异常result
        if (constraintViolations.size() > 0) {
            ConstraintViolation<T> violation = constraintViolations.iterator().next();
            throw new ServiceException(String.format("%s:%s", violation.getPropertyPath().toString(), violation.getMessage()));
        }
    }

    /**
     * 注解验证参数(全部校验)
     *
     * @param obj
     */
    public static <T> void allCheckValidate(T obj) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
        //返回异常result
        if (constraintViolations.size() > 0) {
            List<String> errorMessages = new LinkedList<String>();
            Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<T> violation = iterator.next();
                //errorMessages.add(String.format("%s:%s", violation.getPropertyPath().toString(), violation.getMessage()));
                errorMessages.add(String.format("%s:%s", violation.getMessage(), JSON.toJSONString(violation.getInvalidValue())));
            }
            throw new ServiceException(JSON.toJSONString(errorMessages));
        }
    }
}

