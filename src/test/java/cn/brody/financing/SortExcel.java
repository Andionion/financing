package cn.brody.financing;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author brody
 * @date 2021/12/20
 */
public class SortExcel {
    public static void main(String[] args) {
        ExcelReader reader = ExcelUtil.getReader(Objects.requireNonNull(SortExcel.class.getResource("/")).getPath() + "beam_result.xlsx");
        List<Map<String, Object>> maps = reader.readAll();
        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        Map<String, String> map = new HashMap<>();
        maps.forEach(data -> {
            StringBuilder tempName = new StringBuilder();
            StringBuilder realName = new StringBuilder();
            Object beamType = data.get("beam_type");
            Object level = data.get("level");
            Object Ln1 = data.get("Ln1");
            Object r1b1 = data.get("r1b");
            Object r4Type = data.get("r4_type");
            Object anchor = data.get("anchor");
            Object avoid = data.get("avoid");

            tempName.append(beamType);
            realName.append(beamType).append("-");
            tempName.append(level);
            realName.append(level).append("-");
            if (Ln1.toString().equals("0")) {
                tempName.append("0");
                realName.append("0").append("-");
            } else {
                tempName.append("1");
                realName.append("其他").append("-");
            }
            if (ObjectUtil.isNull(r1b1)) {
                tempName.append("0");
                realName.append("无").append("-");
            } else {
                tempName.append("1");
                realName.append("有").append("-");
            }
            tempName.append(r4Type);
            realName.append(r4Type).append("-");
            tempName.append(anchor);
            realName.append(anchor).append("-");
            tempName.append(avoid);
            realName.append(avoid);
            map.put(tempName.toString(), realName.toString());
            List<Map<String, Object>> tempList = result.get(tempName.toString());
            if (CollectionUtil.isEmpty(tempList)) {
                tempList = new ArrayList<>();
                result.put(tempName.toString(), tempList);
            }
            tempList.add(data);
        });
        System.out.println(result.size());
        // 写入文件
        result.forEach((key, value) -> {
            String realName = map.get(key);
            System.out.println(realName);
            ExcelWriter writer = ExcelUtil.getWriter(Objects.requireNonNull(SortExcel.class.getResource("/")).getPath() + "/result/" + realName + ".xlsx");
            writer.write(value);
            writer.close();
        });

    }
}
