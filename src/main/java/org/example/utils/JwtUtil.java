package org.example.utils;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTException;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;

/**
 * @author nanak
 * <p>
 * JWT 工具类（封装 Hutool）
 */
@Slf4j
public class JwtUtil {
    // 密钥
    private final String key = "Xe1k1Gd6Fv1fVFtxeM0V3zVFKJquVqsxwXWlO3EJGHCy0UJzJjkwjBPo1JADH1LV";
    /**
     * 访问 Token 过期时间（单位：秒，2 小时）
     */
    private final long accessTokenExpire = 7200;

    /**
     * 刷新 Token 过期时间（单位：秒，默认 7 天）
     */
    private final long refreshTokenExpire = 604800;

    /**
     * 生成访问Token
     */
    public String generateAccessToken(Long userId) {
        return JWT.create()
                .setPayload("userId", userId)
                .setPayload("expire", System.currentTimeMillis() + accessTokenExpire * 1000)
                .setKey(key.getBytes())
                .sign();
    }

    /**
     * 生成刷新Token(仅包含基础信息，无敏感数据)
     */
    public String generateRefreshToken(Long userId) {
        return JWT.create()
                .setPayload("userId", userId)
                .setPayload("expire", System.currentTimeMillis() + refreshTokenExpire * 1000)
                .setKey(key.getBytes())
                .sign();
    }

    /**
     * 验证Token
     */
    public boolean validateToken(String token) {
        try {
            if (token == null || token.trim().isEmpty()) {
                return false;
            }
            // 验证签名 + 过期时间
            return JWT.of(token).setKey(key.getBytes()).validate(0);
        } catch (Exception e) {
            log.error("validateToken error: ", e);
            return false;
        }
    }

    /**
     * 解析Token
     */
    public Map<String, Object> parseToken(String token) {
        // 1. 空值校验
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("token must not be empty or null");
        }

        // 2. 解析 Token 为 JWT 对象
        JWT jwt = JWTUtil.parseToken(token);

        // 3. 验证签名（确保 Token 未被篡改）
        JWTSigner signer = JWTSignerUtil.hs256(key.getBytes());
        if (!jwt.verify(signer)) {
            throw new JWTException("token signature verification failed");
        }

        // 5. 提取所有载荷（包含自定义字段 + iss/iat/exp 等标准字段）
        return jwt.getPayloads();
    }

    /**
     * 刷新访问Token
     */
    public String refreshToken(String refreshToken) {
        log.info("refreshToken: {} {}", refreshToken, validateToken(refreshToken));
        // 先验证refreshToken是否有效

        if (!validateToken(refreshToken)) {
            throw new JWTException("refreshToken signature verification failed");
        }
        Long userId = Long.parseLong(parseToken(refreshToken).get("userId").toString());

        return generateAccessToken(userId);

    }
}
