package com.greathealth.greathealth.service.impl;


import com.greathealth.greathealth.common.core.domain.entity.SysDept;
import com.greathealth.greathealth.common.core.domain.model.LoginUser;
import com.greathealth.greathealth.common.utils.SecurityUtils;

import com.greathealth.greathealth.domain.entity.SysDeptEntity;
import com.greathealth.greathealth.system.mapper.SysDeptMapper;

import com.greathealth.greathealth.mapper.SysDeptPlusMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserUtilServiceImpl {


    private final SysDeptMapper sysDeptMapper;

    private final SysDeptPlusMapper sysDeptPlusMapper;


    public List<SysDept> getSubHospital() {
        String deptId = getDeptId();
        return sysDeptMapper.selectChildrenDeptByParentId(deptId);
    }

    public SysDeptEntity getDeptEntity() {
        return sysDeptPlusMapper.selectById(getDeptId());
    }

    public String getDeptType() {
        return getDeptEntity().getDeptType();
    }

    /**
     * 用户ID
     **/
    public String getUserId() {
        return SecurityUtils.getUserId();
    }

    /**
     * 获取部门ID
     **/
    public String getDeptId() {
        return SecurityUtils.getDeptId();

    }

    /**
     * 获取用户账户
     **/
    public String getUsername() {
        return SecurityUtils.getUsername();
    }

    /**
     * 获取用户
     **/
    public LoginUser getLoginUser() {
        return SecurityUtils.getLoginUser();
    }


    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public String encryptPassword(String password) {
        return SecurityUtils.encryptPassword(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public boolean matchesPassword(String rawPassword, String encodedPassword) {
        return SecurityUtils.matchesPassword(rawPassword, encodedPassword);
    }

    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public boolean isAdmin(String userId) {
        return SecurityUtils.isAdmin(userId);
    }

    /**
     * 获取医院底下的科室
     * @return
     */
    public List<SysDept> getSubHosDepartment() {
        String deptId = getDeptId();
        return sysDeptMapper.selectChildDepartmentByParentId(deptId);
    }
}
