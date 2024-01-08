package com.greathealth.greathealth.framework.web.exception;

import com.alibaba.fastjson2.JSON;
import com.github.pagehelper.Page;
import com.greathealth.greathealth.common.constant.HttpStatus;
import com.greathealth.greathealth.common.core.domain.AjaxPageResult;
import com.greathealth.greathealth.common.core.page.TableDataInfo;
import com.greathealth.greathealth.common.exception.DemoModeException;
import com.greathealth.greathealth.common.exception.jkly.JKLYRequestException;
import com.greathealth.greathealth.common.utils.StringUtils;
import com.greathealth.greathealth.domain.vo.GeocoderVo;
import com.greathealth.greathealth.domain.vo.MapVo;
import com.greathealth.greathealth.domain.vo.SearchMapVo;
import com.greathealth.greathealth.common.core.domain.AjaxResult;
import com.greathealth.greathealth.common.exception.ServiceException;
import com.greathealth.greathealth.common.exception.wx.WxPayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler implements ResponseBodyAdvice<Object> {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 权限校验异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public AjaxResult<?> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',权限校验失败'{}'", requestURI, e.getMessage());
        return AjaxResult.error(HttpStatus.FORBIDDEN, "没有权限，请联系管理员授权");
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public AjaxResult<?> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                             HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public AjaxResult<?> handleServiceException(ServiceException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        Integer code = e.getCode();
        return StringUtils.isNotNull(code) ? AjaxResult.error(code, e.getMessage()) : AjaxResult.error(e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public AjaxResult<?> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult<?> handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestURI, e);
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public AjaxResult<?> handleBindException(BindException e) {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return AjaxResult.error(message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        String message = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        return AjaxResult.error(message);
    }

    /**
     * 演示模式异常
     */
    @ExceptionHandler(DemoModeException.class)
    public AjaxResult<?> handleDemoModeException(DemoModeException e) {
        return AjaxResult.error("演示模式，不允许操作");
    }

    /**
     * 健康洛阳异常
     */
    @ExceptionHandler(JKLYRequestException.class)
    public AjaxResult<?> handleJKLYRequestException(JKLYRequestException e) {
        log.error("健康洛阳具体异常信息：", e);
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 微信支付异常
     */
    @ExceptionHandler(WxPayException.class)
    public AjaxResult<?> handleWxPayException(WxPayException e) {
        log.error("微信支付具体异常信息：", e);
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 微信支付异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public AjaxResult<?> handleWxPayException(ConstraintViolationException e) {
        log.error("参数校验异常信息：", e);
        return AjaxResult.error(new String(JSON.toJSONString(e.getMessage()).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof AjaxResult || body instanceof AjaxPageResult ||
                body instanceof MapVo || body instanceof SearchMapVo || body instanceof GeocoderVo ||
                body instanceof TableDataInfo || body instanceof ResponseEntity || body instanceof String) {
            return body;
        }

        URI uri = request.getURI();
        String path = uri.getPath();
        if (path.contains("/swagger-resources") || path.contains("/swagger-ui.html") || path.contains("api-docs") ||
                path.contains("druid") || path.contains("webjars") || path.contains(".html") || path.contains(".css") ||
                path.contains(".js")) {
            return body;
        }

        if (body instanceof Page) {
            return AjaxResult.successPage((List) body);
        }

        return AjaxResult.success(body);

    }
}
