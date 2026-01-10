package org.example.utils;

import org.springframework.stereotype.Component;

/**
 * @author nanak
 *
 * 工具类统一入口（门面模式）
 * 所有工具类调用都通过此类
 */
@Component
public class ToolKit {
    // 日期工具
    public static final DateUtil DATE = new DateUtil();
    // 加密工具
    public static final CryptoUtil CRYPTO = new CryptoUtil();
    // JSON 工具
    public static final JsonUtil JSON = new JsonUtil();
    // 集合工具
    public static final CollectionUtil COLLECTION = new CollectionUtil();
    // 字符串工具
    public static final StringUtil STRING = new StringUtil();
    // 反射工具
    public static final ReflectUtil REFLECT = new ReflectUtil();
}