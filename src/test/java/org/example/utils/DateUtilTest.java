package org.example.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DateUtil 单元测试
 */
@SpringBootTest
class DateUtilTest {

    @Test
    void testFormatWithDefaultFormat() {
        // 准备测试数据
        Date date = new Date(1640995200000L); // 2022-01-01 00:00:00
        
        // 执行测试
        String result = ToolKit.DATE.format(date);
        
        // 验证结果
        assertNotNull(result);
        assertTrue(result.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"));
    }

    @Test
    void testFormatWithCustomFormat() {
        // 准备测试数据
        Date date = new Date(1640995200000L); // 2022-01-01 00:00:00
        String customFormat = "yyyy/MM/dd";
        
        // 执行测试
        String result = ToolKit.DATE.format(date, customFormat);
        
        // 验证结果
        assertNotNull(result);
        assertTrue(result.matches("\\d{4}/\\d{2}/\\d{2}"));
    }

    @Test
    void testNow() {
        // 执行测试
        String now = ToolKit.DATE.now();
        
        // 验证结果
        assertNotNull(now);
        assertTrue(now.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"));
        
        // 验证时间是当前时间（允许1秒误差）
        Date currentDate = new Date();
        String currentFormatted = ToolKit.DATE.format(currentDate);
        // 由于时间可能有微小差异，我们只比较前16位（到分钟）
        assertEquals(currentFormatted.substring(0, 16), now.substring(0, 16));
    }

    @Test
    void testBetweenDay() throws ParseException {
        // 准备测试数据
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse("2022-01-01");
        Date endDate = sdf.parse("2022-01-10");
        
        // 执行测试
        long days = ToolKit.DATE.betweenDay(startDate, endDate);
        
        // 验证结果
        assertEquals(9, days);
    }

    @Test
    void testBetweenDayWithSameDate() throws ParseException {
        // 准备测试数据
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2022-01-01");
        
        // 执行测试
        long days = ToolKit.DATE.betweenDay(date, date);
        
        // 验证结果
        assertEquals(0, days);
    }

    @Test
    void testBetweenDayWithReverseOrder() throws ParseException {
        // 准备测试数据
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse("2022-01-10");
        Date endDate = sdf.parse("2022-01-01");
        
        // 执行测试
        long days = ToolKit.DATE.betweenDay(startDate, endDate);
        
        // 验证结果
        assertEquals(-9, days); // 负数表示开始日期晚于结束日期
    }

    @Test
    void testOffsetDay() throws ParseException {
        // 准备测试数据
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date baseDate = sdf.parse("2022-01-01");
        
        // 测试向后偏移
        Date futureDate = ToolKit.DATE.offsetDay(baseDate, 5);
        String futureDateStr = sdf.format(futureDate);
        assertEquals("2022-01-06", futureDateStr);
        
        // 测试向前偏移
        Date pastDate = ToolKit.DATE.offsetDay(baseDate, -5);
        String pastDateStr = sdf.format(pastDate);
        assertEquals("2021-12-27", pastDateStr);
    }

    @Test
    void testOffsetDayWithZero() throws ParseException {
        // 准备测试数据
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date baseDate = sdf.parse("2022-01-01");
        
        // 执行测试
        Date result = ToolKit.DATE.offsetDay(baseDate, 0);
        
        // 验证结果
        assertEquals(sdf.format(baseDate), sdf.format(result));
    }

    @Test
    void testFormatWithNullDate() {
        // 测试null值处理
        assertThrows(Exception.class, () -> {
            ToolKit.DATE.format(null);
        });
    }

    @Test
    void testConstants() {
        // 验证常量值
        assertEquals("yyyy-MM-dd", DateUtil.DEFAULT_DATE_FORMAT);
        assertEquals("yyyy-MM-dd HH:mm:ss", DateUtil.DEFAULT_DATETIME_FORMAT);
    }
}