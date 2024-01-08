package com.greathealth.greathealth.system.domain.vo;

import com.greathealth.greathealth.common.core.domain.entity.SysUser;
import lombok.Getter;
import lombok.Setter;

public class SystemUserVo extends SysUser {

    @Setter
    @Getter
    private String deptName;

    @Setter
    @Getter
    private Long deptType;
    @Setter
    @Getter
    private String departmentId;
    @Setter
    @Getter
    private String officeId;
    @Setter
    @Getter
    private String officeName;
    @Setter
    @Getter
    private String hospitalId;
    @Setter
    @Getter
    private String hospitalName;


    public static SystemUserVo fromSysUser(SysUser user) {
        SystemUserVo sysTemUserVo = new SystemUserVo();
        sysTemUserVo.setUserId(user.getUserId());
        sysTemUserVo.setDeptId(user.getDeptId());
        sysTemUserVo.setNickName(user.getNickName());
        sysTemUserVo.setUserName(user.getUserName());
        sysTemUserVo.setEmail(user.getEmail());
        sysTemUserVo.setPhonenumber(user.getPhonenumber());
        sysTemUserVo.setSex(user.getSex());
        sysTemUserVo.setAvatar(user.getAvatar());
        sysTemUserVo.setPassword(user.getPassword());
        sysTemUserVo.setSalt(user.getSalt());
        sysTemUserVo.setStatus(user.getStatus());
        sysTemUserVo.setDelFlag(user.getDelFlag());
        sysTemUserVo.setLoginIp(user.getLoginIp());
        sysTemUserVo.setLoginDate(user.getLoginDate());
        sysTemUserVo.setDept(user.getDept());
        sysTemUserVo.setRoles(user.getRoles());
        sysTemUserVo.setRoleIds(user.getRoleIds());
        sysTemUserVo.setPostIds(user.getPostIds());
        sysTemUserVo.setRoleId(user.getRoleId());
        sysTemUserVo.setSearchValue(user.getSearchValue());
        sysTemUserVo.setCreateBy(user.getCreateBy());
        sysTemUserVo.setCreateTime(user.getCreateTime());
        sysTemUserVo.setUpdateBy(user.getUpdateBy());
        sysTemUserVo.setUpdateTime(user.getUpdateTime());
        sysTemUserVo.setRemark(user.getRemark());
        sysTemUserVo.setParams(user.getParams());
        return sysTemUserVo;
    }
}
