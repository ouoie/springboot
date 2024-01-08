package com.greathealth.greathealth.common.core.domain.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 部门表 sys_dept
 */
public class SysDeptVo extends SysDept {

    @Setter
    @Getter
    private Long deptType;
    @Setter
    @Getter
    private String departmentId;

}
