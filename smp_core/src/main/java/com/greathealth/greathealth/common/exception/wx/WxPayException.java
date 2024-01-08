package com.greathealth.greathealth.common.exception.wx;

/**
 * @author zjm
 * @description: 微信支付请求异常
 * @date 2022/06/29
 * @time 15:20
 */
public class WxPayException extends RuntimeException{

    public WxPayException() {
        super();
    }

    public WxPayException(String msg) {
        super(msg);
    }
}
