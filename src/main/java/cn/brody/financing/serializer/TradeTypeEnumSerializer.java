package cn.brody.financing.serializer;

import cn.brody.financing.enums.TradeTypeEnum;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * TradeTypeEnumSerializer
 *
 * @author BrodyChen
 * @since 2025/02/13 16:59
 */

public class TradeTypeEnumSerializer implements ObjectSerializer {

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        if (object == null) {
            serializer.write(null);
            return;
        }

        // 将枚举值转换为小写的字符串形式
        TradeTypeEnum tradeType = (TradeTypeEnum) object;
        serializer.write(tradeType.name().toLowerCase());
    }
}
