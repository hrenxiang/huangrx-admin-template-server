package com.huangrx.template.security.config;

import com.huangrx.template.cache.RedisUtil;
import com.huangrx.template.security.service.SystemLoginService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

/**
 * 注入认证所需配置类
 * <br/>
 * 因为AuthenticationProvider中无法使用@Resource注入相关资源，需要构造器注入
 *
 * @author huangrx
 * @since 2023/4/26 11:42
 */
@Slf4j
@Getter
public class InjectionSourceConfig {

    /**
     * 密码加解密方式
     */
    private PasswordEncoder passwordEncoder;

    /**
     * 外部实现的认证 service
     */
    private SystemLoginService loginService;

    /**
     * 外部封装的 Redis操作类
     */
    private RedisUtil redisUtil;

    /**
     * 无参构造私有化，单例模式创建
     */
    private InjectionSourceConfig() {

    }

    /**
     * 内部私有静态类中进行初始化，并设置为静态类型
     */
    private static class InjectionSourceConfigHook {
        private static final InjectionSourceConfig INSTANCE = new InjectionSourceConfig();
    }

    /**
     * 提供公共静态获取方法，获取的是一个单例的对象
     *
     * @return InjectionSourceConfig
     */
    public static InjectionSourceConfig instance() {
        return InjectionSourceConfigHook.INSTANCE;
    }

    /**
     * 设置密码验证工具
     *
     * @param passwordEncoder 密码验证工具
     */
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        if (Objects.isNull(passwordEncoder)) {
            this.passwordEncoder = new BCryptPasswordEncoder();
            log.info("InjectionSourceConfig ------ 使用默认密码验证工具 BCryptPasswordEncoder");
        } else {
            this.passwordEncoder = passwordEncoder;
            log.info("InjectionSourceConfig ------ 使用配置的密码验证工具 BCryptPasswordEncoder");
        }
    }

    /**
     * 用户操作层注入
     */
    public void setLoginService(SystemLoginService loginService) {
        this.loginService = loginService;
        log.info("InjectionSourceConfig ------ 配置用户实现层 SystemLoginService");
    }

    /**
     * RedisService 注入
     */
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
        log.info("InjectionSourceConfig ------ 配置缓存实现层 RedisUtil");
    }
}
