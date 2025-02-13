package cn.brody.financing.config;

import cn.brody.financing.serializer.KeyStringRedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.StringUtils;

/**
 * VcsRedisConfig
 *
 * @author chenyifu6
 * @since 2024/09/10 14:12
 */
@Configuration
public class RedisConfig {

    @Value("${spring.application.name:}")
    private String applicationName;

    private String redisKeyPrefix;


    /**
     * 设置Redis的键前缀。
     *
     * @param redisKeyPrefix 需要设置的Redis键前缀，如果该参数长度大于0，则使用该参数作为Redis键前缀；否则，使用应用名称作为Redis键前缀。
     */
    @Autowired
    public void setRedisKeyPrefix(@Value("${spring.cache.redis.key-prefix:}") String redisKeyPrefix) {
        if (StringUtils.hasLength(redisKeyPrefix)) {
            this.redisKeyPrefix = redisKeyPrefix;
        } else {
            this.redisKeyPrefix = applicationName;
        }
    }

    /**
     * 创建并配置RedisTemplate实例。
     *
     * @param redisConnectionFactory 用于创建Redis连接的工厂。
     * @return 返回一个已配置好的RedisTemplate实例，该实例可以用于操作Redis数据库。
     */
    @Bean
    public RedisTemplate<String, Object> vcsAutoAddRedisKeyPrefixRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        RedisSerializer<String> redisSerializer = new KeyStringRedisSerializer(this.redisKeyPrefix);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setValueSerializer(RedisSerializer.json());
        redisTemplate.setHashKeySerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(RedisSerializer.json());
        return redisTemplate;
    }
}
