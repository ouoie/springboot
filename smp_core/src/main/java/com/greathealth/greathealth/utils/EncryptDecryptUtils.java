package com.greathealth.greathealth.utils;

import cn.hutool.crypto.SecureUtil;
import com.github.xiaoymin.knife4j.core.util.AnnotationUtils;
import com.greathealth.greathealth.annotation.Crypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @author zjm
 * @description: 数据加密工具类
 * @date 2022/10/09
 * @time 9:20
 */
@Slf4j
public class EncryptDecryptUtils {

    /**
     * 线上运行后勿修改，会影响已加密数据解密
     */
    private static final byte[] KEYS = "A255C9698B796FCF".getBytes(StandardCharsets.UTF_8);


    /**
     * 多field加密方法
     */
    public static <T> T encrypt(Field[] declaredFields, T parameterObject) throws IllegalAccessException {
        for (Field field : declaredFields) {
            Crypt annotation = field.getAnnotation(Crypt.class);
            if (Objects.isNull(annotation)) {
                continue;
            }
            encrypt(field, parameterObject);
        }
        return parameterObject;
    }


    /**
     * 单个field加密方法
     */
    public static <T> T encrypt(Field field, T parameterObject) throws IllegalAccessException {
        field.setAccessible(true);
        Object object = field.get(parameterObject);
        if (object instanceof BigDecimal) {
            log.info("不支持BigDecimal类型");
        } else if (object instanceof Integer) {
            log.info("不支持Integer类型");
        } else if (object instanceof Long) {
            log.info("不支持Long类型");
        } else if (object instanceof String) {
            //定制String类型的加密算法
            String value = (String) object;
            field.set(parameterObject, SecureUtil.aes(KEYS).encryptBase64(value));
        }
        return parameterObject;
    }

    /**
     * 解密方法
     */
    public static <T> T decrypt(T result) throws IllegalAccessException {
        Class<?> parameterObjectClass = result.getClass();
        Field[] declaredFields = parameterObjectClass.getDeclaredFields();
        decrypt(declaredFields, result);
        return result;
    }

    /**
     * 多个field解密方法
     */
    public static void decrypt(Field[] declaredFields, Object result) throws IllegalAccessException {
        for (Field field : declaredFields) {
            Crypt annotation = field.getAnnotation(Crypt.class);
            if (Objects.isNull(annotation)) {
                continue;
            }
            decrypt(field, result);
        }
    }

    /**
     * 单个field解密方法
     */
    public static void decrypt(Field field, Object result) throws IllegalAccessException {
        field.setAccessible(true);
        Object object = field.get(result);
        if (object instanceof BigDecimal) {
            log.info("不支持BigDecimal类型");
        } else if (object instanceof Integer) {
            log.info("不支持Integer类型");
        } else if (object instanceof Long) {
            log.info("不支持Long类型");
        } else if (object instanceof String) {
            //定制String类型的解密算法
            String value = (String) object;
            if (StringUtils.hasText(value)) {
                field.set(result, SecureUtil.aes(KEYS).decryptStr(value));
            }
        }
    }


    public static String decrypt(String value) {
        return SecureUtil.aes(KEYS).decryptStr(value);
    }

    public static String encrypt(String value) {
        return SecureUtil.aes(KEYS).encryptBase64(value);
    }

    public static void encrypt(Object parameterObject) throws IllegalAccessException {
        if (Objects.nonNull(parameterObject)) {
            Class<?> parameterObjectClass = parameterObject.getClass();
            Optional<Crypt> annotation = AnnotationUtils.findAnnotation(parameterObjectClass, Crypt.class);
            if (annotation.isPresent()) {
                Field[] declaredFields = parameterObjectClass.getDeclaredFields();
                encrypt(declaredFields, parameterObject);
            }
        }
    }


    /**
     * 是否需要加解密：
     * ①是否属于基本类型，void类型和String类型，如果是，不加解密
     * ②DO上是否有注解 ③ 属性是否有注解
     */

    public static boolean needToCrypt(Object object) {
        if (object == null) {
            return false;
        }
        Class<?> clazz = object.getClass();
        if (clazz.isPrimitive() || object instanceof String) {
            //基本类型和字符串不加解密
            return false;
        }
        //获取DO注解
        boolean annotationPresent = clazz.isAnnotationPresent(Crypt.class);
        if (!annotationPresent) {
            //无DO注解不加解密
            return false;
        }
        //获取属性注解
        Field[] fields = clazz.getDeclaredFields();
        return Arrays.stream(fields).anyMatch(field -> field.isAnnotationPresent(Crypt.class));
    }

}
