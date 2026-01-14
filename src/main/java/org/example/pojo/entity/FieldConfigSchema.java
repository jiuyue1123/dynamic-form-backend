package org.example.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.RequiredEnum;

import java.time.LocalDateTime;

/**
 * @author nanak
 * <p>
 * 字段配置元数据实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldConfigSchema {
    private Long id;
    private String componentCode;
    private String configKey;
    private String configLabel;
    private String configType;
    private String configOptions;
    private String defaultValue;
    private RequiredEnum isRequired;
    private Integer sort;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
