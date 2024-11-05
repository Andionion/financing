package cn.brody.financing.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import lombok.Setter;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * <p>
 * 构造URL
 * </p>
 *
 * @author yangkai.shen (https://xkcoding.com)
 * @since 1.9.0
 */
@Setter
public class UrlBuilderUtils {

    private final Map<String, String> params = new LinkedHashMap<>(7);
    private String baseUrl;

    private UrlBuilderUtils() {

    }

    /**
     * @param baseUrl 基础路径
     * @return the new {@code UrlBuilderUtils}
     */
    public static UrlBuilderUtils fromBaseUrl(String baseUrl) {
        UrlBuilderUtils builder = new UrlBuilderUtils();
        builder.setBaseUrl(baseUrl);
        return builder;
    }


    /**
     * 只读的参数Map
     *
     * @return unmodifiable Map
     * @since 1.15.0
     */
    public Map<String, Object> getReadOnlyParams() {
        return Collections.unmodifiableMap(params);
    }

    /**
     * 添加参数
     *
     * @param key   参数名称
     * @param value 参数值
     * @return this UrlBuilderUtils
     */
    public UrlBuilderUtils queryParam(String key, Object value) {
        if (StrUtil.isEmpty(key)) {
            throw new RuntimeException("参数名不能为空");
        }
        String valueAsString = (value != null ? value.toString() : null);
        this.params.put(key, valueAsString);

        return this;
    }

    /**
     * 添加参数
     *
     * @param condition 执行条件
     * @param key       参数名称
     * @param value     参数值
     * @return this UrlBuilderUtils
     */
    public UrlBuilderUtils queryParam(boolean condition, String key, Object value) {
        if (condition) {
            String valueAsString = (value != null ? value.toString() : null);
            this.params.put(key, valueAsString);
        }
        return this;
    }

    /**
     * 构造url
     *
     * @return url
     */
    public String build() {
        return this.build(false);
    }

    /**
     * 构造url
     *
     * @param encode 转码
     * @return url
     */
    public String build(boolean encode) {
        if (MapUtil.isEmpty(this.params)) {
            return this.baseUrl;
        }
        String baseUrl = appendIfNotContain(this.baseUrl, "?", "&");
        String paramString = parseMapToString(this.params, encode);
        return baseUrl + paramString;
    }

    /**
     * 如果给定字符串{@code str}中不包含{@code appendStr}，则在{@code str}后追加{@code appendStr}；
     * 如果已包含{@code appendStr}，则在{@code str}后追加{@code otherwise}
     *
     * @param str       给定的字符串
     * @param appendStr 需要追加的内容
     * @param otherwise 当{@code appendStr}不满足时追加到{@code str}后的内容
     * @return 追加后的字符串
     */
    public static String appendIfNotContain(String str, String appendStr, String otherwise) {
        if (StrUtil.isEmpty(str) || StrUtil.isEmpty(appendStr)) {
            return str;
        }
        if (str.contains(appendStr)) {
            return str.concat(otherwise);
        }
        return str.concat(appendStr);
    }

    /**
     * map转字符串，转换后的字符串格式为 {@code xxx=xxx&xxx=xxx}
     *
     * @param params 待转换的map
     * @param encode 是否转码
     * @return str
     */
    public String parseMapToString(Map<String, String> params, boolean encode) {
        List<String> paramList = new ArrayList<>();
        forEach(params, (k, v) -> {
            if (v == null) {
                paramList.add(k + "=");
            } else {
                paramList.add(k + "=" + (encode ? URLUtil.encode(v) : v));
            }
        });
        return String.join("&", paramList);
    }

    /**
     * 遍历
     *
     * @param map    待遍历的 map
     * @param action 操作
     * @param <K>    map键泛型
     * @param <V>    map值泛型
     */
    public <K, V> void forEach(Map<K, V> map, BiConsumer<? super K, ? super V> action) {
        if (MapUtil.isEmpty(map) || action == null) {
            return;
        }
        for (Map.Entry<K, V> entry : map.entrySet()) {
            K k;
            V v;
            try {
                k = entry.getKey();
                v = entry.getValue();
            } catch (IllegalStateException ise) {
                // this usually means the entry is no longer in the map.
                throw new ConcurrentModificationException(ise);
            }
            action.accept(k, v);
        }
    }
}
