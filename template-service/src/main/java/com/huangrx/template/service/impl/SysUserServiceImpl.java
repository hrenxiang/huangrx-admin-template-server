package com.huangrx.template.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huangrx.template.constant.Constants;
import com.huangrx.template.convert.UserConvert;
import com.huangrx.template.dto.AddUserDTO;
import com.huangrx.template.exception.ApiException;
import com.huangrx.template.exception.error.ErrorCode;
import com.huangrx.template.mapper.SysUserMapper;
import com.huangrx.template.po.SysUser;
import com.huangrx.template.security.config.InjectionSourceConfig;
import com.huangrx.template.service.ISysPostService;
import com.huangrx.template.service.ISysRoleService;
import com.huangrx.template.service.ISysUserService;
import com.huangrx.template.user.vo.UserVO;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author huangrx
 * @since 2023-11-26
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Resource
    private ISysRoleService roleService;

    @Resource
    private ISysPostService postService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(AddUserDTO request) {
        // 1、唯一号码判断用户是否存在
        SysUser entity = this.loadUserByPhoneNumber(request.getPhoneNumber());
        if (Objects.nonNull(entity)) {
            throw new ApiException(ErrorCode.Business.REGISTER_USER_EXIST);
        }

        // 2、判断角色和岗位是否存在
        roleService.isPresent(request.getRoleId());
        postService.isPresent(request.getPostId());

        // 3、添加用户信息(密码加密)
        SysUser user = UserConvert.INSTANCE.toUser(request);
        PasswordEncoder passwordEncoder = InjectionSourceConfig.instance().getPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.save(user);
    }

    @Override
    public SysUser loadUserByPhoneNumber(String phoneNumber) {
        return this.getOne(
                Wrappers.<SysUser>lambdaQuery()
                        .eq(SysUser::getPhoneNumber, phoneNumber)
        );
    }

    @Override
    public UserVO loadUserById(Long userId) {
        return baseMapper.loadUserById(userId);
    }

    @Override
    public void isPresent(Long id) {
        SysUser user = this.getById(id);
        if (Optional.ofNullable(user).isEmpty()) {
            throw new ApiException(ErrorCode.Business.USER_NON_EXIST);
        }
    }
}
