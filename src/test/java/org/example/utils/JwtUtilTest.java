package org.example.utils;

import cn.hutool.jwt.JWTException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JwtUtil 单元测试
 */
@SpringBootTest
class JwtUtilTest {

    @Test
    void testGenerateAccessToken() {
        // 准备测试数据
        Long userId = 12345L;
        
        // 执行测试
        String accessToken = ToolKit.JWT.generateAccessToken(userId);
        
        // 验证结果
        assertNotNull(accessToken);
        assertTrue(accessToken.length() > 0);
        
        // 验证Token格式（JWT格式：header.payload.signature）
        String[] parts = accessToken.split("\\.");
        assertEquals(3, parts.length);
    }

    @Test
    void testGenerateRefreshToken() {
        // 准备测试数据
        Long userId = 67890L;
        
        // 执行测试
        String refreshToken = ToolKit.JWT.generateRefreshToken(userId);
        
        // 验证结果
        assertNotNull(refreshToken);
        assertTrue(refreshToken.length() > 0);
        
        // 验证Token格式
        String[] parts = refreshToken.split("\\.");
        assertEquals(3, parts.length);
    }

    @Test
    void testValidateToken() {
        // 准备测试数据
        Long userId = 11111L;
        String token = ToolKit.JWT.generateAccessToken(userId);
        
        // 执行测试
        boolean isValid = ToolKit.JWT.validateToken(token);
        
        // 验证结果
        assertTrue(isValid);
    }

    @Test
    void testValidateTokenWithInvalidToken() {
        // 测试无效Token
        String invalidToken = "invalid.token.here";
        
        // 执行测试
        boolean isValid = ToolKit.JWT.validateToken(invalidToken);
        
        // 验证结果
        assertFalse(isValid);
    }

    @Test
    void testValidateTokenWithNull() {
        // 测试null Token
        boolean isValid = ToolKit.JWT.validateToken(null);
        
        // 验证结果
        assertFalse(isValid);
    }

    @Test
    void testValidateTokenWithEmptyString() {
        // 测试空字符串Token
        boolean isValid = ToolKit.JWT.validateToken("");
        
        // 验证结果
        assertFalse(isValid);
    }

    @Test
    void testParseToken() {
        // 准备测试数据
        Long userId = 22222L;
        String token = ToolKit.JWT.generateAccessToken(userId);
        
        // 执行测试
        Map<String, Object> payload = ToolKit.JWT.parseToken(token);
        
        // 验证结果
        assertNotNull(payload);
        assertTrue(payload.containsKey("userId"));
        assertTrue(payload.containsKey("expire"));
        
        // 验证用户ID
        Long parsedUserId = Long.parseLong(payload.get("userId").toString());
        assertEquals(userId, parsedUserId);
        
        // 验证过期时间存在且为未来时间
        Long expireTime = Long.parseLong(payload.get("expire").toString());
        assertTrue(expireTime > System.currentTimeMillis());
    }

    @Test
    void testParseTokenWithNullToken() {
        // 测试null Token解析
        assertThrows(IllegalArgumentException.class, () -> {
            ToolKit.JWT.parseToken(null);
        });
    }

    @Test
    void testParseTokenWithEmptyToken() {
        // 测试空字符串Token解析
        assertThrows(IllegalArgumentException.class, () -> {
            ToolKit.JWT.parseToken("");
        });
    }

    @Test
    void testParseTokenWithInvalidToken() {
        // 测试无效Token解析
        String invalidToken = "invalid.token.signature";
        
        assertThrows(Exception.class, () -> {
            ToolKit.JWT.parseToken(invalidToken);
        });
    }

    @Test
    void testRefreshToken() {
        // 准备测试数据
        Long userId = 33333L;
        String refreshToken = ToolKit.JWT.generateRefreshToken(userId);
        
        // 执行测试
        String newAccessToken = ToolKit.JWT.refreshToken(refreshToken);
        
        // 验证结果
        assertNotNull(newAccessToken);
        assertTrue(newAccessToken.length() > 0);
        
        // 验证新Token有效
        assertTrue(ToolKit.JWT.validateToken(newAccessToken));
        
        // 验证新Token包含正确的用户ID
        Map<String, Object> payload = ToolKit.JWT.parseToken(newAccessToken);
        Long parsedUserId = Long.parseLong(payload.get("userId").toString());
        assertEquals(userId, parsedUserId);
    }

    @Test
    void testRefreshTokenWithInvalidRefreshToken() {
        // 测试无效的刷新Token
        String invalidRefreshToken = "invalid.refresh.token";
        
        assertThrows(JWTException.class, () -> {
            ToolKit.JWT.refreshToken(invalidRefreshToken);
        });
    }

    @Test
    void testTokenLifecycle() {
        // 完整的Token生命周期测试
        Long userId = 44444L;
        
        // 1. 生成访问Token和刷新Token
        String accessToken = ToolKit.JWT.generateAccessToken(userId);
        String refreshToken = ToolKit.JWT.generateRefreshToken(userId);
        
        // 2. 验证两个Token都有效
        assertTrue(ToolKit.JWT.validateToken(accessToken));
        assertTrue(ToolKit.JWT.validateToken(refreshToken));
        
        // 3. 解析访问Token
        Map<String, Object> accessPayload = ToolKit.JWT.parseToken(accessToken);
        assertEquals(userId, Long.parseLong(accessPayload.get("userId").toString()));
        
        // 4. 解析刷新Token
        Map<String, Object> refreshPayload = ToolKit.JWT.parseToken(refreshToken);
        assertEquals(userId, Long.parseLong(refreshPayload.get("userId").toString()));
        
        // 5. 使用刷新Token生成新的访问Token
        String newAccessToken = ToolKit.JWT.refreshToken(refreshToken);
        assertTrue(ToolKit.JWT.validateToken(newAccessToken));
        
        // 6. 验证新访问Token包含正确信息
        Map<String, Object> newAccessPayload = ToolKit.JWT.parseToken(newAccessToken);
        assertEquals(userId, Long.parseLong(newAccessPayload.get("userId").toString()));
    }

    @Test
    void testDifferentUserTokens() {
        // 测试不同用户的Token不会混淆
        Long userId1 = 55555L;
        Long userId2 = 66666L;
        
        String token1 = ToolKit.JWT.generateAccessToken(userId1);
        String token2 = ToolKit.JWT.generateAccessToken(userId2);
        
        // 验证Token不同
        assertNotEquals(token1, token2);
        
        // 验证解析结果正确
        Map<String, Object> payload1 = ToolKit.JWT.parseToken(token1);
        Map<String, Object> payload2 = ToolKit.JWT.parseToken(token2);
        
        assertEquals(userId1, Long.parseLong(payload1.get("userId").toString()));
        assertEquals(userId2, Long.parseLong(payload2.get("userId").toString()));
    }

    @Test
    void testTokenExpiration() {
        // 测试Token包含过期时间
        Long userId = 77777L;
        String token = ToolKit.JWT.generateAccessToken(userId);
        
        Map<String, Object> payload = ToolKit.JWT.parseToken(token);
        
        // 验证包含过期时间
        assertTrue(payload.containsKey("expire"));
        
        Long expireTime = Long.parseLong(payload.get("expire").toString());
        Long currentTime = System.currentTimeMillis();
        
        // 验证过期时间在未来（访问Token 2小时后过期）
        assertTrue(expireTime > currentTime);
        assertTrue(expireTime <= currentTime + 7200 * 1000 + 1000); // 允许1秒误差
    }

    @Test
    void testRefreshTokenExpiration() {
        // 测试刷新Token的过期时间
        Long userId = 88888L;
        String refreshToken = ToolKit.JWT.generateRefreshToken(userId);
        
        Map<String, Object> payload = ToolKit.JWT.parseToken(refreshToken);
        
        // 验证包含过期时间
        assertTrue(payload.containsKey("expire"));
        
        Long expireTime = Long.parseLong(payload.get("expire").toString());
        Long currentTime = System.currentTimeMillis();
        
        // 验证过期时间在未来（刷新Token 7天后过期）
        assertTrue(expireTime > currentTime);
        assertTrue(expireTime <= currentTime + 604800 * 1000 + 1000); // 允许1秒误差
    }

    @Test
    void testTokenConsistency() {
        // 测试相同用户ID生成的Token在短时间内应该不同（因为时间戳不同）
        Long userId = 99999L;
        
        String token1 = ToolKit.JWT.generateAccessToken(userId);
        
        // 稍微等待一下确保时间戳不同
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String token2 = ToolKit.JWT.generateAccessToken(userId);
        
        // Token应该不同（因为过期时间不同）
        assertNotEquals(token1, token2);
        
        // 但解析出的用户ID应该相同
        Map<String, Object> payload1 = ToolKit.JWT.parseToken(token1);
        Map<String, Object> payload2 = ToolKit.JWT.parseToken(token2);
        
        assertEquals(
            Long.parseLong(payload1.get("userId").toString()),
            Long.parseLong(payload2.get("userId").toString())
        );
    }
}