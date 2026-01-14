package org.example.pojo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.StatusEnum;

import java.time.LocalDateTime;

/**
 * @author nanak
 *
 * 表单元元数据实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormSchemaSaveDTO {
    // 表单业务标识
    @NotEmpty(message = "formName is not empty")
    private String formName;
    private String formDesc;
    @NotEmpty(message = "fields is not empty")
    private String fields;
    @NotNull(message = "status is not null")
    private StatusEnum status;
}
