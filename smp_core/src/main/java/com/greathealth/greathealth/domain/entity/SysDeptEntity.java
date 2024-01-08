package com.greathealth.greathealth.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_dept")
public class SysDeptEntity {
    /**
     * 部门id
     */
   @TableId(type = IdType.ASSIGN_ID)
    private String deptId;
    /**
     * 父部门id
     */
    private String parentId;
    /**
     * 祖级列表
     */
    private String ancestors;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 显示顺序
     */
    private Integer orderNum;
    /**
     * 负责人
     */
    private String leader;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 部门状态（0正常;1停用）
     */
    private String status;
    /**
     * 删除标志（0代表存在;2代表删除）
     */
    private String delFlag;
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
     * 部门类型（1：卫健委，2：医院；3：科室）
     */
    private String deptType;
    /**
     * 医院等部门的对应的主键id
     */
    private String departmentId;

}
