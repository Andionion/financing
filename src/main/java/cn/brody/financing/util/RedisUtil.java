package cn.brody.financing.util;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author chenyifu
 * @date 2020/09/21 10:58
 */
@Slf4j
@Component
public class RedisUtil {

    private static RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setHikRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
    }


    // ==========缓存相关方法===========


    /**
     * 指定缓存失效时间 单位：秒
     */
    public static void expire(String key, Long time) {
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 根据key获取过期时间，返回0表示永久有效，单位秒
     *
     * @param key key
     * @return 过期时间
     */
    public static Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * Find all keys matching the given pattern.
     * 模糊匹配所给表达式，并返回所有匹配到的key
     *
     * @param pattern
     */
    public static Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 判断一个键是否存在
     *
     * @param key
     * @return
     */
    public static Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存
     *
     * @param key
     */
    public static void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 批量删除缓存
     *
     * @param keys
     */
    public static void delete(List<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 获取值
     *
     * @param key
     * @return
     */
    public static Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // ===============数据类型为String的相关方法===============

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
        Object value = get(key);
        if (value != null) {
            return JSONObject.parseObject(value.toString(), clazz);
        }
        return null;
    }

    /**
     * 根据key值存入String类型的缓存值
     *
     * @param key   key
     * @param value value
     */
    public static void setString(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 根据key值存入数据类型为String的缓存值并设置时间,单位为秒
     *
     * @param key   key
     * @param value value
     * @param time  秒
     */
    public static void setString(String key, Object value, Integer time) {
        setString(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 根据key值存入数据类型为String的缓存值并设置时间
     *
     * @param key      key
     * @param value    value
     * @param time     秒
     * @param timeUnit 时间单位
     */
    public static void setString(String key, Object value, Integer time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    // ===============数据类型为List的相关方法===============


    /**
     * 获取List缓存的内容，从start到end，若从0到-1代表所有值，默认为String
     *
     * @param key   key
     * @param start 开始
     * @param end   结束
     * @return 缓存值
     */
    public static List<Object> getList(String key, Long start, Long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }


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
        List<Object> list = getList(key, start, end);
        if (CollectionUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().map(object -> JSONObject.parseObject(object.toString(), clazz)).collect(Collectors.toList());
    }

    /**
     * 根据key值存入数据类型为List的缓存值
     *
     * @param key   key
     * @param value 缓存值
     */
    public static void setList(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    // ==================== hash ===========================


    /**
     * 获取hash值
     *
     * @param key
     * @param field
     * @return
     */
    public static Object getHash(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
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
        Object value = getHash(key, field);
        if (null != value) {
            return JSONObject.parseObject(value.toString(), clazz);
        }
        return null;
    }


    /**
     * 获取key对应的map
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> getHash(String key, Class<T> clazz) {
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
        if (entries.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, T> result = new HashMap<>(entries.size());
        entries.forEach((key1, value) -> result.put(key1.toString(), JSONObject.parseObject(JSONObject.toJSONString(key), clazz)));
        return result;
    }


    /**
     * 添加value为指定对象的hash
     *
     * @param key
     * @param field
     * @param value
     */
    public static void setHash(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }


    /**
     * hash 键数量
     *
     * @param key key
     * @return int
     */
    public static int getHashSize(String key) {
        return redisTemplate.opsForHash().size(key).intValue();
    }

    /**
     * 删除hash
     *
     * @param key
     */
    public static Object deleteHash(String key, Object... fields) {
        return redisTemplate.opsForHash().delete(key, fields);
    }

}