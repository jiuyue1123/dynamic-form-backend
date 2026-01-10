package org.example.utils;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;

/**
 * @author nanak
 *
 * 加密工具类（封装 Hutool，统一加密规则）
 */
public class CryptoUtil {
    /**
     * AES 加密秘钥
     */
    // 16位密钥（AES-128）
    private static final String AES_KEY = "X4Fk6Pa9HASItgC6";

    /**
     * MD5(32位小写)
     */
    public String md5(String str) {
        return SecureUtil.md5(str);
    }

    /**
     * SHA256
     */
    public String sha256(String str) {
        return SecureUtil.sha256(str);
    }

    /**
     * AES 加密(Base64)
     */
    public String aesEncrypt(String str) {
        return SecureUtil.aes(AES_KEY.getBytes(StandardCharsets.UTF_8)).encryptBase64(str);
    }

    /**
     * 生成随机验证码
     */
    public String randomCode(int length) {
        if (length <= 0) {
            return "";
        }
        return RandomUtil.randomNumbers(length);
    }
}
