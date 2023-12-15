package com.huangrx.template.service;

import com.huangrx.template.core.base.IBaseService;
import com.huangrx.template.dto.AddUserDTO;
import com.huangrx.template.po.SysUser;
import com.huangrx.template.user.vo.UserVO;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author huangrx
 * @since 2023-11-26
 */
public interface ISysUserService extends IBaseService<SysUser> {

    /**
     * 注册新用户。
     *
     * @param request 包含用户详细信息的 AddUserDTO 请求。
     */
    void register(AddUserDTO request);


    /**
     * 根据手机号加载用户信息。
     *
     * @param phoneNumber 手机号
     * @return 用户信息
     */
    SysUser loadUserByPhoneNumber(String phoneNumber);

    /**
     * 加载用户ID为userId的用户
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserVO loadUserById(Long userId);
}
