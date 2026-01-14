package org.example.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author nanak
 */
public enum RequiredEnum {
    REQUIRED(0),
    NOT_REQUIRED(1);

    @JsonValue
    @EnumValue
    private final int value;

    RequiredEnum(int value) {
        this.value = value;
    }

    public static RequiredEnum getByValue(int value) {
        for (RequiredEnum type : RequiredEnum.values()) {
            if (type.value == value) {
                return type;
            }
        }
        return null;
    }
}
