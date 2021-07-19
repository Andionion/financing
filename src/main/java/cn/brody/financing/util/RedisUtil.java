package cn.brody.financing.util;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author chenyifu
 * @date 2020/09/21 10:58
 */
@Slf4j
@Component
public class RedisUtil {

    private static RedisTemplate<String, Object> hikRedisTemplate;

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    /**
     * 指定缓存失效时间 单位：秒
     */
    public static void expire(String key, Integer time) {
        hikRedisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    // ==========缓存相关方法===========

    /**
     * 根据key获取过期时间，返回0表示永久有效，单位秒
     *
     * @param key key
     * @return 过期时间
     */
    public static Long getExpire(String key) {
        return hikRedisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * Find all keys matching the given pattern.
     * 模糊匹配所给表达式，并返回所有匹配到的key
     *
     * @param pattern
     */
    public static Set<String> keys(String pattern) {
        return hikRedisTemplate.keys(pattern);
    }

    /**
     * 判断一个键是否存在
     *
     * @param key
     * @return
     */
    public static Boolean hasKey(String key) {
        return hikRedisTemplate.hasKey(key);
    }

    /**
     * 删除缓存
     *
     * @param key
     */
    public static void delete(String key) {
        hikRedisTemplate.delete(key);
    }

    /**
     * 根据key值获取缓存值
     *
     * @param key key
     * @return 缓存值
     */
    public static String getString(String key) {
        return getString(key, String.class);
    }

    /**
     * 根据key值获取缓存值
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getString(String key, Class<T> clazz) {
        Object value = hikRedisTemplate.opsForValue().get(key);
        if (value != null) {
            return JSONObject.parseObject(value.toString(), clazz);
        }
        return null;
    }


    // ===============数据类型为String的相关方法===============

    /**
     * 根据key值存入数据类型为String的缓存值
     *
     * @param key key
     * @param v   value
     */
    public static <V> void setString(String key, V v) {
        String value = JSONObject.toJSONString(v);
        hikRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 根据key值存入数据类型为String的缓存值并设置时间,单位为秒
     *
     * @param key  key
     * @param v    value
     * @param time 秒
     */
    public static <V> void setStringWithTime(String key, V v, Integer time) {
        setStringWithTime(key, v, time, TimeUnit.SECONDS);
    }

    /**
     * 根据key值存入数据类型为String的缓存值并设置时间
     *
     * @param key  key
     * @param v    value
     * @param time 秒
     */
    public static <V> void setStringWithTime(String key, V v, Integer time, TimeUnit timeUnit) {
        String value = JSONObject.toJSONString(v);
        hikRedisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    /**
     * 根据key值存入数据类型为List的缓存值
     *
     * @param key key
     * @param v   缓存值
     */
    public static <V> void setList(String key, V v) {
        String value = JSONObject.toJSONString(v);
        hikRedisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 获取List缓存的内容，从start到end，若从0到-1代表所有值，默认为String
     *
     * @param key   key
     * @param start 开始
     * @param end   结束
     * @return 缓存值
     */
    public static List<String> getList(String key, Long start, Long end) {
        return getList(key, start, end, String.class);
    }

    // ===============数据类型为List的相关方法===============

    /**
     * 获取List缓存的内容，从start到end，若从0到-1代表所有值
     *
     * @param key   key
     * @param start 开始
     * @param end   结束
     * @param clazz 类
     * @return 缓存值
     */
    public static <T> List<T> getList(String key, Long start, Long end, Class<T> clazz) {
        List<Object> list = hikRedisTemplate.opsForList().range(key, start, end);
        if (CollectionUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().map(object -> JSONObject.parseObject(object.toString(), clazz)).collect(Collectors.toList());
    }

    /**
     * 获取value为列表的hash
     *
     * @param key
     * @param field
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> listHash(String key, String field, Class<T> clazz) {
        Object value = hikRedisTemplate.opsForHash().get(key, field);
        if (null != value) {
            return JSONObject.parseArray(value.toString(), clazz);
        }
        return new ArrayList<>();
    }

    /**
     * 获取value为指定对象的hash
     *
     * @param key
     * @param field
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getHash(String key, String field, Class<T> clazz) {
        Object value = hikRedisTemplate.opsForHash().get(key, field);
        if (null != value) {
            return JSONObject.parseObject(value.toString(), clazz);
        }
        return null;
    }


    // ==================== hash ===========================

    /**
     * 添加value为list的hash
     *
     * @param key
     * @param field
     * @param values
     * @param <T>
     */
    public static <T> void setHash(String key, String field, List<T> values) {
        String value = JSONObject.toJSONString(values);
        hikRedisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 添加value为指定对象的hash
     *
     * @param key
     * @param field
     * @param t
     * @param <T>
     */
    public static <T> void setHash(String key, String field, T t) {
        String value = JSONObject.toJSONString(t);
        hikRedisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 删除hash
     *
     * @param key
     */
    public static void deleteHash(String key, Object... fields) {
        hikRedisTemplate.opsForHash().delete(key, fields);
    }

    @Autowired
    public void setHikRedisTemplate() {
        hikRedisTemplate = redisTemplate;
    }

    /**
     * 批量删除缓存
     *
     * @param keys key
     */
    public void delete(List<String> keys) {
        hikRedisTemplate.delete(keys);
    }

}