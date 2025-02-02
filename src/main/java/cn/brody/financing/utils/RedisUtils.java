package cn.brody.financing.utils;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * VcsRedisUtils
 *
 * @author chenyifu6
 * @since 2024/09/10 14:12
 */
@Slf4j
@Configuration
public class RedisUtils {

    public static RedisTemplate<String, Object> redisTemplate;

    /**
     * 根据key获取过期时间，返回0表示永久有效，单位秒
     *
     * @param key 缓存键
     * @return 过期时间
     */
    public static Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 指定缓存失效时间，单位：秒
     *
     * @param key  缓存键
     * @param time 缓存失效时间，单位：秒
     */
    public static void setExpire(String key, Long time) {
        setExpire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 指定缓存失效时间
     *
     * @param key      缓存键
     * @param time     缓存失效时间
     * @param timeUnit 时间单位
     */
    public static void setExpire(String key, Long time, TimeUnit timeUnit) {
        redisTemplate.expire(key, time, timeUnit);
    }


    /**
     * 获取缓存值
     *
     * @param key 缓存键
     * @return 缓存值
     */
    public static Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 模糊匹配所给表达式，并返回所有匹配到的key
     *
     * @param pattern 模糊匹配表达式
     * @return 匹配到的key集合
     */
    public static Set<String> getKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 判断一个键是否存在
     *
     * @param key 缓存键
     * @return 如果存在返回true，否则返回false
     */
    public static Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存
     *
     * @param key 缓存键
     */
    public static void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 批量删除缓存
     *
     * @param keys 要删除的缓存键集合
     */
    public static void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }


    /**
     * 根据key值获取缓存值，并将其转换为String类型
     *
     * @param key 缓存键
     * @return 缓存值，如果缓存不存在或转换失败则返回null
     */
    public static String getString(String key) {
        return getString(key, String.class);
    }

    /**
     * 根据key值获取缓存值，并将其转换为指定类型
     *
     * @param key   缓存键
     * @param clazz 要转换的目标类型
     * @param <T>   目标类型的泛型参数
     * @return 转换后的对象，如果缓存不存在或转换失败则返回null
     */
    public static <T> T getString(String key, Class<T> clazz) {
        Object value = get(key);
        if (value != null) {
            return JSONObject.parseObject(JSONObject.toJSONString(value), clazz);
        }
        return null;
    }

    /**
     * 根据key值存入数据类型为String的缓存值
     *
     * @param key   缓存键
     * @param value 要存入的值
     * @return 如果存入成功返回true，否则返回false
     */
    public static Boolean setString(String key, Object value) {
        return setString(key, value, null);
    }

    /**
     * 设置字符串类型的值，可以指定过期时间
     *
     * @param key   键
     * @param value 值
     * @param time  过期时间，单位为秒
     * @return 是否设置成功
     */
    public static Boolean setString(String key, Object value, Long time) {
        return setString(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 设置字符串类型的值，可以指定过期时间和时间单位
     *
     * @param key      键
     * @param value    值
     * @param time     过期时间
     * @param timeUnit 时间单位
     * @return 是否设置成功
     */
    public static Boolean setString(String key, Object value, Long time, TimeUnit timeUnit) {
        if (ObjectUtil.isNotNull(time)) {
            redisTemplate.opsForValue().set(key, value, time, timeUnit);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
        return true;
    }

    /**
     * 获取列表类型的值
     *
     * @param key 键
     * @return 列表值
     */
    public static List<Object> getList(String key) {
        return getList(key, 0L, -1L);
    }

    /**
     * 获取列表类型的值，可以指定起始和结束索引
     *
     * @param key   键
     * @param start 起始索引
     * @param end   结束索引
     * @return 列表值
     */
    public static List<Object> getList(String key, Long start, Long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取列表中指定索引的值
     *
     * @param key   键
     * @param index 索引
     * @return 索引对应的值
     */
    public static <T> T getList(String key, int index) {
        return (T) redisTemplate.opsForList().index(key, index);
    }

    /**
     * 设置列表类型的值
     *
     * @param key   键
     * @param value 值
     */
    public static void setList(String key, Collection value) {
        redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 初始化列表，将指定数量的null值添加到列表中
     *
     * @param key  键
     * @param size 列表大小
     */
    public static void initList(String key, int size) {
        for (int i = 0; i <= size; i++) {
            redisTemplate.opsForList().leftPush(key, null);
        }
    }

    /**
     * 设置列表类型的值，并指定过期时间和时间单位
     *
     * @param key      键
     * @param value    值
     * @param time     过期时间
     * @param timeUnit 时间单位
     */
    public static void setList(String key, Collection value, Long time, TimeUnit timeUnit) {
        setList(key, value);
        setExpire(key, time, timeUnit);
    }

    /**
     * 设置列表中指定索引的值
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     */
    public static void setList(String key, int index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 移除列表中的指定元素
     *
     * @param key   键
     * @param value 要移除的元素
     */
    public static void removeListElement(String key, Object value) {
        redisTemplate.opsForList().remove(key, 0, value);
    }

    /**
     * 获取哈希类型的值，根据字段名获取
     *
     * @param key   键
     * @param field 字段名
     * @return 字段对应的值
     */
    public static Object getHash(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 获取哈希类型的值，根据字段名和类型获取，并将结果转换为指定类型
     *
     * @param key   键
     * @param field 字段名
     * @param clazz 类型
     * @return 转换后的类型对象
     */
    public static <T> T getHash(String key, String field, Class<T> clazz) {
        Object value = getHash(key, field);
        if (null != value) {
            return JSONObject.parseObject(JSONObject.toJSONString(value), clazz);
        }
        return null;
    }

    /**
     * 获取哈希类型的值，根据键名和类型获取，并将结果转换为指定类型
     *
     * @param key   键
     * @param clazz 类型
     * @return 转换后的类型对象集合
     */
    public static <T> Map<String, T> getHash(String key, Class<T> clazz) {
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
        if (entries.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, T> result = new HashMap<>(entries.size());
        entries.forEach((key1, value) -> result.put(key1.toString(), JSONObject.parseObject(JSONObject.toJSONString(value), clazz)));
        return result;
    }

    /**
     * 设置哈希类型的值，根据字段名设置值
     *
     * @param key   键
     * @param field 字段名
     * @param value 值
     */
    public static void setHash(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 获取哈希类型的值的大小
     *
     * @param key 键
     * @return 哈希类型的值的大小
     */
    public static int getHashSize(String key) {
        return redisTemplate.opsForHash().size(key).intValue();
    }

    /**
     * 删除哈希类型的值，根据字段名删除
     *
     * @param key    键
     * @param fields 要删除的字段名数组
     * @return 删除的字段数
     */
    public static Object deleteHash(String key, Object... fields) {
        return redisTemplate.opsForHash().delete(key, fields);
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        RedisUtils.redisTemplate = redisTemplate;
    }

}
