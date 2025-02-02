package cn.brody.financing.serializer;

import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

/**
 * KeyStringRedisSerializer
 *
 * @author chenyifu6
 * @since 2024/09/11 14:46
 */
public class KeyStringRedisSerializer extends StringRedisSerializer {
    private final String keyPrefix;

    public KeyStringRedisSerializer(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    @Override
    public String deserialize(@Nullable byte[] bytes) {
        String str = super.deserialize(bytes);
        if (StringUtils.hasLength(this.keyPrefix) && str.startsWith(this.keyPrefix)) {
            str = str.substring(this.keyPrefix.length() + 1);
        }
        return str;
    }

    @Override
    public byte[] serialize(@Nullable String string) {
        if (StringUtils.hasLength(this.keyPrefix)) {
            string = this.keyPrefix + ":" + string;
        }
        return super.serialize(string);
    }
}