package com.greathealth.greathealth.domain.dto;

import com.greathealth.greathealth.annotation.Insert;
import com.greathealth.greathealth.annotation.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SysUserUpdateDto {
    /**
     * 用户ID
     */
    @NotNull(message = "id不能用空", groups = {Update.class})
    private String userId;
    /**
     * 科室ID
     */
    @NotBlank(message = "科室不能为空", groups = {Insert.class, Update.class})
    private String departmentId;
    /**
     * 用户账号
     */
    @NotBlank(message = "用户账号不能为空", groups = {Insert.class, Update.class})
    private String userName;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户类型（00系统用户）
     */
    private String userType;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String phonenumber;
    /**
     * 用户性别（0男；1女；2未知）
     */
    private String sex;
    /**
     * 头像地址
     */
    private String avatar;
    /**
     * 密码
     */
    private String password;
    /**
     * 帐号状态（0正常；1停用）
     */
    private String status;
    /**
     * 删除标志（0代表存在；2代表删除）
     */
    private String delFlag;
    /**
     * 备注
     */
    private String remark;
}
