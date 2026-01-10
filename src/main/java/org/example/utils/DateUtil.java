package org.example.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;

import java.util.Date;

/**
 * @author nanak
 */
public class DateUtil {
    // 全局默认日期格式
    // yyyy-MM-dd
    public static final String DEFAULT_DATE_FORMAT = DatePattern.NORM_DATE_PATTERN;
    // yyyy-MM-dd HH:mm:ss
    public static final String DEFAULT_DATETIME_FORMAT = DatePattern.NORM_DATETIME_PATTERN;

    /**
     * 日期转字符串（默认格式：yyyy-MM-dd HH:mm:ss）
     */
    public String format(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        return cn.hutool.core.date.DateUtil.format(date, DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 日期转字符串(自定义格式)
     */
    public String format(Date date, String format) {
        return cn.hutool.core.date.DateUtil.format(date, format);
    }

    /**
     * 当前时间（yyyy-MM-dd HH:mm:ss）
     */
    public String now() {
        return cn.hutool.core.date.DateUtil.now();
    }

    /**
     * 计算两个日期的天数
     */
    public long betweenDay(Date start, Date end) {
        // 当前时间晚于结束时间，返回负数
        if (start.after(end)) {
            return -cn.hutool.core.date.DateUtil.between(end, start, DateUnit.DAY);
        }
        return cn.hutool.core.date.DateUtil.between(start, end, DateUnit.DAY);
    }

    /**
     * N天后
     */
    public Date offsetDay(Date date, int offset) {
        return cn.hutool.core.date.DateUtil.offsetDay(date, offset);
    }
}
