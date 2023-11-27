package com.huangrx.template.cache;

import java.util.concurrent.TimeUnit;

/**
 * Redis所有Keys
 *
 * @author huangrx
 * @since 2023-11-26 21:44
 */
public enum CacheKeyEnum {

    /**
     * Redis各类缓存集合
     */
    CAPTCHA("captcha_codes:", 2, TimeUnit.MINUTES),
    LOGIN_USER_KEY("login_tokens:", 30, TimeUnit.MINUTES),
    RATE_LIMIT_KEY("rate_limit:", 60, TimeUnit.SECONDS),
    USER_ENTITY_KEY("user_entity:", 60, TimeUnit.MINUTES),
    ROLE_ENTITY_KEY("role_entity:", 60, TimeUnit.MINUTES),
    POST_ENTITY_KEY("post_entity:", 60, TimeUnit.MINUTES),
    ROLE_MODEL_INFO_KEY("role_model_info:", 60, TimeUnit.MINUTES),

    ;


    CacheKeyEnum(String key, Integer expiration, TimeUnit timeUnit) {
        this.key = key;
        this.expiration = expiration;
        this.timeUnit = timeUnit;
    }

    private final String key;
    private final Integer expiration;
    private final TimeUnit timeUnit;

    public String key() {
        return key;
    }

    public Integer expiration() {
        return expiration;
    }

    public TimeUnit timeUnit() {
        return timeUnit;
    }

}
