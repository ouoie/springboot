package com.greathealth.greathealth.domain.dto;

import lombok.Data;

@Data
public class SysUserDto {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 部门ID
     */
    private Long deptId;
    /**
     * 用户账号
     */
    private String userName;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户性别（0男；1女；2未知）
     */
    private String sex;
    /**
     * 帐号状态（0正常；1停用）
     */
    private String status;
    /**
     * 删除标志（0代表存在；2代表删除）
     */
    private String delFlag;

}
