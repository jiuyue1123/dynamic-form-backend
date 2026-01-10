package org.example.exception;

import lombok.Getter;
import org.example.enums.ErrorCode;

/**
 * @author nanak
 *
 * 自定义异常类
 */
@Getter
public class BusinessException  extends RuntimeException {
    /**
     * 错误码
     */
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }
}
