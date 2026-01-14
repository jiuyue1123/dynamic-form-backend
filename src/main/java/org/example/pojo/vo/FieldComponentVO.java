package org.example.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.ComponentType;
import org.example.enums.StatusEnum;

import java.time.LocalDateTime;

/**
 * @author nanak
 *
 * 组件类型实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldComponentVO {
    private Long id;
    private String componentCode;
    private String componentName;
    private ComponentType componentType;
    private Integer sort;
    private String icon;
    private String description;
    private StatusEnum status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
