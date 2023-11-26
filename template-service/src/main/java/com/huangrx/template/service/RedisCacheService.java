package com.huangrx.template.service;

import cn.hutool.extra.spring.SpringUtil;
import com.huangrx.template.po.SysPost;
import com.huangrx.template.po.SysRole;
import com.huangrx.template.po.SysUser;
import com.huangrx.template.redis.CacheKeyEnum;
import com.huangrx.template.redis.RedisCacheTemplate;
import com.huangrx.template.redis.RedisUtil;
import com.huangrx.template.security.entity.SecurityUser;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;


/**
 * 缓存接口实现类 （可设置三级缓存，添加guava）
 *
 * @author   huangrx
 * @since   2023-11-26 23:20
 */
@Component
@RequiredArgsConstructor
public class RedisCacheService {

    private final RedisUtil redisUtil;

    public RedisCacheTemplate<String> captchaCache;
    public RedisCacheTemplate<SecurityUser> loginUserCache;
    public RedisCacheTemplate<SysUser> userCache;
    public RedisCacheTemplate<SysRole> roleCache;
    public RedisCacheTemplate<SysPost> postCache;


    @PostConstruct
    public void init() {

        captchaCache = new RedisCacheTemplate<>(redisUtil, CacheKeyEnum.CAPTCHA);

        loginUserCache = new RedisCacheTemplate<>(redisUtil, CacheKeyEnum.LOGIN_USER_KEY);

        userCache = new RedisCacheTemplate<>(redisUtil, CacheKeyEnum.USER_ENTITY_KEY) {
            @Override
            public SysUser getObjectFromDb(Object id) {
                ISysUserService userService = SpringUtil.getBean(ISysUserService.class);
                return userService.getById((Serializable) id);
            }
        };

        roleCache = new RedisCacheTemplate<>(redisUtil, CacheKeyEnum.ROLE_ENTITY_KEY) {
            @Override
            public SysRole getObjectFromDb(Object id) {
                ISysRoleService roleService = SpringUtil.getBean(ISysRoleService.class);
                return roleService.getById((Serializable) id);
            }
        };

        postCache = new RedisCacheTemplate<>(redisUtil, CacheKeyEnum.POST_ENTITY_KEY) {
            @Override
            public SysPost getObjectFromDb(Object id) {
                ISysPostService postService = SpringUtil.getBean(ISysPostService.class);
                return postService.getById((Serializable) id);
            }

        };


    }


}
