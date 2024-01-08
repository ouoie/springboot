package com.greathealth.greathealth.framework.interceptor;

import com.alibaba.fastjson2.JSON;
import com.greathealth.greathealth.common.annotation.CanRepeatSubmit;
import com.greathealth.greathealth.common.utils.ServletUtils;
import com.greathealth.greathealth.common.core.domain.AjaxResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 防止重复提交拦截器
 *
 * @author
 */
@Component
public abstract class RepeatSubmitInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            PostMapping annotation = method.getAnnotation(PostMapping.class);
            CanRepeatSubmit canRepeatSubmit = method.getAnnotation(CanRepeatSubmit.class);
            if (annotation != null && canRepeatSubmit == null) {
                if (this.isRepeatSubmit(request)) {
                    AjaxResult ajaxResult = AjaxResult.error("不允许重复提交，请稍候再试");
                    ServletUtils.renderString(response, JSON.toJSONString(ajaxResult));
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 验证是否重复提交由子类实现具体的防重复提交的规则
     *
     * @param request
     * @return
     * @throws Exception
     */
    public abstract boolean isRepeatSubmit(HttpServletRequest request);
}
