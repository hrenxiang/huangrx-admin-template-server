package com.huangrx.template.service.impl;

import com.huangrx.template.po.SysUser;
import com.huangrx.template.mapper.SysUserMapper;
import com.huangrx.template.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
