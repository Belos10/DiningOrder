package com.example.utils.payUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.*;

/**
 * @author zhuchengcheng
 * @date 2021/7/27
 */

public class AssembleUtil {

    /**
     * 接口返回结果复杂集合类转换签名
     *
     * @param res 接口返回结果
     * @return
     */
    public static Map<String, Object> translateMapData(Map<String, Object> res) {
        Map<String, Object> resultDataMap = new TreeMap<String, Object>();
        Set set = res.entrySet();
        for (Iterator iter = set.iterator(); iter.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            if (!(entry.getValue() instanceof String)) {
                if (entry.getValue() instanceof String[]
                        || entry.getValue() instanceof Map
                        || entry.getValue() instanceof List) {
                    // 将数据转换成json字符串参与签名
                    resultDataMap.put(key, JSON.toJSONStringWithDateFormat(value, "yyyy-MM-dd'T'HH:mm:ss",
                            SerializerFeature.WriteMapNullValue,
                            SerializerFeature.UseISO8601DateFormat,
                            SerializerFeature.MapSortField));
                } else {
                    if (value instanceof Date) {
                        value = DateFormatUtils.format((Date) value, "yyyy-MM-dd'T'HH:mm:ss");
                    }
                    resultDataMap.put(key, value);
                }
            } else {
                resultDataMap.put(key, value);
            }
        }
        return resultDataMap;
    }

    //顺序组装请求参数，用于签名/校验
    public static String AssembleSignatureData(Map data) {
        StringBuilder sb = new StringBuilder();
        TreeMap<String, Object> treeMap = new TreeMap(data);
        for (Map.Entry<String, Object> ent : treeMap.entrySet()) {
            String name = ent.getKey();
            if (!"sign".equals(name)) {
                sb.append(name).append('=').append(ent.getValue()).append('&');
            }
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 顺序组装请求参数，用于签名/校验
     */
    public static String AssembleJSONString(Map data) {

        Map<String, String> translateData = translateMapData(data);

        TreeMap<String, String> treeMap = new TreeMap(translateData);
        return JSON.toJSONString(treeMap, SerializerFeature.WriteMapNullValue);

    }

}
