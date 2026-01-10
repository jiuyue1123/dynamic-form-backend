package org.example.result;

import lombok.Data;
import org.example.enums.ErrorCode;

import java.io.Serializable;

/**
 * @author nanak
 */
@Data
public class Result<T> implements Serializable {
    private int code;
    private T data;
    private String message;

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(0, "success", data);
    }

    public static <T> Result<T> success() {
        return new Result<>(0, "success", null);
    }

    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }

    public static <T> Result<T> error(ErrorCode resultCode) {
        return new Result<>(resultCode.getCode(), resultCode.getMessage(), null);
    }
}
