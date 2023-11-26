package com.huangrx.template.security.config;

import cn.huangrx.blogserver.module.user.service.IUserService;
import cn.huangrx.blogserver.redis.service.RedisService;
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
public class InjectionSourceConfig {

    /**
     * 密码加解密方式
     */
    private PasswordEncoder passwordEncoder;

    /**
     * 外部实现的认证 service
     */
    private IUserService userService;

    /**
     * 外部封装的 Redis操作类
     */
    private RedisService redisService;

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
     * 获取配置的密码验证工具
     *
     * @return PasswordEncoder
     */
    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
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
     * 获取用户操作 Service
     *
     * @return IUserService
     */
    public IUserService getUserService() {
        return userService;
    }

    /**
     * 用户操作层注入
     */
    public void setUserService(IUserService userService) {
        this.userService = userService;
        log.info("InjectionSourceConfig ------ 配置用户实现层 IUserService");
    }

    /**
     * 获取Redis操作类
     *
     * @return Redis Bean
     */
    public RedisService getRedisService() {
        return redisService;
    }

    /**
     * RedisService 注入
     */
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
        log.info("InjectionSourceConfig ------ 配置缓存实现层 RedisService");
    }
}
