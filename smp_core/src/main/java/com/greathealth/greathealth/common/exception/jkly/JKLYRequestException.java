package com.greathealth.greathealth.common.exception.jkly;

/**
 * @author zjm
 * @description: 健康洛阳请求异常
 * @date 2022/06/29
 * @time 15:20
 */
public class JKLYRequestException extends RuntimeException{

    public JKLYRequestException() {
        super();
    }

    public JKLYRequestException(String msg) {
        super(msg);
    }
}
