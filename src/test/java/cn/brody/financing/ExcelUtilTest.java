package cn.brody.financing;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author brody
 * @date 2021/11/28
 */
public class ExcelUtilTest {

    @Test
    public void testRead() {
//        String resourceAsString = ResourceHelper.getResourceAsString(getClass(), "/fundTradeTest.xlsx");
        String path = Objects.requireNonNull(getClass().getResource("/")).getPath();
        ExcelReader reader = ExcelUtil.getReader(Objects.requireNonNull(getClass().getResource("/")).getPath() + "fundTrade.xlsx");
        List<List<Object>> read = reader.read();
        List<Map<String, Object>> maps = reader.readAll();
        List<Map<LocalDate, Object>> tempList = new ArrayList<>();

        maps.forEach(map -> {
            String code = map.get("code").toString();
            map.remove("code");
            Map<LocalDate, Object> tempMap = new LinkedHashMap<>();
            map.forEach((key, value) -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDate parse = LocalDate.parse(key, formatter);
                tempMap.put(parse, value);
            });
            tempList.add(tempMap);
        });
        System.out.println(read);
    }
}
