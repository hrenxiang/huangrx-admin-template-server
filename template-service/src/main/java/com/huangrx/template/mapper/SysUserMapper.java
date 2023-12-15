package com.huangrx.template.mapper;

import com.huangrx.template.po.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huangrx.template.user.vo.UserVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author huangrx
 * @since 2023-11-26
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 加载用户ID为userId的用户
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserVO loadUserById(@Param("id") Long userId);
}
