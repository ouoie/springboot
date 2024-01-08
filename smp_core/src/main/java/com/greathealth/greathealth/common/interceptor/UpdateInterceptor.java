package com.greathealth.greathealth.common.interceptor;

import com.baomidou.mybatisplus.annotation.TableId;
import com.greathealth.greathealth.utils.EncryptDecryptUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * 数据库更新操作拦截器
 * 一、支持的使用场景
 * ①场景一：通过mybatis-plus BaseMapper自动映射的方法
 * ②场景一：通过mapper接口自定义的方法，更新对象为自定义DO
 * 二、使用方法
 * ①在自定义的DO上加上注解 @Crypt
 * ②在需要加解密的字段属性上加上 @Crypt
 * ③自定义映射方法在需要加解密的自定义DO参数使用@Param("et")
 */
@Slf4j
@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class UpdateInterceptor implements Interceptor {

    /**
     * 更新参数名称，ParamMap的key值
     */
    private static final String CRYPT = "et";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //代理类方法参数，该拦截器拦截的update方法有两个参数args = {MappedStatement.class, Object.class}
        Object[] args = invocation.getArgs();
        //获取方法参数
        MappedStatement mappedStatement = (MappedStatement) args[0];
        Object parameter = args[1];
        if (Objects.isNull(parameter)) {
            //无参数，直接放行
            return invocation.proceed();
        }
        // 如果是多个参数或使用Param注解（Param注解会将参数放置在ParamMap中）
        if (parameter instanceof MapperMethod.ParamMap) {
            Map paramMap = (Map) parameter;
            if (paramMap.containsKey(CRYPT)) {
                Object updateParameter = paramMap.get(CRYPT);
                if (EncryptDecryptUtils.needToCrypt(updateParameter)) {
                    //执行sql，还原加解密后的报文
                    return proceed(invocation, mappedStatement, updateParameter);
                }
            }
        } else if (parameter instanceof DefaultSqlSession.StrictMap) {
            //不知道是啥意思，直接过
            return invocation.proceed();
        } else if (EncryptDecryptUtils.needToCrypt(parameter)) {
            //执行sql，还原加解密后的报文
            return proceed(invocation, mappedStatement, parameter);
        }
        //其他场景直接放行
        return invocation.proceed();
    }

    /**
     * 执行sql，还原加解密后的报文

     */
    public Object proceed(Invocation invocation, MappedStatement mappedStatement, Object parameter) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        //先复制一个对象备份数据
        Object newInstance = newInstance(parameter);
        //调用加解密服务
        EncryptDecryptUtils.encrypt(parameter);
        //执行操作，得到返回结果
        Object result = invocation.proceed();
        //把加解密后的字段还原
        reductionParameter(mappedStatement, newInstance, parameter);
        //返回结果
        return result;
    }

    /**
     * 先复制一个对象备份数据,便于加解密后还原原报文
     */
    private Object newInstance(Object parameter) throws IllegalAccessException, InstantiationException {
        Object newInstance = parameter.getClass().newInstance();
        BeanUtils.copyProperties(parameter, newInstance);
        return newInstance;
    }

    /**
     * 把加解密后的字段还原，同时把mybatis返回的tableId返回给参数对象
     *
     */
    private void reductionParameter(MappedStatement mappedStatement, Object newInstance, Object parameter) throws IllegalAccessException {
        //获取映射语句命令类型
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        if (SqlCommandType.INSERT == sqlCommandType) {
            //从参数属性中找到注解是TableId的字段
            Field[] parameterFields = parameter.getClass().getDeclaredFields();
            Optional<Field> optional = Arrays.stream(parameterFields).filter(field -> field.isAnnotationPresent(TableId.class)).findAny();
            if (optional.isPresent()) {
                Field field = optional.get();
                field.setAccessible(true);
                Object id = field.get(parameter);
                //覆盖参数加解密的值
                BeanUtils.copyProperties(newInstance, parameter);
                field.set(parameter, id);
            } else {
                //覆盖参数加解密的值
                BeanUtils.copyProperties(newInstance, parameter);
            }
        } else {
            //覆盖参数加解密的值
            BeanUtils.copyProperties(newInstance, parameter);
        }
    }


}
