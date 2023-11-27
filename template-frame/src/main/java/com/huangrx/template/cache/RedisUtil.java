package com.huangrx.template.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * Redis 工具类
 *
 * @author huangrx
 * @since 2023-11-26 19:36
 */
@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    // =============================String=================================

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    public void set(final String key, final Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    public void set(final String key, final Object value, final Integer timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public Object get(final String key) {
        ValueOperations<String, Object> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key 键
     */
    public void del(final String key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     *
     * @param keys 多个key
     */
    public Long del(final Collection<String> keys) {
        return redisTemplate.delete(keys);
    }


    // ================================List=================================

    /**
     * 缓存List数据
     *
     * @param key   缓存的键值
     * @param value 待缓存的数据
     * @return 缓存的对象
     */
    public Long lPush(final String key, final Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 缓存List数据
     *
     * @param key     缓存的键值
     * @param value   待缓存的数据
     * @param timeout 超时时间
     * @return 缓存的对象
     */
    public Long lPush(final String key, final Object value, final Integer timeout) {
        Long count = redisTemplate.opsForList().rightPush(key, value);
        expire(key, timeout);
        return count == null ? 0L : count;
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public Long lPushAll(final String key, final List<Object> dataList) {
        return redisTemplate.opsForList().rightPushAll(key, dataList);
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @param timeout  超时时间
     * @return 缓存的对象
     */
    public Long lPushAll(final String key, final List<Object> dataList, final Integer timeout) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        expire(key, timeout);
        return count == null ? 0L : count;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public List<Object> lRange(final String key) {
        BoundListOperations<String, Object> boundListOperations = redisTemplate.boundListOps(key);
        return boundListOperations.range(0, -1);
    }

    /**
     * 获取缓存列表的大小
     *
     * @param key 缓存键
     * @return 缓存列表的大小
     */
    public Long lSize(final String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 从缓存列表中根据索引获取元素
     *
     * @param key   缓存键
     * @param index 索引位置
     * @return 索引位置上的元素
     */
    public Object lIndex(final String key, final Long index) {
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        return listOperations.index(key, index);
    }

    /**
     * 从 Redis 缓存列表中删除指定元素。
     *
     * @param key   缓存列表的键
     * @param count 删除的数量
     * @param value 要删除的元素
     * @return 成功删除的元素数量
     */
    public Long lRemove(final String key, final Long count, final Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    // ===============================Set=================================

    /**
     * 从 Redis 缓存中获取指定键对应的集合的所有成员。
     *
     * @param key 缓存键
     * @return 包含集合所有成员的 Set 对象
     */
    public Set<Object> sMembers(final String key) {
        BoundSetOperations<String, Object> boundSetOperations = redisTemplate.boundSetOps(key);
        return boundSetOperations.members();
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public final Long sAdd(final String key, final Set<Object>[] dataSet) {
        Long count = redisTemplate.opsForSet().add(key, dataSet);
        return count == null ? 0 : count;
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param timeout 超时时间
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public Long sAdd(final String key, final Integer timeout, final Set<Object>[] dataSet) {
        Long count = redisTemplate.opsForSet().add(key, dataSet);
        expire(key, timeout);
        return count == null ? 0 : count;
    }

    /**
     * 判断给定的值是否是集合中的成员。
     *
     * @param key   缓存键
     * @param value 缓存值
     * @return true=是集合中的成员；false=不是集合中的成员
     */
    public Boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 获取 Redis 集合的大小
     *
     * @param key 缓存键
     * @return Redis 集合的大小
     */
    public Long sSize(final String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 从Redis中移除指定集合中的元素
     *
     * @param key    集合的键
     * @param values 要移除的元素
     * @return 成功移除的元素数量
     */
    public Long sRemove(final String key, Object[] values) {
        return redisTemplate.opsForSet().remove(key, values);
    }


    // ===============================HASH=================================

    /**
     * 该方法用于在Redis中设置哈希表的键值对。
     *
     * @param key     键
     * @param hashKey 哈希键
     * @param value   值
     */
    public void hSet(final String key, final Object hashKey, final Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 在Redis中设置哈希表的值，并设置过期时间。
     *
     * @param key     键
     * @param hashKey 哈希表的键
     * @param value   哈希表的值
     * @param timeout 过期时间（毫秒）
     * @return 设置过期时间的结果
     */
    public Boolean hSet(final String key, final Object hashKey, final Object value, final Integer timeout) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        return expire(key, timeout);
    }

    /**
     * 直接设置整个Hash结构
     *
     * @param key     缓存键
     * @param dataMap 缓存值
     */
    public void hSet(final String key, final Map<Object, Object> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 直接设置整个Hash结构
     *
     * @param key     缓存键
     * @param dataMap 缓存值
     * @param timeout 超时时间
     * @return 设置结果
     */
    public Boolean hSet(final String key, final Map<Object, Object> dataMap, final Integer timeout) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
            return expire(key, timeout);
        }
        return Boolean.FALSE;
    }

    /**
     * 从Redis中获取哈希表指定字段的值
     *
     * @param key  Redis键
     * @param hKey 哈希表字段名
     * @return 哈希表指定字段的值
     */
    public Object hGet(final String key, final Object hKey) {
        BoundHashOperations<String, Object, Object> boundHashOperations = redisTemplate.boundHashOps(key);
        return boundHashOperations.get(hKey);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public List<Object> hGet(final String key, final Collection<Object> hKeys) {
        BoundHashOperations<String, Object, Object> boundHashOperations = redisTemplate.boundHashOps(key);
        return boundHashOperations.multiGet(hKeys);
    }

    /**
     * 获得缓存的Map
     *
     * @param key 缓存键
     */
    public Map<Object, Object> hGetAll(final String key) {
        BoundHashOperations<String, Object, Object> boundHashOperations = redisTemplate.boundHashOps(key);
        return boundHashOperations.entries();
    }

    /**
     * 删除 Redis 哈希表中指定的字段
     *
     * @param key     Redis 键
     * @param hashKey 哈希表字段
     */
    public void hDel(final String key, final Object[] hashKey) {
        BoundHashOperations<String, Object, Object> boundHashOperations = redisTemplate.boundHashOps(key);
        boundHashOperations.delete(hashKey);
    }

    /**
     * 检查给定的key和hashKey是否存在于Redis的哈希表中
     *
     * @param key     Redis的key
     * @param hashKey Redis的哈希表的key
     * @return 如果存在返回true，否则返回false
     */
    public Boolean hHasKey(final String key, final String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 在Redis中对给定的哈希键的值进行增量操作。
     *
     * @param key     Redis中的键
     * @param hashKey 哈希键
     * @param delta   增量值
     * @return 增量操作后的值
     */
    public Long hIncr(final String key, final String hashKey, final Long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }


    /**
     * 减少Redis中哈希表中指定键和字段的值。
     *
     * @param key     要操作的哈希表的键
     * @param hashKey 要操作的哈希表的字段
     * @param delta   要减少的值
     * @return 减少后的值
     */
    public Long hDecr(final String key, final String hashKey, final Long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, -delta);
    }

    // ============================incr=============================

    /**
     * 按delta递增
     *
     * @param key   Redis键
     * @param delta 增量
     * @return Hash对象集合
     */
    public Long incr(final String key, final Long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 按delta递减
     *
     * @param key   Redis键
     * @param delta 增量
     * @return Hash对象集合
     */
    public Long decr(final String key, final Long delta) {
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    // ============================key=============================

    /**
     * 验证是否存在某个key
     *
     * @param key Redis键
     * @return 是否存在
     */
    public Boolean hasKey(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }


    /**
     * 从Redis中获取以指定前缀开头的键的数量
     *
     * @param keyPrefix 键前缀
     * @return 键的数量
     */
    public int countKey(final String keyPrefix) {
        return Objects.requireNonNull(redisTemplate.keys(keyPrefix)).size();
    }

    // ===============================expire=================================

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间（秒）
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final Integer timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取有效时间
     *
     * @param key Redis键
     * @return 有效时间（秒）
     */
    public Long getExpire(final String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    public Boolean expire(final String key, final Integer timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获取有效时间
     *
     * @param key  Redis键
     * @param unit 时间单位
     * @return 有效时间
     */
    public Long getExpire(final String key, final TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }
}
