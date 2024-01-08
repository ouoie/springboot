package com.greathealth.greathealth.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.greathealth.greathealth.common.utils.SecurityUtils;
import com.greathealth.greathealth.domain.dto.SysUserUpdateDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_user")
public class SysUserEntity {
    /**
     * 用户ID
     */
   @TableId(type = IdType.ASSIGN_ID)
    private String userId;
    /**
     * 部门ID
     */
    private String deptId;
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
    private LocalDateTime loginDate;
    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    /**
     * 更新者
     */
    @TableField(fill = FieldFill.UPDATE)
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


    public static SysUserEntity fromSysUserUpdateDto(SysUserUpdateDto dto) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUserName(dto.getUserName());
        sysUserEntity.setNickName(dto.getNickName());
        sysUserEntity.setUserType("00");
        sysUserEntity.setEmail(dto.getEmail());
        sysUserEntity.setPhonenumber(dto.getPhonenumber());
        sysUserEntity.setSex(dto.getSex());
        sysUserEntity.setAvatar(dto.getAvatar());
        String password = "123456";
        sysUserEntity.setPassword(SecurityUtils.encryptPassword(password));
        sysUserEntity.setStatus("0");
        sysUserEntity.setDelFlag("0");
        sysUserEntity.setRemark(dto.getRemark());
        return sysUserEntity;
    }

    public static SysUserEntity fromSysUserUpdateDtoUpdate(SysUserUpdateDto dto) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUserName(dto.getUserName());
        sysUserEntity.setNickName(dto.getNickName());
        sysUserEntity.setUserType("00");
        sysUserEntity.setEmail(dto.getEmail());
        sysUserEntity.setPhonenumber(dto.getPhonenumber());
        sysUserEntity.setSex(dto.getSex());
        sysUserEntity.setAvatar(dto.getAvatar());
        sysUserEntity.setPassword(SecurityUtils.encryptPassword(dto.getPassword()));
        sysUserEntity.setStatus(dto.getStatus());
        sysUserEntity.setDelFlag(dto.getDelFlag());
        sysUserEntity.setRemark(dto.getRemark());
        return sysUserEntity;
    }

}
