package course.selection.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *  將Map的參數轉為駝峰命的的工具類別
 */
public class CamelCaseUtil {

    public static Map<String, Object> underlineToCamel(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        map.forEach((key, value) -> {
            result.put(underlineToCamel(key), value);
        });
        return result;
    }

    public static List<Map<String, Object>> underlineToCamel(List<Map<String, Object>> listMap) {
        List<Map<String, Object>> result = new ArrayList<>();
        listMap.forEach(map -> {
            map = underlineToCamel(map);
            result.add(map);
        });
        return result;
    }

    private static String underlineToCamel(String key) {
        boolean upperFlag = false;
        StringBuilder sb = new StringBuilder();
        for (char ch : key.toCharArray()) {
            if (ch == '_') {
                upperFlag = true;
            } else {
                if (upperFlag) {
                    sb.append(Character.toUpperCase(ch));
                    upperFlag = false;
                } else {
                    sb.append(ch);
                }
            }
        }
        return sb.toString();
    }
}
