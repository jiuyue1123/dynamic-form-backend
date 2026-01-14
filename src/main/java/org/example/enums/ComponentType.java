package org.example.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author nanak
 */
@Getter
public enum ComponentType {
    BASE("base", "基础组件"),
    ADVANCED("advanced", "高级组件");

    @JsonValue
    @EnumValue
    private final String value;
    private final String label;

    ComponentType(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public static ComponentType getByValue(String value) {
        for (ComponentType type : ComponentType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }
}
