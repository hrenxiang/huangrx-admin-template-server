package com.huangrx.template.cache;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * 缓存接口实现类 （添加一层guava可构成三级缓存，一级redis，二级guava，三级数据库）
 *
 * @author valarchie
 */
@Slf4j
public class CacheTemplate<T> {

    @Resource
    private RedisUtil redisUtil;

    private final CacheKeyEnum redisRedisEnum;

    public CacheTemplate(CacheKeyEnum redisRedisEnum) {
        this.redisRedisEnum = redisRedisEnum;
    }

    /**
     * 从缓存中获取对象   如果获取不到的话  从DB层面获取
     *
     * @param id id
     */
    public T getObjectById(Object id) {
        String cachedKey = generateKey(id);
        Optional<Object> obj = Optional.ofNullable(redisUtil.get(cachedKey));
        if (obj.isPresent()) {
            T objectFromDb = getObjectFromDb(id);
            set(id, objectFromDb);
            return objectFromDb;
        } else {
            log.error("从缓存中获取对象失败");
            return null;
        }
    }

    /**
     * 从缓存中获取 对象， 即使找不到的话 也不从DB中找
     *
     * @param id id
     */
    @SuppressWarnings(value = "unchecked")
    public T getObjectOnlyInCacheById(Object id) {
        String cachedKey = generateKey(id);
        Optional<T> optional = Optional.ofNullable((T) redisUtil.get(cachedKey));
        return optional.orElse(null);
    }

    /**
     * 从缓存中获取 对象， 即使找不到的话 也不从DB中找
     *
     * @param cachedKey 直接通过redis的key来搜索
     */
    @SuppressWarnings(value = "unchecked")
    public T getObjectOnlyInCacheByKey(String cachedKey) {
        Optional<T> optional = Optional.ofNullable((T) redisUtil.get(cachedKey));
        return optional.orElse(null);
    }

    public void set(Object id, T obj) {
        redisUtil.set(generateKey(id), obj, redisRedisEnum.expiration(), redisRedisEnum.timeUnit());
    }

    public void del(Object id) {
        redisUtil.del(generateKey(id));
    }

    public void refresh(Object id) {
        redisUtil.expire(generateKey(id), redisRedisEnum.expiration(), redisRedisEnum.timeUnit());
    }

    public String generateKey(Object id) {
        return redisRedisEnum.key() + id;
    }

    /**
     * 从数据库中获取对象的抽象方法
     *
     * @param id id标识
     * @return <T> 泛型
     */
    public T getObjectFromDb(Object id) {
        return null;
    }
}
