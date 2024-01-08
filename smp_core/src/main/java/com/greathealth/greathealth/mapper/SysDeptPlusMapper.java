package com.greathealth.greathealth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.greathealth.greathealth.domain.entity.SysDeptEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SysDeptPlusMapper extends BaseMapper<SysDeptEntity> {

    Optional<SysDeptEntity> findByDeptTypeAndDepartmentId(String deptType, String departmentId);

    List<SysDeptEntity> findByParentId(String parentId);

     SysDeptEntity selectDeptById(String deptId);

     SysDeptEntity selectParentById(String parentId);

}
