package cn.brody.financing.cache;

import cn.brody.financing.util.RedisUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author chenyifu6
 * @date 2021/10/15
 */
@Component
@Slf4j
public class MybatisRedisCache implements Cache {

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * 缓存刷新时间（秒）
     */
    @Setter
    private long flushInterval = 1000L;

    private String id;

    public MybatisRedisCache() {
    }

    public MybatisRedisCache(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        RedisUtil.setHash(id, key.toString(), value);
        if (flushInterval > 0L) {
            RedisUtil.expire(id, flushInterval);
        }
    }

    @Override
    public Object getObject(Object key) {
        return RedisUtil.getHash(id, key.toString());
    }

    @Override
    public Object removeObject(Object key) {
        return RedisUtil.deleteHash(getId(), key);
    }

    @Override
    public void clear() {
        RedisUtil.delete(getId());
    }

    @Override
    public int getSize() {
        return RedisUtil.getHashSize(getId());
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }
}
