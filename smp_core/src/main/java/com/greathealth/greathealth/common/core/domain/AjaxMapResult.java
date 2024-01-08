package com.greathealth.greathealth.common.core.domain;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AjaxMapResult extends AjaxResult {

    public static AjaxResult<?> success() {
        AjaxMapResult ajaxMapResult = new AjaxMapResult();
        ajaxMapResult.setMsg("操作成功");
        ajaxMapResult.setCode(200);
        return ajaxMapResult;
    }

    @ApiModelProperty("状态码")
    private Integer code;

    @ApiModelProperty("返回内容")
    private String msg;

    private String imgUrl;

    private String roleGroup;

    private String postGroup;

    private boolean captchaOnOff;

    private String uuid;

    private String img;

    private Object checkedKeys;

    private Object menus;

    private Object roles;

    private Object posts;

    private Object postIds;

    private Object roleIds;

    private Object user;

    private Object token;

    private Object permissions;

    private Object depts;

    private Object url;

    private Object fileName;

    private Object newFileName;

    private Object originalFilename;

    private Object userId;

    private Object userInfo;

}
