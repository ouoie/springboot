package com.greathealth.greathealth.common.core.domain;

import com.github.pagehelper.PageInfo;
import com.greathealth.greathealth.common.constant.HttpStatus;
import com.greathealth.greathealth.common.exception.ServiceException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作消息提醒
 */
@Data
@ApiModel("基本后台响应容器类")
public class AjaxResult<T> {


    @ApiModelProperty("状态码")
    private Integer code;

    @ApiModelProperty("返回内容")
    private String msg;

    @ApiModelProperty("数据对象")
    private T data;


    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    public static final String CODE_TAG = "code";

    /**
     * 返回内容
     */
    public static final String MSG_TAG = "msg";

    /**
     * 数据对象
     */
    public static final String DATA_TAG = "data";

    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public AjaxResult() {
    }

    public AjaxResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static AjaxResult<?> success() {
        return AjaxResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static <T> AjaxResult<T> success(T data) {
        return AjaxResult.success("操作成功", data);
    }

    public static <T> AjaxPageResult<T> successPage(List<T> list) {
        AjaxPageResult<T> ajaxResult = new AjaxPageResult<>();
        ajaxResult.setRows(new ArrayList<>());
        ajaxResult.setTotal(0L);
        ajaxResult.setMsg("操作成功");
        ajaxResult.setCode(200);

        if (list != null) {
            ajaxResult.setRows(list);
            ajaxResult.setTotal(new PageInfo<>(list).getTotal());
        }
        return ajaxResult;
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static AjaxResult<?> success(String msg) {
        return AjaxResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static <T> AjaxResult<T> success(String msg, T data) {
        return new AjaxResult<>(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     */
    public static AjaxResult<?> error() {
        return AjaxResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResult<?> error(String msg) {
        return AjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static AjaxResult<?> error(String msg, Object data) {
        return new AjaxResult<>(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public static AjaxResult<?> error(int code, String msg) {
        return new AjaxResult<>(code, msg, null);
    }

    /**
     * 方便链式调用
     *
     * @param key   键
     * @param value 值
     * @return 数据对象
     */

    public AjaxMapResult put(String key, Object value) {
        if (!(this instanceof AjaxMapResult)) {
            throw new ServiceException("返回类型错误，请联系运维人员");
        }
        AjaxMapResult ajaxMapResult = (AjaxMapResult) this;
        int num = 0;

        if ("imgUrl".equals(key)) {
            ajaxMapResult.setImgUrl((String) value);
            num++;
        }

        if ("data".equals(key)) {
            ajaxMapResult.setData(value);
            num++;
        }
        if ("roleGroup".equals(key)) {
            ajaxMapResult.setRoleGroup((String) value);
            num++;
        }
        if ("postGroup".equals(key)) {
            ajaxMapResult.setPostGroup((String) value);
            num++;
        }
        if ("captchaOnOff".equals(key)) {
            ajaxMapResult.setCaptchaOnOff((Boolean) value);
            num++;
        }
        if ("uuid".equals(key)) {
            ajaxMapResult.setUuid((String) value);
            num++;
        }
        if ("img".equals(key)) {
            ajaxMapResult.setImg((String) value);
            num++;
        }
        if ("checkedKeys".equals(key)) {
            ajaxMapResult.setCheckedKeys(value);
            num++;
        }
        if ("menus".equals(key)) {
            ajaxMapResult.setMenus(value);
            num++;
        }
        if ("roles".equals(key)) {
            ajaxMapResult.setRoles(value);
            num++;
        }
        if ("posts".equals(key)) {
            ajaxMapResult.setPosts(value);
            num++;
        }
        if ("postIds".equals(key)) {
            ajaxMapResult.setPostIds(value);
            num++;
        }
        if ("roleIds".equals(key)) {
            ajaxMapResult.setRoleIds(value);
            num++;
        }
        if ("user".equals(key)) {
            ajaxMapResult.setUser(value);
            num++;
        }
        if ("token".equals(key)) {
            ajaxMapResult.setToken(value);
            num++;
        }
        if ("permissions".equals(key)) {
            ajaxMapResult.setPermissions(value);
            num++;
        }
        if ("url".equals(key)) {
            ajaxMapResult.setUrl(value);
            num++;
        }
        if ("fileName".equals(key)) {
            ajaxMapResult.setFileName(value);
            num++;
        }
        if ("newFileName".equals(key)) {
            ajaxMapResult.setNewFileName(value);
            num++;
        }
        if ("originalFilename".equals(key)) {
            ajaxMapResult.setOriginalFilename(value);
            num++;
        }

        if ("userId".equals(key)) {
            ajaxMapResult.setUserId(value);
            num++;
        }

        if ("userInfo".equals(key)) {
            ajaxMapResult.setUserInfo(value);
            num++;
        }

        if (num == 0) {
            throw new ServiceException("参数不存在，请联系管理员");
        }

        return ajaxMapResult;
    }


    public static AjaxResult<?> toAjax(int rows) {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }
}
