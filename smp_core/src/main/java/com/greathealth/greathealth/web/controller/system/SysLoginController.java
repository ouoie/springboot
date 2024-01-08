package com.greathealth.greathealth.web.controller.system;

import com.greathealth.greathealth.common.core.domain.entity.SysMenu;
import com.greathealth.greathealth.common.core.domain.entity.SysUser;
import com.greathealth.greathealth.common.core.domain.model.LoginBody;
import com.greathealth.greathealth.common.utils.SecurityUtils;
import com.greathealth.greathealth.common.constant.Constants;
import com.greathealth.greathealth.common.core.domain.AjaxMapResult;
import com.greathealth.greathealth.common.core.domain.AjaxResult;
import com.greathealth.greathealth.common.core.domain.entity.SysDeptVo;
import com.greathealth.greathealth.framework.web.service.SysLoginService;
import com.greathealth.greathealth.framework.web.service.SysPermissionService;
import com.greathealth.greathealth.system.domain.vo.SystemUserVo;
import com.greathealth.greathealth.system.mapper.SysDeptMapper;
import com.greathealth.greathealth.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 登录验证
 *
 * @author
 */
@RestController
public class SysLoginController {
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxMapResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    private Map<String, String> getDataParamMap(String dateStr) {
        String[] params = dateStr.split("\\|");
        Map<String, String> dataMap = new HashMap<String, String>();
        String[] var4 = params;
        int var5 = params.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String param = var4[var6];
            int index = param.indexOf(61);
            if (index > 0) {
                dataMap.put(param.substring(0, index), param.substring(index + 1));
            }
        }

        return dataMap;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxMapResult.success();

        SystemUserVo systemUserVo = SystemUserVo.fromSysUser(user);

        Optional<SysDeptVo> sysDept = sysDeptMapper.selectById(user.getDeptId());

        if (sysDept.isPresent()) {
            SysDeptVo sysDeptVo = sysDept.get();
            systemUserVo.setDeptType(sysDeptVo.getDeptType());
            systemUserVo.setOfficeName(sysDeptVo.getDeptName());
            systemUserVo.setOfficeId(sysDeptVo.getDepartmentId());

            if (Long.valueOf(3L).equals(sysDeptVo.getDeptType())) {
                systemUserVo.setDepartmentId(sysDeptVo.getDepartmentId());
                systemUserVo.setDeptName(sysDeptVo.getDeptName());
                Optional<SysDeptVo> parentDeptVo = sysDeptMapper.selectById(sysDeptVo.getParentId());
                parentDeptVo.ifPresent(deptVo -> {
                    systemUserVo.setHospitalId(deptVo.getDepartmentId());
                    systemUserVo.setHospitalName(deptVo.getDeptName());
                });
            }

            if (Long.valueOf(2L).equals(sysDeptVo.getDeptType())) {
                systemUserVo.setHospitalId(sysDeptVo.getDepartmentId());
                systemUserVo.setHospitalName(sysDeptVo.getDeptName());
            }
        }

        ajax.put("user", systemUserVo);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters() {
        String userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
