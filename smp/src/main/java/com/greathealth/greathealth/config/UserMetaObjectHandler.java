package com.greathealth.greathealth.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.greathealth.greathealth.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserMetaObjectHandler implements MetaObjectHandler {

    private final UserServiceImpl userService;

    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            strictInsertFill(metaObject, "createBy", String.class, userService.getUserId());
            strictInsertFill(metaObject, "createdBy", String.class, userService.getUserId());

        } catch (Exception e) {
            log.error("更新createdBy失败  ", e);
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {

            strictUpdateFill(metaObject, "updatedBy", String.class, userService.getUserId());
            strictUpdateFill(metaObject, "updateBy", String.class, userService.getUserId());
        } catch (Exception e) {
            log.error("更新updatedBy失败  ", e);
        }

    }
}
