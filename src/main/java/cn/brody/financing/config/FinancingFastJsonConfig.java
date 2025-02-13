package cn.brody.financing.config;

import cn.brody.financing.enums.TradeTypeEnum;
import cn.brody.financing.serializer.TradeTypeEnumDeserializer;
import cn.brody.financing.serializer.TradeTypeEnumSerializer;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FinancingFastJsonConfig
 *
 * @author BrodyChen
 * @since 2025/02/13 16:54
 */
@Configuration
public class FinancingFastJsonConfig {

    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        // 配置 FastJSON
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue
        );

        // 注册自定义序列化器
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        serializeConfig.put(TradeTypeEnum.class, new TradeTypeEnumSerializer());
        fastJsonConfig.setSerializeConfig(serializeConfig);

        // 注册自定义反序列化器
        ParserConfig parserConfig = ParserConfig.getGlobalInstance();
        parserConfig.putDeserializer(TradeTypeEnum.class, new TradeTypeEnumDeserializer());
        fastJsonConfig.setParserConfig(parserConfig);

        fastConverter.setFastJsonConfig(fastJsonConfig);
        return fastConverter;
    }
}
