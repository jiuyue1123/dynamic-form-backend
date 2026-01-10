package org.example.enums;

import lombok.Getter;

/**
 * @author nanak
 * <p>
 * 全局响应状态码枚举（5位规范）
 * 规范：
 * 0 - 通用成功
 * 4xxxx - 客户端错误（40000=参数错误，40100=未授权，40300=无权限，40400=资源不存在）
 * 5xxxx - 服务端错误（50000=通用失败，50001=数据库异常，50002=缓存异常）
 * 6xxxx - 业务自定义错误（60000=用户相关，60100=订单相关，60200=商品相关）
 */
@Getter
public enum ErrorCode {
    SUCCESS(0, "success"),
    PARAMS_ERROR(40000, "invalid parameters"),
    UNAUTHORIZED(40100, "unauthenticated"),
    FORBIDDEN(40300, "forbidden"),
    NOT_FOUND(40400, "resource not found"),
    SERVER_ERROR(50000, "internal server error"),
    ;

    /**
     * 状态码
     */
    private final int code;

    /**
     * 响应信息
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
