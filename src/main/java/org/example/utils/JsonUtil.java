package org.example.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * @author nanak
 *
 * JSON 工具类（封装 FastJSON2，统一序列化规则）
 */
public class JsonUtil {
    /**
     * JSON字符串转对象
     */
    public <T> T parseObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * 对象转JSON字符串
     */
    public String toJsonString(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * JSON 字符串转 List
     */
    public <T> List<T> toList(String jsonStr, Class<T> clazz) {
        return JSON.parseArray(jsonStr, clazz);
    }

    /**
     * JSON 字符串转 Map
     */
    public Map<String, Object> toMap(String jsonStr) {
        return JSON.parseObject(jsonStr, new TypeReference<Map<String, Object>>() {});
    }

    /**
     * 对象转 JSONObject
     */
    public JSONObject toJsonObject(Object obj) {
        return (JSONObject) JSON.toJSON(obj);
    }
}
