package com.greathealth.greathealth.common.core.domain.model;

/**
 * 用户登录对象
 *
 * @author
 */
public class XftLoginBody
{
    /**
     * 用户名
     */
    private String urlData;

    /**
     * 用户密码
     */
    private String urlSign;

    public String getUrlData() {
        return urlData;
    }

    public void setUrlData(String urlData) {
        this.urlData = urlData;
    }

    public String getUrlSign() {
        return urlSign;
    }

    public void setUrlSign(String urlSign) {
        this.urlSign = urlSign;
    }
}
