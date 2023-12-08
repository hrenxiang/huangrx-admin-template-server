package com.huangrx.template.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huangrx.template.core.base.IBaseService;
import com.huangrx.template.exception.ApiException;
import com.huangrx.template.exception.error.ErrorCode;
import com.huangrx.template.mapper.SysRoleMapper;
import com.huangrx.template.po.SysRole;
import com.huangrx.template.service.ISysRoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author huangrx
 * @since 2023-11-26
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Override
    public void isPresent(Long roleId) {
        SysRole role = this.getById(roleId);
        if (Optional.ofNullable(role).isEmpty()) {
            throw new ApiException(ErrorCode.Business.POST_NON_EXIST);
        }
    }
}
