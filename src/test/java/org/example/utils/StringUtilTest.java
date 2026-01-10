package org.example.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * StringUtil 单元测试
 */
@SpringBootTest
class StringUtilTest {

    @Test
    void testIsEmpty() {
        // 测试空值
        assertTrue(StringUtil.isEmpty(null));
        assertTrue(StringUtil.isEmpty(""));
        
        // 测试非空值
        assertFalse(StringUtil.isEmpty("test"));
        assertFalse(StringUtil.isEmpty(" ")); // 空格不算空
    }

    @Test
    void testIsNotEmpty() {
        // 测试空值
        assertFalse(StringUtil.isNotEmpty(null));
        assertFalse(StringUtil.isNotEmpty(""));
        
        // 测试非空值
        assertTrue(StringUtil.isNotEmpty("test"));
        assertTrue(StringUtil.isNotEmpty(" ")); // 空格不算空
    }

    @Test
    void testSensitiveWithMobilePhone() {
        // 测试手机号脱敏
        String phone = "13812345678";
        String result = StringUtil.sensitive(phone);
        
        assertNotNull(result);
        assertNotEquals(phone, result);
        assertTrue(result.contains("138"));
        assertTrue(result.contains("78"));
        assertTrue(result.contains("****"));
    }

    @Test
    void testSensitiveWithIdCard() {
        // 测试身份证号脱敏
        String idCard = "110101199001011234";
        String result = StringUtil.sensitive(idCard);
        
        assertNotNull(result);
        assertNotEquals(idCard, result);
        assertTrue(result.startsWith("110101"));
        assertTrue(result.endsWith("34"));
    }

    @Test
    void testSensitiveWithEmail() {
        // 测试邮箱脱敏
        String email = "test@example.com";
        String result = StringUtil.sensitive(email);
        
        assertNotNull(result);
        assertNotEquals(email, result);
        assertTrue(result.contains("@"));
        assertTrue(result.contains("example.com"));
    }

    @Test
    void testSensitiveWithLandline() {
        // 测试座机号脱敏
        String landline = "010-12345678";
        String result = StringUtil.sensitive(landline);
        
        assertNotNull(result);
        assertNotEquals(landline, result);
        assertTrue(result.contains("010"));
    }

    @Test
    void testSensitiveWithBankCard() {
        // 测试银行卡号脱敏
        String bankCard = "6222021234567890";
        String result = StringUtil.sensitive(bankCard);
        
        assertNotNull(result);
        assertNotEquals(bankCard, result);
        assertTrue(result.startsWith("6222"));
        assertTrue(result.endsWith("7890"));
    }

    @Test
    void testSensitiveWithChineseName() {
        // 测试中文姓名脱敏
        String name = "张三";
        String result = StringUtil.sensitive(name);
        
        assertNotNull(result);
        assertNotEquals(name, result);
        assertTrue(result.startsWith("张"));
    }

    @Test
    void testSensitiveWithUserId() {
        // 测试纯数字ID脱敏
        String userId = "123456789";
        String result = StringUtil.sensitive(userId);
        
        assertNotNull(result);
        assertNotEquals(userId, result);
        assertTrue(result.endsWith("6789"));
    }

    @Test
    void testSensitiveWithAddress() {
        // 测试地址脱敏
        String address = "北京市朝阳区某某街道123号";
        String result = StringUtil.sensitive(address);
        
        assertNotNull(result);
        assertNotEquals(address, result);
        assertTrue(result.startsWith("北京市朝阳区"));
    }

    @Test
    void testSensitiveWithNull() {
        // 测试null值
        String result = StringUtil.sensitive(null);
        assertNull(result);
    }

    @Test
    void testSensitiveWithEmptyString() {
        // 测试空字符串
        String result = StringUtil.sensitive("");
        assertEquals("", result);
    }

    @Test
    void testSensitiveWithWhitespace() {
        // 测试空白字符串
        String result = StringUtil.sensitive("   ");
        assertEquals("   ", result);
    }

    @Test
    void testConcat() {
        // 测试字符串拼接
        String result = StringUtil.concat(false, "Hello", " ", "World");
        assertEquals("Hello World", result);
        
        // 测试包含null的拼接
        String resultWithNull = StringUtil.concat(true, "Hello", null, "World");
        assertEquals("HelloWorld", resultWithNull);
    }

    @Test
    void testJoin() {
        // 测试join拼接
        String result = StringUtil.join(",", "a", "b", "c");
        assertEquals("a,b,c", result);
        
        // 测试单个元素
        String singleResult = StringUtil.join(",", "a");
        assertEquals("a", singleResult);
        
        // 测试空数组
        String emptyResult = StringUtil.join(",");
        assertEquals("", emptyResult);
    }

    @Test
    void testUpperFirst() {
        // 测试首字母大写
        assertEquals("Hello", StringUtil.upperFirst("hello"));
        assertEquals("HELLO", StringUtil.upperFirst("hELLO"));
        assertEquals("H", StringUtil.upperFirst("h"));
        assertEquals("", StringUtil.upperFirst(""));
        assertNull(StringUtil.upperFirst(null));
    }

    @Test
    void testSensitiveWithPlateNumber() {
        // 测试车牌号脱敏
        String plateNumber = "京A12345";
        String result = StringUtil.sensitive(plateNumber);
        
        assertNotNull(result);
        assertNotEquals(plateNumber, result);
    }

    @Test
    void testSensitiveWithUnknownPattern() {
        // 测试未知模式的字符串
        String unknown = "abcdefg";
        String result = StringUtil.sensitive(unknown);
        
        assertNotNull(result);
        assertNotEquals(unknown, result);
        assertTrue(result.startsWith("a"));
        assertTrue(result.endsWith("g"));
    }
}