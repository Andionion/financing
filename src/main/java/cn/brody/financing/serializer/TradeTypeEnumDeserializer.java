package cn.brody.financing.serializer;

import cn.brody.financing.enums.TradeTypeEnum;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;

/**
 * TradeTypeEnumDeserializer
 *
 * @author BrodyChen
 * @since 2025/02/13 16:52
 */

public class TradeTypeEnumDeserializer implements ObjectDeserializer {

    @Override
    public TradeTypeEnum deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        String value = parser.parseObject(String.class);
        if (value == null) {
            return null;
        }
        return TradeTypeEnum.forValue(value);
    }

    @Override
    public int getFastMatchToken() {
        return 0; // 默认返回 0 即可
    }
}
