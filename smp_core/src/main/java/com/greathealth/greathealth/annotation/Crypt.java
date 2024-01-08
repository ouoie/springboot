package com.greathealth.greathealth.annotation;

import java.lang.annotation.*;

/**
 * @author zjm
 * @description: 数据库字段加密类注解
 * @date 2022/10/09
 * @time 9:17
 */
@Documented
@Inherited
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Crypt {

}
