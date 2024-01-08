package com.greathealth.greathealth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.greathealth.greathealth.domain.dto.SysUserDto;
import com.greathealth.greathealth.domain.entity.SysUserEntity;
import com.greathealth.greathealth.domain.vo.SysUserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SysUserPlusMapper extends BaseMapper<SysUserEntity> {

    List<SysUserVo> findByDeptIdAndKeys(List<String> ids, SysUserDto sysUserDto);
}
