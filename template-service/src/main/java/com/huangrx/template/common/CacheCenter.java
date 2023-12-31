package com.huangrx.template.common;

import cn.hutool.extra.spring.SpringUtil;
import com.huangrx.template.cache.CacheKeyEnum;
import com.huangrx.template.cache.CacheTemplate;
import com.huangrx.template.user.base.LoginUser;
import com.huangrx.template.po.SysPost;
import com.huangrx.template.po.SysRole;
import com.huangrx.template.po.SysUser;
import com.huangrx.template.service.ISysPostService;
import com.huangrx.template.service.ISysRoleService;
import com.huangrx.template.service.ISysUserService;
import org.springframework.stereotype.Component;

import java.io.Serializable;


/**
 * 缓存中心  提供Service层及以下访问点
 * 如果是领域类的缓存  可以自己新建一个直接放在CacheCenter
 * 不用放在infrastructure包里的GuavaCacheService
 * 或者RedisCacheService
 *
 * @author huangrx
 * @since 2023/11/27 11:50
 */
@Component
public class CacheCenter {

    private CacheCenter() {

    }

    public static final CacheTemplate<String> CAPTCHA_CACHE = new CacheTemplate<>(CacheKeyEnum.CAPTCHA);

    public static final CacheTemplate<LoginUser> LOGIN_USER_CACHE = new CacheTemplate<>(CacheKeyEnum.LOGIN_USER_KEY);

    public static final CacheTemplate<SysUser> USER_CACHE = new CacheTemplate<>(CacheKeyEnum.USER_ENTITY_KEY) {
        @Override
        public SysUser getObjectFromDb(Object id) {
            return SpringUtil.getBean(ISysUserService.class).getById((Serializable) id);
        }
    };

    public static final CacheTemplate<SysRole> ROLE_CACHE = new CacheTemplate<>(CacheKeyEnum.ROLE_ENTITY_KEY) {
        @Override
        public SysRole getObjectFromDb(Object id) {
            return SpringUtil.getBean(ISysRoleService.class).getById((Serializable) id);
        }
    };

    public static final CacheTemplate<SysPost> POST_CACHE = new CacheTemplate<>(CacheKeyEnum.POST_ENTITY_KEY) {
        @Override
        public SysPost getObjectFromDb(Object id) {
            return SpringUtil.getBean(ISysPostService.class).getById((Serializable) id);
        }

    };

}
