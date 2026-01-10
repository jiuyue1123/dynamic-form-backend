package org.example.utils;

import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.StrUtil;

import java.util.regex.Pattern;

/**
 * @author nanak
 * <p>
 * 字符串工具类
 */
public class StringUtil {
    // 正则匹配规则（核心：用于识别字符串类型）
    private static final Pattern PATTERN_PHONE = Pattern.compile("^1[3-9]\\d{9}$");
    private static final Pattern PATTERN_ID_CARD = Pattern.compile("^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$");
    private static final Pattern PATTERN_EMAIL = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
    private static final Pattern PATTERN_LANDLINE = Pattern.compile("^0\\d{2,3}-\\d{7,8}$");
    private static final Pattern PATTERN_BANK_CARD = Pattern.compile("^\\d{16,19}$");
    private static final Pattern PATTERN_PLATE_NUMBER = Pattern.compile("^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4,5}[A-Z0-9挂学警港澳]{1}$");
    // 2-4个中文字符（姓名）
    private static final Pattern PATTERN_CHINESE_NAME = Pattern.compile("^[\\u4e00-\\u9fa5]{2,4}$");

    /**
     * 判断字符串是否为空（空串/空白符/null）
     */
    public static boolean isEmpty(String str) {
        return StrUtil.isEmpty(str);
    }

    /**
     * 判断字符串是否非空
     */
    public static boolean isNotEmpty(String str) {
        return StrUtil.isNotEmpty(str);
    }

    /**
     * 字符串脱敏(用户id
     * 中文姓名
     * 身份证号
     * 座机号
     * 手机号
     * 地址
     * 电子邮件
     * 密码
     * 中国大陆车牌，包含普通车辆、新能源车辆
     * 银行卡)
     */
    public static String sensitive(String str) {
        // 空值处理：避免空指针
        if (str == null || str.trim().isEmpty()) {
            return str;
        }
        String trimStr = str.trim();

        // 1. 匹配手机号（优先级高于座机/数字ID）
        if (PATTERN_PHONE.matcher(trimStr).matches()) {
            return DesensitizedUtil.mobilePhone(trimStr);
        }
        // 2. 匹配身份证号
        else if (PATTERN_ID_CARD.matcher(trimStr).matches()) {
            // 保留前6后2
            return DesensitizedUtil.idCardNum(trimStr, 6, 2);
        }
        // 3. 匹配邮箱
        else if (PATTERN_EMAIL.matcher(trimStr).matches()) {
            return DesensitizedUtil.email(trimStr);
        }
        // 4. 匹配座机号
        else if (PATTERN_LANDLINE.matcher(trimStr).matches()) {
            return DesensitizedUtil.fixedPhone(trimStr);
        }
        // 5. 匹配银行卡号
        else if (PATTERN_BANK_CARD.matcher(trimStr).matches()) {
            return DesensitizedUtil.bankCard(trimStr);
        }
        // 6. 匹配车牌（含新能源）
        else if (PATTERN_PLATE_NUMBER.matcher(trimStr).matches()) {
            return DesensitizedUtil.carLicense(trimStr);
        }
        // 7. 匹配中文姓名
        else if (PATTERN_CHINESE_NAME.matcher(trimStr).matches()) {
            return DesensitizedUtil.chineseName(trimStr);
        }
        // 8. 匹配纯数字（用户ID）：假设ID为纯数字，保留后4位
        else if (trimStr.matches("^\\d+$")) {
            return DesensitizedUtil.idCardNum(trimStr, 0, 4);
        }
        // 9. 匹配地址（包含中文+数字/字符，长度>5）
        // 保留前8位
        else if (trimStr.length() > 5 && trimStr.matches(".*[\\u4e00-\\u9fa5]+.*")) {
            return DesensitizedUtil.address(trimStr, 8);
        }
        // 11. 默认脱敏：非上述类型，保留前1后1，中间用*填充
        else {
            return StrUtil.hide(trimStr, 1, trimStr.length() - 1);
        }
    }

    /**
     * 字符串拼接
     */
    public static String concat(boolean isNullToEmpty, String... strs) {
        return StrUtil.concat(isNullToEmpty, strs);
    }

    /**
     * join拼接
     */
    public static String join(String separator, String... strs) {
        return StrUtil.join(separator, (Object) strs);
    }

    /**
     * 首字母大写
     */
    public static String upperFirst(String str) {
        return StrUtil.upperFirst(str);
    }
}
