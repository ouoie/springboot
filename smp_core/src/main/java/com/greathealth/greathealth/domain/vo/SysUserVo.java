package com.greathealth.greathealth.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysUserVo {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 部门ID
     */
    private String deptId;
    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 用户账号
     */
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
     * 最后登录IP
     */
    private String loginIp;
    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime loginDate;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 部门类型（1：卫健委，2：医院；3：科室）
     */
    private Long deptType;
    /**
     * 医院等部门的对应的主键id
     */
    private String departmentId;
}
