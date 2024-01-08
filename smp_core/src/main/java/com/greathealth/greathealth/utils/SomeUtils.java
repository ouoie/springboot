package com.greathealth.greathealth.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdcardUtil;
import com.greathealth.greathealth.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @author zjm
 * @description: 工具类
 * @date 2022/08/02
 * @time 15:13
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SomeUtils {

    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public <T, R> void asyncAddFlow(T entity, Function<T, R> function) {
        if (threadPoolTaskExecutor == null) {
            throw new RuntimeException("未配置线程池");
        }
        threadPoolTaskExecutor.execute(() -> function.apply(entity));
    }

    public static String getBirthByIdCard(String idCard) {
        DateTime birthday = IdcardUtil.getBirthDate(idCard);
        return DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,birthday);
    }

}

