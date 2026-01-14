package org.example.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author nanak
 */
@Getter
public enum StatusEnum {
    ENABLED(1),
    DISABLED(0);

    @JsonValue
    @EnumValue
    private final int value;

    StatusEnum(int value) {
        this.value = value;
    }
}
