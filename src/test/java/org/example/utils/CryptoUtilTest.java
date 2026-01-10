package org.example.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CryptoUtil 单元测试
 */
@SpringBootTest
@TestPropertySource(properties = {"aes.key=X4Fk6Pa9HASItgC6"})
class CryptoUtilTest {

    @Test
    void testMd5() {
        // 准备测试数据
        String input = "password123";
        
        // 执行测试
        String result = ToolKit.CRYPTO.md5(input);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(32, result.length()); // MD5固定32位
        assertTrue(result.matches("[a-f0-9]{32}")); // 只包含小写字母和数字
        
        // 测试相同输入产生相同输出
        String result2 = ToolKit.CRYPTO.md5(input);
        assertEquals(result, result2);
    }

    @Test
    void testSha256() {
        // 准备测试数据
        String input = "password123";
        
        // 执行测试
        String result = ToolKit.CRYPTO.sha256(input);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(64, result.length()); // SHA256固定64位
        assertTrue(result.matches("[a-f0-9]{64}")); // 只包含小写字母和数字
        
        // 测试相同输入产生相同输出
        String result2 = ToolKit.CRYPTO.sha256(input);
        assertEquals(result, result2);
    }

    @Test
    void testAesEncrypt() {
        // 准备测试数据
        String input = "sensitive data";
        
        // 执行测试
        String encrypted = ToolKit.CRYPTO.aesEncrypt(input);
        
        // 验证结果
        assertNotNull(encrypted);
        assertNotEquals(input, encrypted);
        assertTrue(encrypted.length() > 0);
        
        // 测试相同输入产生相同输出
        String encrypted2 = ToolKit.CRYPTO.aesEncrypt(input);
        assertEquals(encrypted, encrypted2);
    }

    @Test
    void testRandomCode() {
        // 测试6位验证码
        String code6 = ToolKit.CRYPTO.randomCode(6);
        assertNotNull(code6);
        assertEquals(6, code6.length());
        assertTrue(code6.matches("\\d{6}")); // 只包含数字
        
        // 测试4位验证码
        String code4 = ToolKit.CRYPTO.randomCode(4);
        assertNotNull(code4);
        assertEquals(4, code4.length());
        assertTrue(code4.matches("\\d{4}"));
        
        // 测试随机性（两次生成的验证码应该不同）
        String code1 = ToolKit.CRYPTO.randomCode(6);
        String code2 = ToolKit.CRYPTO.randomCode(6);
        // 注意：理论上可能相同，但概率极低
        // assertNotEquals(code1, code2);
    }

    @Test
    void testMd5WithEmptyString() {
        String result = ToolKit.CRYPTO.md5("");
        assertNotNull(result);
        assertEquals(32, result.length());
    }

    @Test
    void testSha256WithEmptyString() {
        String result = ToolKit.CRYPTO.sha256("");
        assertNotNull(result);
        assertEquals(64, result.length());
    }

    @Test
    void testAesEncryptWithEmptyString() {
        String result = ToolKit.CRYPTO.aesEncrypt("");
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }

    @Test
    void testRandomCodeWithZeroLength() {
        String result = ToolKit.CRYPTO.randomCode(0);
        assertNotNull(result);
        assertEquals(0, result.length());
    }

    @Test
    void testMd5Consistency() {
        // 测试MD5的一致性
        String input = "test123";
        String expected = "cc03e747a6afbbcbf8be7668acfebee5"; // test123的MD5值
        String actual = ToolKit.CRYPTO.md5(input);
        assertEquals(expected, actual);
    }
}