package com.greathealth.greathealth.framework.interceptor.impl;

import com.alibaba.fastjson2.JSON;
import com.greathealth.greathealth.common.constant.Constants;
import com.greathealth.greathealth.common.core.redis.RedisCache;
import com.greathealth.greathealth.common.filter.RepeatedlyRequestWrapper;
import com.greathealth.greathealth.common.utils.StringUtils;
import com.greathealth.greathealth.common.utils.http.HttpHelper;
import com.greathealth.greathealth.framework.interceptor.RepeatSubmitInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 判断请求url和数据是否和上一次相同，
 * 如果和上次相同，则是重复提交表单。 有效时间为10秒内。
 *
 * @author
 */
@Slf4j
@Component
public class SameUrlDataInterceptor extends RepeatSubmitInterceptor {
    public final String REPEAT_PARAMS = "repeatParams";

    public final String REPEAT_TIME = "repeatTime";

    private final int repeatSubmitTime = 2000;

    // 令牌自定义标识
    @Value("${token.header}")
    private String header;

    @Autowired
    private RedisCache redisCache;

    @SuppressWarnings("unchecked")
    @Override
    public boolean isRepeatSubmit(HttpServletRequest request) {
        String nowParams = "";
        if (request instanceof RepeatedlyRequestWrapper) {
            RepeatedlyRequestWrapper repeatedlyRequest = (RepeatedlyRequestWrapper) request;
            nowParams = HttpHelper.getBodyString(repeatedlyRequest);
        }

/*        if (StringUtils.isEmpty(nowParams)) {
            HttpServletRequest requestCacheWrapperObject
                    = new ContentCachingRequestWrapper(request);
            nowParams = HttpHelper.getBodyString(requestCacheWrapperObject);
            log.info(nowParams);
        }*/


        // body参数为空，获取Parameter的数据
        if (StringUtils.isEmpty(nowParams)) {
            nowParams = JSON.toJSONString(request.getParameterMap());
        }
        Map<String, Object> nowDataMap = new HashMap<>();
        nowDataMap.put(REPEAT_PARAMS, nowParams);
        nowDataMap.put(REPEAT_TIME, System.currentTimeMillis());

        // 请求地址（作为存放cache的key值）
        String url = request.getRequestURI();

        // 唯一值（没有消息头则使用请求地址）
        String submitKey = StringUtils.trimToEmpty(request.getHeader(header));

        // 唯一标识（指定key + url + 消息头）
        String cacheRepeatKey = Constants.REPEAT_SUBMIT_KEY + url + submitKey;

        Object sessionObj = redisCache.getCacheObject(cacheRepeatKey);
        if (sessionObj != null) {
            Map<String, Object> sessionMap = (Map<String, Object>) sessionObj;
            if (sessionMap.containsKey(url)) {
                Map<String, Object> preDataMap = (Map<String, Object>) sessionMap.get(url);
                if (compareParams(nowDataMap, preDataMap) && compareTime(nowDataMap, preDataMap, repeatSubmitTime)) {
                    return true;
                }
            }
        }
        Map<String, Object> cacheMap = new HashMap<>();
        cacheMap.put(url, nowDataMap);
        redisCache.setCacheObject(cacheRepeatKey, cacheMap, repeatSubmitTime, TimeUnit.MILLISECONDS);
        return false;
    }

    /**
     * 判断参数是否相同
     */
    private boolean compareParams(Map<String, Object> nowMap, Map<String, Object> preMap) {
        String nowParams = (String) nowMap.get(REPEAT_PARAMS);
        String preParams = (String) preMap.get(REPEAT_PARAMS);
        return nowParams.equals(preParams);
    }

    /**
     * 判断两次间隔时间
     */
    private boolean compareTime(Map<String, Object> nowMap, Map<String, Object> preMap, int interval) {
        long time1 = (Long) nowMap.get(REPEAT_TIME);
        long time2 = (Long) preMap.get(REPEAT_TIME);
        return (time1 - time2) < interval;
    }
}
