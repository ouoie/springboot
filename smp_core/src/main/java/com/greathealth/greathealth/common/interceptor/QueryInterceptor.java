package com.greathealth.greathealth.common.interceptor;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.greathealth.greathealth.utils.EncryptDecryptUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * 查询条件加密使用方式：使用 @Param("decrypt")注解的自定义类型
 * 返回结果解密使用方式： ①在自定义的DO上加上注解 CryptAnnotation  ②在需要加解密的字段属性上加上CryptAnnotation
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class})
})
public class QueryInterceptor implements Interceptor {
    /**
     * 查询参数名称，ParamMap的key值
     */
    private static final String DECRYPT = "decrypt";

    private final UpdateInterceptor updateInterceptor;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //获取查询参数，查询条件是否需要加密
        Object[] args = invocation.getArgs();
        Object parameter = args[1];
        Object result = null;
        //设置执行标识
        boolean flag = true;
        if (parameter instanceof MapperMethod.ParamMap) {
            Map paramMap = (Map) parameter;
            if (paramMap.containsKey(DECRYPT)) {
                Object queryParameter = paramMap.get(DECRYPT);
                if (EncryptDecryptUtils.needToCrypt(queryParameter)) {
                    //执行sql，还原加密后的报文
                    MappedStatement mappedStatement = (MappedStatement) args[0];
                    result = updateInterceptor.proceed(invocation, mappedStatement, queryParameter);
                    flag = false;
                }
            }
        }
        //是否需要执行
        if (flag) {
            result = invocation.proceed();
        }
        if (Objects.isNull(result)) {
            return null;
        }
        // 返回列表数据，循环检查
        if (result instanceof ArrayList) {
            ArrayList resultList = (ArrayList) result;
            if (CollectionUtils.isNotEmpty(resultList) && EncryptDecryptUtils.needToCrypt(resultList.get(0))) {
                for (Object o : resultList) {
                    EncryptDecryptUtils.decrypt(o);
                }
            }
        } else if (EncryptDecryptUtils.needToCrypt(result)) {
            EncryptDecryptUtils.decrypt(result);
        }
        //返回结果
        return result;
    }
}
